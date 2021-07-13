## sentinel介绍
sentinel 是阿里开源的流量控制，熔断降级，系统负载保护的一个Java组件；

Sentinel 分为两个部分:
核心库（Java 客户端）不依赖任何框架/库，能够运行于所有 Java 运行时环境，同时对 Dubbo / Spring Cloud 等框架也有较好的支持。
控制台（Dashboard）基于 Spring Boot 开发，打包后可以直接运行，不需要额外的 Tomcat 等应用容器。
Quick Start

[sentinel 官方文档点击这里](https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D)

## sentinel Quick Start
我们这里还是以springboot 项目写一个demo，创建完成springboot 项目之后pom中引入依赖：

```pom
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
    <version>1.8.1</version>
</dependency>
```
然后定义流控规则并加载到内存：
```java

@Configuration
public class SentileConfig {


    @PostConstruct
    private void initRules() throws Exception {
        FlowRule rule1 = new FlowRule();
        rule1.setResource("test.hello");
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setCount(1);   // 每秒调用最大次数为 1 次
        List<FlowRule> rules = new ArrayList<>();
        rules.add(rule1);
        // 将控制规则载入到 Sentinel
        com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager.loadRules(rules);
    }
}
```

然后创建 `controller` 并定义资源：

```java

@RestController
public class TestController {
   

    @GetMapping("/test0")
    public String test0(){
        try {
            Entry resourceName = SphU.entry("test.hello");
            return resourceName.toString();
        } catch (BlockException e) {
            e.printStackTrace();
            return "error";
        }
    }
}

```

然后我们启动项目并访问 `http://localhost:8081/test0` 然后不断的刷新，就会发现如果刷新频率超过一秒就会返回error 否则会返回一个时间戳。
这里这些类的api和源码我们先不介绍，只对其功能先做一个大致的体验。

接下来我们继续引入依赖：
```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-annotation-aspectj</artifactId>
            <version>1.8.1</version>
        </dependency>
```

同时注入一个切面到springboot 中去：

```java
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

```

这个时候我们就可以通过注解去做流量控制了，写一个接口测试一下：

```java
    @GetMapping("/test")
    @SentinelResource(value = "test.hello")
    public String test(){
        return "success";
    }

```

同样通过浏览器访问这个接口并不断刷新，会发现会频率过快的时候会返回 springboot 的错误页面，这是因为当aop切面会抛出 `BlockException`，当没有对应的
异常处理器的时候springboot就会返回默认错误页面。这个时候我们有两种方式处理我们超出访问频率的时候的逻辑。

第一种，加降级方法：

```java
 @GetMapping("/test")
    @SentinelResource(value = "test.hello",fallback = "testFallback")
    public String test(){
        return "success";
    }
    
    public String testFallback() {
        return "xxx";
    }

```

第二种，加`BlockException`异常处理器：

```java

@ControllerAdvice
public class ExceptionHandlerConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BlockException.class)
    @ResponseBody
    public ResultWrapper sentinelBlockHandler(BlockException e) {
        logger.warn("Blocked by Sentinel: {}", e.getRule());
        // Return the customized result.
        return "error";
    }
}

```


## 对所有controller 层做流控
`sentinel` 还提供了 spring-mvc 的拦截器，配置该拦截器你可以对你项目的所有所有请求进行流控管理，首先我们需要引入依赖：
```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-spring-webmvc-adapter</artifactId>
            <version>1.8.1</version>
        </dependency>
```

然后注入一个sentinel 的拦截器：
```java
@Configuration
public class SimpleWebmvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注冊sentinel 拦截器
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        config.setHttpMethodSpecify(true);
        config.setWebContextUnify(true);
        config.setBlockExceptionHandler(new SimpleBlockExceptionHandler());
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }
}
```

代码中 `SimpleBlockExceptionHandler` 是自定义流控异常处理器，作用是处理流控异常 `BlockException` 源码如下：
```java

public class SimpleBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        out.print("{\"code\":500}");
        out.flush();
        out.close();
    }
}
```
`SentinelWebMvcConfig` 是流控配置类，通过其属性命名就不难猜出其作用 `isHttpMethodSpecify` 是否区分请求方式；
`isWebContextUnify`是否使用统一web上下文； `UrlCleaner` 是url清理器，作用是对url进行整理

## 接入控制台
`sentinel` 为我们提供了一个控制台应用，通过这个控制台我们可以直观的看到流控数据，动态的修改流控规则，下面让我们看看如何接入控制台。

首先引入依赖：

```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-transport-simple-http</artifactId>
            <version>1.8.1</version>
        </dependency>
```

这个依赖sentinel连接 控制端的通讯包。

然后添加配置：
```properties
csp.sentinel.dashboard.server=localhost:8080
```

注意，因为这个配置项不是属于 springboot的 所以不能添加在application中，要通过 -D 的方式在jvm启动的时候添加这个配置项。

再去 [sentinel的github](https://github.com/alibaba/Sentinel/releases) 下载 控制台jar包 ，启动该jar包；访问8080 端口，
登录账号密码和密码都是 sentinel。这个控制台实际上是一个springboot应用，启动端口和账号密码都可以通过application 修改。
接下来，启动我们自己的应用，并访问一些接口，我们就能再界面上看到监控数据：
![sentinel-dashboard](sentinel-1.jpg)

通过控制台我们可以监控流量，管理流控降级等规则，这些规则都是存储在我们程序应用的内存中的，因此我们还需要学会这些规则的配置使用及其持久化。

sentinel 官方还提供了 springcloud 的包，可以让我们很方便的在 spring cloud 项目中使用sentinel，springcloud 中使用 sentinel和 springboot
中使用sentinel方式差不多，只是多了一个链路调用；因此我们要先学会了如何在 springboot中使用它。



# sentinel 

## 流量控制

## 降级规则

## 热点规则

## 系统规则

## 授权规则

## 持久化

关于 sentinel 持久化 在官方资料 [https://github.com/alibaba/Sentinel/wiki/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%99%E6%89%A9%E5%B1%95](动态规则)中有介绍，

应用流控规则的更新有三种方式——推模式，拉模式，api推送，推模式是使用 zk redis nacos 等具有监听功能的中间件来将更新后的规则推送到应用中去，而拉模式则需要
应用主动去数据源中拉取规则，因此推模式更具有时效性。sentinal 官方提供的数据源整合依赖包括 consul redis Apollo eureka redis spring-cloud-config
zookeeper 。
对于数据源的整合，sentinel的扩展性是很强的，最坏的情况也是使用api去调用接口对规则的管理，这种情况需要使用者完全独立的开发一个规则管理应用出来。


整合数据源的推模式（这也是使用场景最多的）核心依赖是

```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-extension</artifactId>
            <version>1.8.1</version>
        </dependency>
```

官方提供的数据源整合依赖都是在这个依赖的基础上开发的。接下来我们就已redis做为数据源，以`sentinel-datasource-extension` 作为基础依赖开发一个
sentinel的数据源整合依赖。

## 核心类说明

实现拉模式的数据源最简单的方式是继承 AutoRefreshDataSource 抽象类，然后实现 readSource() 方法，在该方法里从指定数据源读取字符串格式的配置数据。

实现推模式的数据源最简单的方式是继承 AbstractDataSource 抽象类，在其构造方法中添加监听器，并实现 readSource() 从指定数据源读取字符串格式的配置数据。

我们



## 

## 结语

## 