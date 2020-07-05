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

cal 日历 

clear 清屏

管道 符号  |

作用  用于 过滤 扩展处理 特殊语法

ls | grep 'xxx' ·

hostname -f

ps -ef 查看服务器进程信息
-e 等价于 -A 显示全部进程
-f 显示字段
pid  进程id 
ppid 父进程id 
ppid 不存在为僵尸进程
c 表示cpu 占用率
stime 启动时间
cmd 进程的名称或者对应的路径

top 查看服务器进程占用资源

find /etc -name 'xxx'

find /etc -type f 

find /etc -type d

service start stop restart 

kill  66666 (pid )

ifconfig 

reboot  重启

shutdown 关机

uptime 开机时间

netstat -tnlp  net状态

-t 只显示tcp协议

-n 信息转化

-l 过滤 状态列的值为 listen的连接

-p 显示发起连接的进程pid 

uname  获取计算机系统信息

man  命令手册

Linux的运行模式

linux 存在一个init 进程 pid 为1 该进程存在一个配置文件 inittab (/etc/inittab)

用户级别 0 关机 1单用户  2多用户  3完全多用户 4其他

用户与用户组

添加用户 useradd 

-g 指定用户组 

-G 指定用户附加组

-u 指定用户id 

修改用户 usermod 

设置用户密码 Linux 不允许无密码用户
无密码处于锁定状态 

passwd xxx 回车

用户信息 /etc/shadow
用户名：加密密码：最后一次修改时间：最小修改时间间隔：密码有效期：密码需要变更前的警告天数：密码过期后的宽限时间：账号失效时间：保留字段

切换用户 su xxx

删除用户 userdel  -r删除对应文件夹
用户组 用户组的增删改查操作的是  /etc/group

groupadd -g(同用户 -u)
groupdel 

网络设置 

网卡配置文件位置：
/etc/sysconfig/network-scripts

网卡文件命名规则  ifcfg-xxx

软连接