## 查看文档

--按日期查看 
sed -n "/2022-11-17 09:25:55/,/2022-12-17 09:25:55/p" test.log
sed -n ‘5,10p’ filename 这样你就可以只查看文件的第5行到第10行。
cat filename | grep abc -A4

> 表示输入到目标，>> 表示追加

--cat,more,less,head,tail
--创建并输入 
cat>test.txt

-- 翻页显示
more -s file
-- 第20行
more +20 
less 退出按q
--显示test4.txt的前二十行内容
head -n 20 test4.txt

-- 从第3行开始显示，显示接下来10行内容。
cat filename | tail -n +3 | head -n +10

## 性能分析

top 进程信息 输入top 后 按 P CPU 排序 M 内存排序 q 退出

磁盘使用情况

查询子级目录的大小 du -h --max-depth=1 /
查询磁盘情况 df -h /

ps -aux|grep java

netstat -nltp | grep 端口号

free -m和free -g命令查看，分别表示MB和GB  cat /proc/meminfo
nohup java -jar babyshark-0.0.1-SNAPSHOT.jar  > log.file  2>&1 &

## 配置

find /etc -name '*srm*' 文件查找
yum list | grep jdk-devel
环境变量 cat /etc/profile
source  /etc/profile

vi 搜索
/test

##

mkdir -p 创建带层级的目录
cp -r 对目录操作 递归



sed -n "/2022-01-14 15:05:55/,/2022-01-14 15:15:55/p" test.log