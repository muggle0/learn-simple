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
```

自定义变量 （用户变量和 局部变量）



### 分区表

### 

