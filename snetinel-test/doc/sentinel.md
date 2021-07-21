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



# sentinel 实战

## 簇点链路


## 流控规则持久化

我们之前配置的流控规则都是存储在应用的内存中的，这种方式明显无法满足我们实际开发的需求，一旦项目被重启，流控规则就被初始化了，需要我们再次去重新配置，因此规则的持久化就显得很有必要了。

本节会介绍几类主流持久化方式并对自定义持久化做介绍

### 文件持久化

文件持久化是通过 sentinel spi 扩展点来加载本地文件中的持久化数据到内存中，它依赖接口 `InitFunc`，对于非spring项目这种方式可以很便捷的实现
文件持久化。

实现文件持久化首先要自定义一个类并实现`InitFunc` 接口：

```java

public class MyflieInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        URL resource = MyflieInitFunc.class.getClassLoader().getResource("");
        File file = new File(resource.getPath()+"/config/flow.json");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (!file.exists()){
            file.createNewFile();
        }
        ReadableDataSource<String, List<FlowRule>> flowReadDataSource = new FileRefreshableDataSource<>(
            resource.getPath()+"/config/flow.json",
            source -> JSON.parseObject(
                source,
                new TypeReference<List<FlowRule>>() {
                }
            ));
        FlowRuleManager.register2Property(flowReadDataSource.getProperty());
        WritableDataSource<List<FlowRule>> flowWriteDataSource = new FileWritableDataSource<>(
            resource.getPath()+"/config/flow.json",
            t->JSON.toJSONString(t)
        );
        WritableDataSourceRegistry.registerFlowDataSource(flowWriteDataSource);
    }
}
```

然后在resources 文件夹下新建文件 `META-INF\services\com.alibaba.csp.sentinel.init.InitFunc` 
内容为`MyflieInitFunc` 的类路径：
```properties
com.muggle.sentinel.config.MyflieInitFunc
```

完成以上步骤后，文件持久化的方式就配置完成了。

`InitFunc` 的资源初始化方法 `init()` 并不是在项目启动的时候调用的，而是在首次产生流控数据的时候调用的，
也就是说它是一个懒加载的方法。
在文件持久化配置中，`FileRefreshableDataSource` , `FileWritableDataSource` , `FlowRuleManager` 这三个类是有必要去熟识的。

- FlowRuleManager 流控规则管理器，用于对流控规则的加载和管理，每类规则都有对应的管理器，后文会介绍。
- FileRefreshableDataSource 流控规则读取及刷新的类，该类配置到sentinel中后会定时拉取流控文件中的流控规则
- FileWritableDataSource 流控规则写入类，当我们在控制台编辑新的流控规则后，控制台会将规则推送给应用，应用接收到推送的规则后，
会通过该类将数据写入流控文件中

### `FlowRuleManager` 源码分析

```java

public class FlowRuleManager {
    private static final AtomicReference<Map<String, List<FlowRule>>> flowRules = new AtomicReference();
    private static final FlowRuleManager.FlowPropertyListener LISTENER = new FlowRuleManager.FlowPropertyListener();
    private static SentinelProperty<List<FlowRule>> currentProperty = new DynamicSentinelProperty();
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1, new NamedThreadFactory("sentinel-metrics-record-task", true));
     
    public static List<FlowRule> getRules() {
       
        List<FlowRule> rules = new ArrayList();
        Iterator var1 = ((Map)flowRules.get()).entrySet().iterator();

        while(var1.hasNext()) {
            Entry<String, List<FlowRule>> entry = (Entry)var1.next();
            rules.addAll((Collection)entry.getValue());
        }

        return rules;
    }
    
    public static void loadRules(List<FlowRule> rules){
         currentProperty.updateValue(rules);
    }
    
     public static boolean hasConfig(String resource) {
         return ((Map)flowRules.get()).containsKey(resource);
    }
}
```
该类的静态属性包括 流控规则数组 `flowRules` ，用于监控流控规则更新的监听器`LISTENER` , 轮询监听流控配置的线程池`SCHEDULER`,sentinel 配置类`currentProperty`.
而它几个api也很明了，就是对流控规则的增删改查。

### `FileRefreshableDataSource` 源码分析

`FileRefreshableDataSource` 继承了`AutoRefreshDataSource`,而`AutoRefreshDataSource` 中有一个线程池 `service` 用于拉取 文件中存储的规则
以及拉取间隔 `recommendRefreshMs` .
：

```java

public abstract class AutoRefreshDataSource<S, T> extends AbstractDataSource<S, T> {
    private ScheduledExecutorService service;
    protected long recommendRefreshMs = 3000L;

    public AutoRefreshDataSource(Converter<S, T> configParser) {
        super(configParser);
        this.startTimerService();
    }
......
}
``` 

我们可以看一下 `FileRefreshableDataSource` 构造函数：

