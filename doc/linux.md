目录含义

bin 二进制文件
dev 外接设备（需要挂载）
etc 配置文件
home user 目录
proc 存储linux运行进程
root root用户的目录
sbin 超级管理员权限的二进制文件
tmp 临时文件
usr:用户软件
var 日志文件夹
mnt 当外接设备需要挂载时，挂载到该目录下

linux 一切皆文件
指令的一般格式 
指令主体 [选项] [操作对象，不指定一般为当前对象]
```java
drwxr-xr-x.  2 root       root 4096 4月  11 2018 adm
               （用户组）（用户名）
```
第一位 d表示文件夹 - 表示文件

ls -lh /var


pwd （当前路径）

