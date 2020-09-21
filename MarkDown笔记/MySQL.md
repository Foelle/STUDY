## MySQL

### 一、MySQL管理

#### 1. 启动和关闭MySQL服务

##### Windows系统下：

```plain text
#以管理员身份打开cmd
net start mysql
net stop mysql
```

##### Linux系统下：

```plain text
#安装MySQL服务端、核心程序
sudo apt-get install mysql-server
#安装MySQL客户端
sudo apt-get install mysql-client
#根据提示确认输入 YES，设置 root 用户密码（之后也可以修改）等，稍等片刻便可安装成功
#验证是否安装并启动成功，输入密码后出现mysql:LISTEN则安装成功
sudo netstat -tap | grep mysql
#修改MySQL的配置文件（my.cnf），找对应目录下的my.cnf
sudo gedit /etc/mysql/my.cnf
```
###### 使用servic服务

```plain text
#启动MySQL服务
sudo service mysql start
#root用户登录
mysql -u root -p
#关闭MySQL服务
sudo service mysql stop
#重启MySQL服务
sudo service mysql restart
```
#### 2.用户设置

```mysql
#连接数据库
use mysql;
#给指定数据库mysql添加用户mysql，密码,授予相应的权限
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP
ON TUTORIALS.*
TO 'mysql'@'localhost'
IDENTIFIED BY 'mysql123';
#退出mysql命令框
exit
```

#### 3.MySQL文件默认配置

##### Windows系统下：

```plain text
[mysqld] 
# 设置 3306 端口 
port=3306 
# 设置 mysql 的安装目录 
basedir=D:\workware\mysql-8.0.21-winx64
# 设置 mysql 数据库的数据的存放目录
datadir=D:\workware\mysql-8.0.21-winx64\data
# 允许最大连接数 
max_connections=200 
# 允许连接失败的次数。这是为了防止有人从该主机试图攻击数据库系统 
max_connect_errors=10 
# 服务端使用的字符集默认为 UTF8 
character-set-server=utf8mb4 
# 创建新表时将使用的默认存储引擎 
default-storage-engine=INNODB 
# 默认使用“mysql_native_password”插件认证 
default_authentication_plugin=mysql_native_password 
[mysql] 
# 设置 mysql 客户端默认字符集 
default-character-set=utf8mb4
[client] 
# 设置 mysql 客户端连接服务端时默认使用的端口 
port=3306 
default-character-set=utf8mb4
```

##### 4.管理MySQL的命令

```mysql
#列出所有数据库
SHOW DATABASES;
#连接数据库
use mysql;
#删除数据库
drop database mysql;
#列出指定数据库的所有表
SHOW TABLES;
#显示数据表的属性、类型、主键、约束等信息
SHOW COLUMNS FROM mysql_tb1;
#显示数据表的详细索引信息，包括主键
SHOW INDEX FROM mysql_tab1;
#显示数据库 mysql 中所有表的信息
SHOW TABLE STATUS FROM mysql;
# 表名以mysql开头的表的信息
SHOW TABLE STATUS from mysql LIKE 'mysql%';
# 加上 \G，查询结果按列打印
SHOW TABLE STATUS from mysql LIKE 'mysql%'\G;
```

### 二、创建数据库并插入数据

#### 1.通用语法：

```mysql
#创建数据库
CREATE DATABASE 数据库名;
#创建数据表
#第一种是创建数据表时判断是否存在，存在则相当于插入insert语句，不存在则创建
#数据表存在的时候不创建也不插入数据，则先drop table
#drop table if exists '数据表名';
CREATE TABLE IF NOT EXISTS '数据表名'
(
    '列名a' 数据类型(数据长度),
    '列名b' 数据类型(数据长度),
    PRIMARY KEY ('列名a')
);
CREATE TABLE 数据表名
(
    列名a 数据类型(数据长度),
    列名b 数据类型(数据长度),
    PRIMARY KEY (列名a)
);
#插入数据，字符型的值使用单引号或双引号
INSERT INTO 表的名字(列名a,列名b,列名c)
VALUES(值1,值2,值3);
```

#### 2.实例：

