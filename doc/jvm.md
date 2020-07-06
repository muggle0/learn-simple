class 文件阅读操作码 javap
jcmd 分析内存快照
-XX:+HeapDumpOnOutOfMemoryError 生成内存快照
jmap -dump:live,format=b,file=m.hprof PID 内存快照
查看堆内存树信息 ：
classname | shallow heap (对象本身所占用内存大小) | Reatined heap（对象和引用对象内存总和） | percentage 

jps 查看java进程工具 

jconsole 图形化界面 

堆内存区域划分 

年轻代(Young Gen) ： Eden Space和2个Suvivor Space（命名为A和B）

虚拟机栈 本地方法栈 程序计数器 

每次方法执行 java虚拟机栈创建一个栈帧 存储局部变量表
xmx xms 堆内存

GC :
当年轻代（Eden区）满时就会触发 Minor GC，这里的年轻代满指的是 Eden区满。Survivor 满不会触发 Minor GC 

xmn 新生代内存 
Eden 内存比例设置 -XX:suvivor retio=8

内存分配策略
优先分配到eden 
大对象直接进入老年代
长期存活对象进入老年代
空间分配担保
动态对象年龄判断

jps

jstate -gcutil 6666
