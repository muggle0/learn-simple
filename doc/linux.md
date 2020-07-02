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

mkdir -p
touch 创建文件

cp -r 
ls 文件颜色（蓝色是文件夹 黑色是文件 绿色是权限）

vim 编辑器三种模式

底线命令模式 
输入模式
命令模式

输出重定向 （将输出的内容指定到目标 ）  > 和 >>  覆盖输出 追加输出
cat 打开文件 文件合并
cat a.txt b.txt > c.txt

df -h 查看磁盘

free -m 查看内存使用情况 -m表示 以mb为单位

head  查看文件前n行 默认10 head -n xxx.txt 

tail -n 查看最后几行
less 较少内容查看文件 

wc 统计文件内容信息

date 查看日期