```mysql
#创建数据库MYSQL
CREATE DATABASE MYSQL;
#连接数据库MYSQL
use MYSQL;
#创建数据表
CREATE TABLE mysql_tab1
(
    mysql_id INT NOT NULL AUTO_INCREMENT,
    mysql_title VARCHAR(100) NOT NULL,
    mysql_author VARCHAR(40) NOT NULL,
    submission_date DATE,
    PRIMARY KEY (mysql_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#AUTO_INCREMENT定义列为自增的属性，一般用于主键，数值自动加1
#PRIMARY KEY关键字用于定义列为主键。 您可以使用多列来定义主键，列间以逗号分隔
#ENGINE 设置存储引擎，CHARSET 设置编码
```

#### 3.数据类型：

 暂时参考：https://www.runoob.com/mysql/mysql-data-types.html

### 三、SQL的约束

#### 1.常用约束：

| 约束类型 |    主键     | 默认值  |  唯一  |    外键     |   非空   |
| :------: | :---------: | :-----: | :----: | :---------: | :------: |
|  关键字  | PRIMARY KEY | DEFAULT | UNIQUE | FOREIGN KEY | NOT NULL |

#### 2.约束语法

##### （1）主键

主键必须包含唯一的值，主键列不能包含 NULL 值。每个表都应该有一个主键，并且每个表只能有一个主键。

```mysql
#定义主键
CREATE TABLE Persons
(
    P_Id INT(10) NOT NULL,
    PRIMARY KEY(P_Id)
);
#或
CREATE TABLE Persons
(
    P_Id INT(10) PRIMARY KEY
);
#定义且重命名
CREATE TABLE Persons
(
    P_Id INT(10),
    CONSTRAINT pk_PersonID PRIMARY KEY(P_Id)
);

#复合主键
#其中pk_PersonID为主键名字（自定义的）,复合主键:P_Id,LastName
CONSTRAINT pk_PersonID PRIMARY KEY(P_Id,LastName)
```

当表已经创建时，需要给某列创建PRIMARY约束，某列必须在创建时声明过NOT NULL

```mysql
#单独某列时
ALTER TABLE Persons
ADD PRIMARY KEY (P_Id);
#重命名PRIMARY KEY约束，并定义多个列的PRIMARY KEY约束
ALTER TABLE Persons
ADD CONSTRAINT pk_PersonID PRIMARY KEY (P_Id,LastName);
#撤销PRIMARY KEY约束
ALTER TABLE Persons
DROP PRIMARY KEY;
```

##### （2）默认值约束

默认值约束 (DEFAULT) 规定，当有 DEFAULT 约束的列，插入数据为空时，将使用默认值。 INSERT 语句中，如果被 DEFAULT 约束的位置没有值，那么这个位置将会被 DEFAULT 的值填充。

```mysql
#创建表时，设置默认值约束的用法
CREATE TABLE Persons
(
    people_num INT(10) DEFAULT 10,
    City varchar(255) DEFAULT 'Sandnes',
    OrderDate date DEFAULT GETDATE()
)
#表已被创建时,将Persons表的City列默认值修改
ALTER TABLE Persons
ALTER City SET DEFAULT 'SANDNES'
#撤销DEFAULT约束
ALTER TABLE Persons
ALTER City DROP DEFAULT;
```

##### （3）唯一约束

```mysql
#该表中的指定列的值不能有重复值，否则插入数据时INSERT失败
UNIQUE(列名)
#重命名UNIQUE约束且定义多个列的UNIQUE约束
CONSTRAINT uc_PersonID UNIQUE (P_Id,LastName)
#表已被创建时,单列
ALTER TABLE Persons
ADD UNIQUE (P_Id);
#表已被创建时,重命名且多列
ALTER TABLE Persons
ADD CONSTRAINT uc_PersonID UNIQUE (P_Id,LastName);
#撤销唯一约束
ALTER TABLE Persons
DROP INDEX P_ID;
```

##### （4）非空约束

默认情况下，表的列是接受NULL值。NOT NULL 约束强制列不接受 NULL 值，字段始终包含值。MySQL中违反非空约束，会报错。

```mysql
#实例
CREATE TABLE Persons (
    ID int NOT NULL,
    LastName varchar(255) NOT NULL,
    FirstName varchar(255) NOT NULL,
    Age int
);
#已创建的表的列"Age"字段中添加NOT NULL约束
ALTER TABLE Persons
MODIFY Age int NOT NULL;
#撤销NOT NULL约束
ALTER TABLE Persons
MODIFY Age int NULL;
```

##### （5）外键约束

