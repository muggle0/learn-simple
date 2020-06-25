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


###

### 分区表

### 

