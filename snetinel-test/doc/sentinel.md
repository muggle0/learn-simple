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

然后创建 `ccontroller` 并定义资源：

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
`SentinelWebMvcConfig` 是流控配置类，在这里说明一下其一些配置的意义;





## 接入控制台

sentinel 提供了一个可视化的控制台应用 `sentinel-dashboard`，

## 流量控制

## 降级规则

## 热点规则

## 系统规则

## 授权规则

## 持久化

## 

## 结语

## 