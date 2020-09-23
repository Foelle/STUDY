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

#### 1.基本语法：

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

 [点击查看](https://www.runoob.com/mysql/mysql-functions.html)

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

设置UNIQUE约束，该表中的指定列的值不能有重复值，否则插入数据时INSERT失败

```mysql
#基本语法
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

一个表可以有多个外键，每个外键必须 REFERENCES (参考) 另一个表的主键，被外键约束的列，取值必须在它参考的列中有对应值。

```mysql
#实例
CREATE TABLE Orders
(
O_Id int NOT NULL,
OrderNo int NOT NULL,
P_Id int,
PRIMARY KEY (O_Id),
FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
);
#"Orders" 表中的 "P_Id" 列指向 "Persons" 表中的 "P_Id" 列
#"Persons" 表中的 "P_Id" 列是 "Persons" 表中的 PRIMARY KEY
#"Orders" 表中的 "P_Id" 列是 "Orders" 表中的 FOREIGN KEY

#重命名且定义多个列的 FOREIGN KEY 约束
CREATE TABLE Orders
(
O_Id int NOT NULL,
OrderNo int NOT NULL,
P_Id int,
PRIMARY KEY (O_Id),
CONSTRAINT fk_PerOrders FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
);
```

```mysql
#表已被创建
ALTER TABLE Orders
ADD FOREIGN KEY (P_Id) REFERENCES Persons(P_Id);
#表已创建，重命名且定义多个
ALTER TABLE Orders
ADD CONSTRAINT fk_PerOrders
FOREIGN KEY (P_Id) REFERENCES Persons(P_Id);
#撤销FOREIGN KEY约束
ALTER TABLE Orders
DROP FOREIGN KEY fk_PerOrders;
```

### 四、SELECT语句详解

#### 1.基础SELECT语句

SELECT语句用于从数据库选取数据。

```mysql
#基本语法
SELECT 列名1,列名2 FROM 表名字 
[WHERE Clause]
[LIMIT number][OFFSET M]
#实例
SELECT * FROM employee;
SELECT name,age FROM employee;
```

WHERE 语句来包含任何条件， LIMIT 属性来设定返回的记录数，OFFSET指定SELECT语句开始查询的数据偏移量。默认情况下偏移量为0。

#### 2.SELECT DISTING语句

SELECT DISTINCT 语句用于返回唯一不同的值，即去掉列的重复值，列出不同的值。

```mysql
#基本语法
SELECT DISTINCT 列名1,列名2 FROM 表名;
#实例
SELECT DISTINCT country FROM Websites;
```

#### 3.WHERE子句和AND&OR运算符

##### （1）WHERE子句

WHERE 子句用于提取那些满足指定条件的记录。

```mysql
#基本语法
SELECT 列名1, field2 FROM 表名1, table_name2
[WHERE condition1 [AND [OR]] condition2;
```

WHERE 子句中可以指定任何条件，可以使用 AND 或者 OR 指定一个或多个条件，也可以运用于 SQL 的 DELETE 或者 UPDATE 命令。WHERE类似于是if语句，根据 MySQL 表中的字段值来读取指定的数据。

**运算符列表**：

| 运算符  | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| =       | 等号，检测两个值是否相等，如果相等返回true                   |
| <>,!=   | 不等于，检测两个值是否相等，如果不相等返回true               |
| >       | 大于号，检测左边的值是否大于右边的值, 如果左边的值大于右边的值返回true |
| <       | 小于号，检测左边的值是否小于右边的值, 如果左边的值小于右边的值返回true |
| >=      | 大于等于号，检测左边的值是否大于或等于右边的值, 如果左边的值大于或等于右边的值返回true |
| <=      | 小于等于号，检测左边的值是否小于或等于右边的值, 如果左边的值小于或等于右边的值返回true |
| BETWEEN | 在某个范围内                                                 |
| LIKE    | 搜索某种模式                                                 |
| IN      | 指定对某个列的多个可能值                                     |

```mysql
#查找工资为3500的员工
SELECT * from employee WHERE salary=3500;

#使用 BINARY 关键字来设定 WHERE 子句的字符串比较是区分大小写的
SELECT * from employee WHERE BINARY in_dpt='dpt1'; //有数据显示
SELECT * from employee WHERE BINARY in_dpt='DPT1'; //该查询无数据显示
```

##### （2）AND&OR运算符

**AND运算符实例**

```mysql
#筛选出从 "Websites" 表中选取国家为 "CN" 且alexa排名大于 "50" 的所有网站
SELECT * FROM Websites
WHERE country='CN' AND alexa > 50;

#筛选出 age 大于 25，且 age 小于 30,包含25和30
SELECT name,age FROM employee 
WHERE age BETWEEN 25 AND 30;
```

**OR运算符实例**

```mysql
#筛选出 age 小于 25，或 age 大于 30
SELECT name,age FROM employee 
WHERE age<25 OR age>30;
```

**结合AND&OR**

```mysql
#"Websites" 表中选取 alexa 排名大于 "15" 且国家为 "CN" 或 "USA" 的所有网站
SELECT * FROM Websites
WHERE alexa > 15 AND (country='CN' OR country='USA');
```

#### 4.IN & NOT IN操作符

IN 操作符允许您在 WHERE 子句中规定多个值， **IN** 和 **NOT IN** 用于筛选“在”或"不在"某个范围内的结果

```mysql
#基本语法
SELECT column_name(s) FROM table_name
WHERE column_name IN (value1,value2,...);

#IN的实例
#查询在 dpt3 或 dpt4 的人
SELECT name,age,phone,in_dpt FROM employee 
WHERE in_dpt IN ('dpt3','dpt4');
#转换成用 '=' 实现
SELECT name,age,phone,in_dpt FROM employee 
WHERE in_dpt='dpt3'OR in_dpt='dpt4';

#NOT IN的实例
#查询出了不在 dpt1 也不在 dpt3 的人：
SELECT name,age,phone,in_dpt FROM employee 
WHERE in_dpt NOT IN ('dpt1','dpt3');
```

#### 5.通配符

##### （1）LIKE子句

LIKE 操作符用于在 WHERE 子句中搜索列中的指定模式

```mysql
#基本语法
SELECT field1, field2,...fieldN FROM table_name
WHERE field1 LIKE condition1 [AND [OR]] filed2 = 'somevalue'

#查找首字母为J的人
SELECT name,age,phone FROM employee 
WHERE name LIKE 'J%';

#查找出了 1101 开头的 6 位数电话号码
SELECT name,age,phone FROM employee 
WHERE phone LIKE '1101__';
```

LIKE通常与 _ 、%一同使用，类似于一个元字符的搜索，使用AND或OR指定一个或多个条件，DELETE或UPDATE命令使用WHERE...LIKE子句来指定条件。

##### (2) [charlist] 通配符

MySQL中使用REGEXP或NOT REGEXP运算符（或RLIKE和NOT RLIKE）来操作正则表达式

```mysql
#查找name开头以“J”、"A"或"s"开头的所有员工：
SELECT * FROM employee 
WHERE name REGEXP '^[JAs]';

#查找以A到H字母开头的员工：
SELECT * FROM employee
WHERE name REGEXP '^[A-H]';

#查找不以A到H字母开头的员工：
SELECT * FROM employee
WHERE name REGEXP '^[^A-H]';

SELECT * FROM employee
WHERE name NOT REGEXP '^[A-H]';
```

#### 6.排序ORDER BY

ORDER BY使用 ASC 或 DESC 关键字来设置查询结果是按升序或降序排列。 默认情况下，它是按升序排列，使用WHERE...LIKE子句来设置条件。

```mysql
#基本语法
SELECT field1, field2,...fieldN FROM table_name1, table_name2...
ORDER BY field1 [ASC [DESC][默认 ASC]], [field2...] [ASC [DESC][默认 ASC]]

#按照salary降序排列
SELECT name,age,salary,phone FROM employee 
ORDER BY salary DESC;

#按照聘用时间升序排列
SELECT name,age,salary,phone FROM employee 
ORDER BY employdate;
```

ORDER多列排序时，先按照第一个column name排序，再按照第二个column name排序。第一个column name同属时，按照第二个column name排序。其中**desc** 或者 **asc** 只对它紧跟着的那一个列名有效，其他不受影响，仍然是默认的升序。

```mysql
#多列排序
#employdate降序排列，salary升序排列
SELECT name,age,salary,phone FROM employee 
ORDER BY employdate desc,salary;
```

#### 7.内置函数

[点击查看详细函数列表](https://www.runoob.com/mysql/mysql-functions.html)

##### 常用的内置函数

| 函数名 | COUNT | SUM  | AVG      | MAX    | MIN    |
| ------ | ----- | ---- | -------- | ------ | ------ |
| 作用   | 计数  | 求和 | 求平均数 | 最大数 | 最小数 |

其中 **COUNT** 函数可用于任何数据类型(因为它只是计数)，而 **SUM** 、**AVG** 函数都只能对数字类数据类型做计算，**MAX** 和 **MIN** 可用于数值、字符串或是日期时间数据类型。

```mysql
#计算出salary的最大值、最小值，其中AS关键词给最大值重命名为max_salary
SELECT MAX(salary) AS max_salary,
MIN(salary) FROM employee;
```

#### 8.分组GROUP BY和HAVING子句

##### （1）GROUP BY

**GROUP BY** 语句根据一个或多个列对结果集进行分组，在分组的列上我们可以使用 COUNT, SUM, AVG,等函数。

```mysql
#基本语法
SELECT column_name, function(column_name) 
FROM table_name
WHERE column_name operator value
GROUP BY column_name;

#统计access_log各个site_id的访问量
SELECT site_id, SUM(access_log.count) AS nums
FROM access_log GROUP BY site_id;
+----------+----------+
| site_id  |   nums   |
+--------+------------+
|        1 |       275|
|        2 |        10|
|        3 |       521|
+--------+------------+
3 rows in set (0.01 sec)
```

**WITH ROLLUP** 可以实现在分组统计数据基础上再进行相同的统计（SUM,AVG,COUNT…）

```mysql
#将以上的数据表按名字进行分组，再统计每个人登录的次数
#其中记录 NULL 表示所有人的登录次数
SELECT name, SUM(singin) as singin_count 
FROM  employee_tbl 
GROUP BY name WITH ROLLUP;
+--------+--------------+
| name   | singin_count |
+--------+--------------+
| 小丽   |             2|
| 小明   |             7|
| 小王   |             7|
| NULL   |            16|
+--------+--------------+
4 rows in set (0.00 sec)


#coalesce设置取代NULL的名称
#如果a==null,则选择b；如果b==null,则选择c；
#如果a!=null,则选择a；如果a b c 都为null ，则返回为null（没意义）
select coalesce(a,b,c);

SELECT coalesce(name, '总数'), SUM(singin) as singin_count 
FROM  employee_tbl 
GROUP BY name WITH ROLLUP;
+--------------------------+--------------+
| coalesce(name, '总数') | singin_count |
+--------------------------+--------------+
| 小丽                   |            2 |
| 小明                   |            7 |
| 小王                   |            7 |
| 总数                   |           16 |
+--------------------------+--------------+
4 rows in set (0.01 sec)
```

##### （2）HAVING子句

内容待定，需要参考《MySQL必知必会》这本书

#### 8.子查询

```mysql
#处理多个表时，子查询只有在结果来自一个表时才有用
#员工信息储存在 employee 表中，但工程信息储存在 project 表中
#查询名为 "Tom" 的员工所在部门做了几个工程
SELECT of_dpt,COUNT(proj_name) AS count_project 
FROM project GROUP BY of_dpt
HAVING of_dpt IN
(SELECT in_dpt FROM employee WHERE name='Tom');
#第二个 SELECT 语句将返回一个集合的数据形式，然后被第一个 SELECT 语句用 in 进行判断
```



#### 9.连接查询

```mysql
#连接的基本思想是把两个或多个表当作一个新的表来操作
#显示两个表或多个表中的数据

#使用JOIN ON语法，查询各员工所在部门的人数
SELECT id,name,people_num
FROM employee JOIN department
ON employee.in_dpt = department.dpt_name
ORDER BY id;
```

