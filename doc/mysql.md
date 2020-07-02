## mysql 表设计

数据类型要合理，长度要合理

varchar的长度 
mysql在查询时对于varchar字段在内存中是采用固定宽度而不是储存时的变长宽度 尤其是查询时创建的隐形临时表 
所以在选择字段属性时还是适可而止 
根据自己的业务来选择最合适的并且最小的长度 从而来提高查询速度 减少数据库服务器的开销

枚举和set

## 索引 
跳表 哈希 b树和b+树的优势对比

## 表设计

字段冗余 缓存表汇总表

## 存储过程类 
create view 

## mysql 主从配置

需要下载免安装版

## mysql 语法练习

```java
 # 查询所有的字段
 show full cloumns from table
 
 # 查询锁 当事务正在执行时会显示sql
 
 show processlist
 
 show OPEN TABLES where In_use > 0;

# 查看正在锁的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS; 
 # 查看等待锁的事务
   SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS;
 
 # 字段增删改
 alter table [modify,add ,drop ,rename,alter]
 
 # 删除表数据
 truncate 
 
 # 权限配置
 
 
```

### 变量

系统变量（全局变量和会话变量）


```java
show SESSION VARIABLES; 会话变量

show global VARIABLES
查看变量

show global variavles like ''

select @@global.xxx;

set global.xxx=xxxx

set @@global.autocommit=1;


服务器每次启动系统变量都会被赋初始值

隔离级别 session @@session.tx_isolation='read-committed'


```

自定义变量 （用户变量和 局部变量）

用户变量只作用于当前会话

声明并初始化

set @xxx=xxx
set @xxx:=xxx
select @xxx:=xxx

赋值 同上
或者 select count(*) into @count from table
set @name='xx'
set @name=11 合法

查看 

select @count

可以声明在 begin 外部只作用于当前会话

局部变量只作用于begin end 且必须是第一句话

声明 declare 变量名 类型
declare 变量名 类型 值

赋值 select count(*) into @count from table

使用 select 局部变量名


### 存储过程

call 存储过程名（实参列表）
delimiter  $  
create procedure test()
begin
 
 sql
 
end $

in 模式存储过程

create procedure test(in xxx varchar(50) )
out 模式存储过程

delimiter $ 
create procedure test(out xxx varchar(50))

select name  into xxx  from table

delimiter  ;
inout 模式存储过程

删除 drop procedure xxx;
查看 show create procedure xxx

### 视图

create view xx as 
select ...

Mysql视图的定义在from关键字后面不能包含子查询

alter view 视图名 as select 语句

show create view 视图名;

　drop view 视图名[,视图名…];
Rename table 视图名 to 新视图名;
只保存了sql逻辑不保存查询结果


### 函数
    CREATE FUNCTION 函数名(参数列表) RETURNS 返回类型

        BEGIN

           declare c int default 0
           
           select count(*) into c from table
           
           return c;

        END

### 分区表
将一个表分成好几个区表

range 范围分区

list 类似range 不连续

hash 对用户的表达式所返回的返回值进行分区

key 类似hash 多列

示例
```java
create table test (
        id int 
) partition by range(id)(
        partition  p0 values less than(5)
)
```

主要目的缩小表检索范围


### 主从复制
mysql 的复制类型 基于sql 基于行数据的 混合模式
STATEMENT格式 优点：日志记录量相对较小，节约磁盘及网络I/0;
            
            缺点：对UUID(),USER(),这样的函数存在BUG
优点：使MySQL主从复制更加安全

缺点：记录日志量较大

3）MIXED格式

根据SQL语句由系统决定是基于段还是基于行来进行复制

推荐使用：binlog_format=row

1 线程 写入binlog 
2线程 读取并写入relaylog
3 线程写从库

策略 异步 主库不去管从库是否同步成功

半同步 只要一个从库接收到日志并写入relaylog就算成功

同步 当主库执行完一个事务所有的从库都同步完才返回


基于GTID复制的优缺点

1）什么是GTID

GTID即全局事务ID，其保证为每一个在主上提交的事务在复制集群中可以生成一个唯一的ID

GTID=source_id:transaction_id

2）优点：可以很方便的进行故障转移，从库不会丢失主库上的任何修改

3）缺点：故障处理比较复杂，对执行的SQL有一定的限制


主库配置

```java
server_id= 1 # 服务器id
log-bin=xxx # binlog的文件名
gtid_mode=on # gitd 模式

binlog_format= row

```
半同步复制需要安装插件

mysql主从模式默认是异步复制的，而MySQL Cluster是同步复制的，只要设置为相应的模式即是在使用相应的同步策略。

