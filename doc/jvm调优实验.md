jvm 调优 主要干的事情：

OutOfMemoryError，内存不足
内存泄露
线程死锁
锁争用（Lock Contention）
Java进程消耗CPU过高
查看gc情况，调整内存分配

参数示例：
-Xms7168m -Xmx14336m  -Xmn2048m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC

jps 找到我们应用的进程号

top -Hp pid 查看线程情况，找到消耗内存最高的线程号

查看线程堆栈信息
将需要的线程ID转换为16进制格式：printf "%x\n" 14386

jstack pid |grep tid -A 30
jstack 14372 |grep 3832 -A 30

https://www.cnblogs.com/wuchanming/p/7766994.html

获取线程快照分析


![](2021-12-23-01-02-20.png)

主要可以分析当前线程状态线程执行链路，以找到耗性能的代码

检查 jvm 参数，jar 参数 

jinfo

jmap
 jmap -heap 28843
查看运行中的jvm物理内存的占用情况。
 参数如下：
 -heap ：打印jvm h堆的情况
 -histo： 打印jvm heap的直方图。其输出信息包括类名，对象数量，对象占用大小。
 -histo：live ： 同上，但是只答应存活对象的情况
 -permstat： 打印permanent generation heap情况

https://cloud.tencent.com/developer/article/1446243?from=15425

通过 jmap 可以检测 老年代新生代

 4.jstat
 参数如下：
 -class：统计class loader行为信息
 -compile：统计编译行为信息
 -gc：统计jdk gc时heap信息
 -gccapacity：统计不同的generations（不知道怎么翻译好，包括新生区，老年区，permanent区）相应的heap容量情况
 -gccause：统计gc的情况，（同-gcutil）和引起gc的事件
 -gcnew：统计gc时，新生代的情况
 -gcnewcapacity：统计gc时，新生代heap容量
 -gcold：统计gc时，老年区的情况
 -gcoldcapacity：统计gc时，老年区heap容量
 -gcpermcapacity：统计gc时，permanent区heap容量
 -gcutil：统计gc时，heap情况

jstat -gcnew 28843 1000 10
 统计gc时新生代的情况，每间隔1000ms统计一次，共统计10次


## 垃圾收集器配置

串行收集器组合 Serial + Serial Old

-XX:+SerialGC

串行收集器特别适合堆内存不高、单核甚至双核CPU的场合。

并行收集器组合 Parallel Scavenge + Parallel Old

开启选项：-XX:+UseParallelGC或-XX:+UseParallelOldGC(可互相激活)

并行收集器适合对吞吐量要求远远高于延迟要求的场景，并且在满足最差延时的情况下，并行收集器将提供最佳的吞吐量

并发标记清除收集器组合 ParNew + CMS + Serial Old

开启选项：-XX:+UseConcMarkSweepGC

https://cloud.tencent.com/developer/article/1459638





 
