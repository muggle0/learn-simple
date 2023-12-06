通过阿里的Transmittable Thread Local（TTL）线程变量，是可以在创建匿名Runnable对象并使用线程池执行时，将父线程的变量传递给子线程的。

TTL线程变量是对ThreadLocal的增强，它可以在父线程中创建的变量值在子线程中被传递和访问。当你创建一个匿名Runnable对象并将其提交给线程池执行时，TTL会自动将父线程的变量值复制到子线程中，因此你可以在子线程中访问到父线程的TTL线程变量。

以下是一个示例代码，演示如何在匿名Runnable对象中通过线程池执行，并使用TTL线程变量获取父线程的变量值：
```java

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

public class TtlExample {
private static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("Hello from parent thread");

        Runnable runnable = () -> {
            String value = threadLocal.get();
            System.out.println("Value in child thread: " + value);
        };

        // 使用TtlRunnable包装匿名Runnable对象
        Runnable ttlRunnable = TtlRunnable.get(runnable);

        // 创建线程池并执行任务
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(ttlRunnable);

        // 关闭线程池
        executorService.shutdown();
    }
}

```

在上面的示例中，我们创建了一个TTL线程变量threadLocal，并在父线程中设置了变量值。然后，我们创建了一个匿名Runnable对象，并使用TtlRunnable.get()方法对其进行包装，以确保TTL线程变量在子线程中被传递。最后，我们使用线程池执行了这个包装后的任务。

当子线程运行时，它将能够访问父线程的TTL线程变量，并输出其值。通过使用TTL线程变量，你可以在匿名Runnable对象中获取父线程的变量值。