```java

 public FileRefreshableDataSource(File file, Converter<String, T> configParser) throws FileNotFoundException {
        this(file, configParser, 3000L, 1048576, DEFAULT_CHAR_SET);
    }

    public FileRefreshableDataSource(String fileName, Converter<String, T> configParser) throws FileNotFoundException {
        this(new File(fileName), configParser, 3000L, 1048576, DEFAULT_CHAR_SET);
    }

    public FileRefreshableDataSource(File file, Converter<String, T> configParser, int bufSize) throws FileNotFoundException {
        this(file, configParser, 3000L, bufSize, DEFAULT_CHAR_SET);
    }

    public FileRefreshableDataSource(File file, Converter<String, T> configParser, Charset charset) throws FileNotFoundException {
        this(file, configParser, 3000L, 1048576, charset);
    }

    public FileRefreshableDataSource(File file, Converter<String, T> configParser, long recommendRefreshMs, int bufSize, Charset charset) 
```

不难看出，如果在 new `FileRefreshableDataSource` 时不指定刷新间隔就取默认值 3000 毫秒。

### `FileWritableDataSource` 源码分析

```java

public class FileWritableDataSource<T> implements WritableDataSource<T> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private final Converter<T, String> configEncoder;
    private final File file;
    private final Charset charset;
    private final Lock lock;
    
    public void write(T value) throws Exception {
        this.lock.lock();
        try {
            String convertResult = (String)this.configEncoder.convert(value);
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(this.file);
                byte[] bytesArray = convertResult.getBytes(this.charset);
                RecordLog.info("[FileWritableDataSource] Writing to file {}: {}", new Object[]{this.file, convertResult});
                outputStream.write(bytesArray);
                outputStream.flush();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception var16) {
                    }
                }

            }
        } finally {
            this.lock.unlock();
        }
    }
}

```
代码结构也很了然，一个数据转换器，一个 file 一个lock ,当框架调用 `write` 方法时上锁并往 file中写配置。

分析得差不多了，让我们看看实战效果吧；

首先启动项目和控制台，然后在控制台上配置一个流控规则，可以观察到项目规则存储文件中多了点内容：
![](sentinel2.jpg)

```json
[{"clusterConfig":{"acquireRefuseStrategy":0,"clientOfflineTime":2000,"fallbackToLocalWhenFail":true,"resourceTimeout":2000,"resourceTimeoutStrategy":0,"sampleCount":10,"strategy":0,"thresholdType":0,"windowIntervalMs":1000},"clusterMode":false,"controlBehavior":0,"count":1.0,"grade":1,"limitApp":"default","maxQueueingTimeMs":500,"resource":"test","strategy":0,"warmUpPeriodSec":10}]
```

我们重启项目和控制台规则也不会丢失，规则持久化生效。

通过分析我们知道，这种持久化方式是一种拉模式，胜在配置简单，不需要外部数据源就能完成流控数据的持久化。由于规则是用 FileRefreshableDataSource 定时更新的，所以规则更新会有延迟。
如果FileRefreshableDataSource定时时间过大，可能长时间延迟；如果FileRefreshableDataSource过小，又会影响性能；
因为规则存储在本地文件，如果需要迁移微服务，那么需要把规则文件一起迁移，否则规则会丢失。

文件持久化能应付我们需求的大部分场景，但对于微服务而言是不那么满足要求的；
因为文件持久化就必定要求我们在服务器上提供一个用于存储配置文件的文件夹，而微服务项目大部分情况是容器部署，这就让文件持久化显得不那么好用了。

为此，官方提供了自定义的持久化maven依赖：
```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-extension</artifactId>
        </dependency>

```
以及在这个依赖的基础上开发的以CONSUL NACOS REDIS 作为数据源的maven 依赖：

```xml
        <dependency>
            <artifactId>sentinel-datasource-consul</artifactId>
            <groupId>com.alibaba.csp</groupId>
        </dependency>
        <dependency>
            <artifactId>sentinel-datasource-redis</artifactId>
            <groupId>com.alibaba.csp</groupId>
        </dependency>

        <dependency>
            <artifactId>sentinel-datasource-nacos</artifactId>
            <groupId>com.alibaba.csp</groupId>
        </dependency>

```
### redis 持久化
以上三种种持久化不同于文件持久化，它们是推模式的，而且迁移部署起来更为方便，符合微服务的特性。接下来我们就以nacos持久化为例来学习一下这种方式是怎么配置的。

首先引入依赖

```java
ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
```

sentinel 增加规则的方式 包括三种，数据源加载，代码加载，控制台加载；每一类流控规则我都会从这三个方面去说明如何使用。

## 流量控制

流量控制概念



## 降级规则

降级熔断概念

降级熔断和流量控制的区别


## 热点规则
热点规则的概念


## 系统规则
系统规则的概念

系统规则的特点

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