## MySQL基础

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

#### 1.基本语句：

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
#基本语句
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
#基本语句
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
#基本语句
SELECT DISTINCT 列名1,列名2 FROM 表名;
#实例
SELECT DISTINCT country FROM Websites;
```

#### 3.WHERE子句和AND&OR运算符

##### （1）WHERE子句

WHERE 子句用于提取那些满足指定条件的记录。

```mysql
#基本语句
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
#基本语句
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
#基本语句
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
#基本语句
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
#基本语句
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

### 五、数据库及表的修改和删除

#### 1.删除数据库

```mysql
#显示所有数据库后删除特定数据库
SHOW DATABASES;
DROP DATABASE xxxx;
```

#### 2.对一张表的修改

##### （1）重命名一张表

```mysql
#三种基本语句
RENAME TABLE 原名 TO 新名字;
ALTER TABLE 原名 RENAME 新名;
ALTER TABLE 原名 RENAME TO 新名;

#实例说明
use mysql_shiyan;
RENAME TABLE table_1 TO table_2;
```

##### （2）删除一张表

```mysql
#基本语句
DROP TABLE 表名字;

#实例说明
DROP TABLE table_2;
```

#### 3.对一列的修改（即对表结构的修改）

##### （1）增加一列

```mysql
#两种基本语句
ALTER TABLE 表名字 ADD COLUMN 列名字 数据类型 约束;
ALTER TABLE 表名字 ADD 列名字 数据类型 约束;

#实例说明
ALTER TABLE employee ADD height INT(4) DEFAULT 170;
/*语句中的 INT(4) 不是表示整数的字节数，而是表示该值的显示宽度，如果设置填充字符为 0，则 170 显示为 0170*/
```

新增加的列，被默认放置在这张表的**最右边**。如果要把增加的列插入在指定位置，则需要在语句的最后**使用 AFTER** 关键词，如果想放在第一列的位置，则**使用 `FIRST`** 关键词。

```mysql
#指定增加列的插入某位置后面
ALTER TALBLE employee ADD weight INT(4) DEFAULT 120 AFTER age;

#指定插入第一列
ALTE TABLE employee ADD test INT(10) DEFAULT 11 FIRST;
```

##### （2）删除一列

```mysql
#两种基本语句
ALTER TABLE 表名字 DROP COLUMN 列名字;
ALTER TABLE 表名字 DROP 列名字;

#实例说明
ALTER TABLE employee DROP test;
```

##### （3）重命名一列

```mysql
#基本语句
ALTER TABLE 表名字 CHANGE 原列名 新列名 数据类型 约束;
#这条重命名语句后面的 “数据类型” 不能省略，否则重命名失败

#实例说明：“height” 一列重命名为汉语拼音 “shengao” 
ALTER TABLE employee CHANGE height shengao INT(4) DEFAULT 170;
```

##### （4）改变数据类型

**重命名CHANGE语句**可以修改一列的数据类型，只要原列名与新列名相同即可用于修改数据类型和约束。

```mysql
#基本语句
ALTER TABLE 表名字 MODIFY 列名字 新数据类型;
```

#### 4.对表的内容修改

##### （1）修改表中的某个值

```mysql
#基本语句
UPDATE 表名字 SET 列1=值1,列2=值2 WHERE 条件;

#Tom 的 age 改为 21，salary 改为 3000
UPDATE employee SET age=21,salary=300 WHERE name='Tom';
```

##### （2）删除一行记录

```mysql
#基本语句
DELETE FROM 表名字 WHERE 条件;

#删除Tom的数据
DELETE FROM employee WHERE name='Tom';
```

### 六、其它操作

#### 1.索引

索引是一种与表有关的结构，它的作用相当于书的目录，可以根据目录中的页码快速找到所需的内容。

当表中有大量记录时，若要对表进行查询，没有索引的情况是全表搜索：将所有记录一一取出，和查询条件进行对比，然后返回满足条件的记录。这样做会执行大量磁盘 I/O 操作，并花费大量数据库系统时间。

在表中已建立索引，在索引中找到符合查询条件的索引值，通过索引值就可以快速找到表中的数据，可以**大大加快查询速度**。

```mysql
#两种创建索引的基本语句
ALTER TABLE 表名字 ADD INDEX 索引名 (列名);
CREATE INDEX 索引名 ON 表名 (列名);

##在employee表的id列上建立名为idx_id的索引
ALTER TABLE employee ADD INDEX idx_id (id);  

#在employee表的name列上建立名为idx_name的索引
CREATE INDEX idx_name ON employee (name);   

#查看新建的索引
SHOW INDEX FROM 表名字;
```

使用 SELECT 语句查询的时候，语句中 WHERE 里面的条件，会**自动判断有没有可用的索引**。

用户名(username)具有唯一性，并且格式具有较强的限制，我们可以给用户名加上一个唯一索引；一些字段不适合创建索引，比如性别，这个字段存在大量的重复记录无法享受索引带来的速度加成，甚至会拖累数据库，导致数据冗余和额外的 CPU 开销。

#### 2.视图

视图是从一个或多个表中导出来的表，是一种**虚拟存在的表**。

- 数据库中只存放了视图的定义，而没有存放视图中的数据，这些**数据存放在原来的表中**；

- 使用视图查询数据时，数据库系统会从原来的表中取出对应的数据；

- **视图中的数据依赖于原来表中的数据**，一旦表中数据发生改变，显示在视图中的数据也会发生改变；

- 在使用视图的时候，可以把它当作一张表。

  ```mysql
  #创建视图的基本语句
  CREATE VIEW 视图名(列a,列b,列c) AS SELECT 列1,列2,列3 FROM 表名字;
  ```

  **视图也可以建立在多张表上**，只需在 SELECT 语句中使用**子查询**或**连接查询**。

  ```mysql
  #创建一个简单的视图，名为 v_emp，包含v_name，v_age，v_phone三个列
  CREATE VIEW v_emp (v_name,v_age,v_phone) AS SELECT name,age,phone FROM employee;
  ```

#### 3.导入

导入有**纯数据文件**导入和**SQL文件**导入，

```mysql
//导入SQL文件的语法，文件路径
source *.sql
```

区别：数据文件导入方式只包含数据，导入规则由数据库系统完成；SQL 文件导入相当于执行该文件中包含的 SQL 语句，可以实现多种操作，包括删除，更新，新增，甚至对数据库的重建。

数据文件的导入，可以把文件的数据保存进一张表。

```mysql
LOAD DATA INFILE '文件路径和文件名' INTO TABLE 表名字;
```

实际在mysql中进行导入导出操作，由于mysql的安全策略，必须在指定路径下进行。

##### (1)在mysql终端查看路径变量

```mysql
mysql -uroot
mysql> show variables like '%secure%';
+--------------------------+-----------------------+
| Variable_name            | Value                 |
+--------------------------+-----------------------+
| require_secure_transport | OFF                   |
| secure_auth              | ON                    |
| secure_file_priv         | /var/lib/mysql-files/ |
+--------------------------+-----------------------+
3 rows in set (0.00 sec)
```

secure_file_priv 变量指定安全路径为`/var/lib/mysql-files/`  ，要导入数据文件，需要将该文件移动到安全路径下。

##### （2）移动到安全路径下

打开 Xfce 终端，输入命令拷贝 SQL6 文件夹到 `/var/lib/mysql-files/` 目录

```bash
sudo cp -a /home/shiyanlou/Desktop/SQL6 /var/lib/mysql-files/
```

使用命令 `sudo vim /var/lib/mysql-files/SQL6/in.txt` 查看 `in.txt` 文件中的内容：

<img src="https://doc.shiyanlou.com/document-uid600404labid76timestamp1531869274599.png" alt="in.txt文件内容" style="zoom:70%;" />

可以看到其中仅仅包含了数据本身，没有任何的 SQL 语句。

##### （3）确认没有导入前的数据内容

以 root 用户登录数据库，再连接 **mysql_shiyan** 数据库，查看没有导入数据之前，employee表中的数据

```bash
# 在Xfce 终端输入命令
mysql -u root

# 在 MySQL 控制台中输入命令
use mysql_shiyan
SELECT * FROM employee;
```

<img src="https://doc.shiyanlou.com/MySQL/sql-06-04.png" alt="img" style="zoom:80%;" />

##### (4)执行导入语句

```mysql
LOAD DATA INFILE '/var/lib/mysql-files/SQL6/in.txt' INTO TABLE employee;
```

<img src="https://doc.shiyanlou.com/document-uid600404labid76timestamp1529104481426.png" alt="img" style="zoom:75%;" />

#### 4.导出

```mysql
#导出的基本语句
SELECT 列1,列2 INTO OUTFILE  '文件路径和文件名' FROM 表名字;
#语句中 “文件路径” 之下不能已经有同名文件。
```

```mysql
#将employee表的数据导出到指定目录并命名为out.txt
SELECT * INTO OUTFILE '/var/lib/mysql-files/out.txt' FROM employee;
```

在Linux系统中，可以使用 gedit 可以查看导出文件 `/var/lib/mysql-files/out.txt` 的内容，或者在终端直接`sudo cat /var/lib/mysql-files/out.txt`命令查看。

#### 5.备份

**备份与导出**的**区别**：导出的文件只是保存数据库中的数据；而备份，则是把数据库的结构，包括数据、约束、索引、视图等全部另存为一个文件。

**mysqldump** 是 MySQL 用于备份数据库的实用程序。它主要产生一个 SQL 脚本文件，其中包含从头重新创建数据库所必需的命令 CREATE TABLE INSERT 等。

```bash
# Ctrl+D 退出 MySQL 控制台,再在终端中运行
mysqldump -u root 数据库名 > 备份文件名;   #备份整个数据库

mysqldump -u root 数据库名 表名字 > 备份文件名;  #备份整个表
```

```bash
#备份mysql_shiyan
cd /home/shiyanlou
mysqldump -u root mysql_shiyan > bak.sql;
```

<img src="https://doc.shiyanlou.com/MySQL/sql-06-07.png" alt="img" style="zoom:80%;" />

#### 6.恢复

```mysql
#第一种
#把MySQL-06.sql 文件中保存的mysql_shiyan数据库恢复
source /tmp/SQL6/MySQL-06.sql  

#第二种
CREATE DATABASE test;  #新建一个名为test的数据库

#退出mysql，在终端输入命令
mysql -u root test < bak.sql

mysql -u root;
use test  # 连接数据库 test
SHOW TABLES;  # 查看 test 数据库的表
SELECT * FROM employee; #查看employee表的恢复情况
```

#### 7.总结概念

- 索引：可以加快查询速度
- 视图：是一种虚拟存在的表
- 导入：从文件中导入数据到表
- 导出：从表中导出到文件中
- 备份：mysqldump 备份数据库到文件
- 恢复：从文件恢复数据库

## MySQL基础习题

**实验二**：新建一个名为 library 的数据库，包含 book、reader 两张表，根据自己的理解安排表的内容并插入数据。

```mysql
create database library;
use library;

create table book(
  book_id int primary key,
  book_name varchar(50) not null
);

create table reader(
  reader_id int primary key,
  reader_name varchar(50) not null
);
```

**实验三**：构建一个简易的成绩管理系统的数据库，来记录几门课程的学生成绩。数据库中有三张表分别用于记录学生信息、课程信息和成绩信息。

<img src="https://doc.shiyanlou.com/document-uid370051labid48timestamp1490234173345.png" alt="img" style="zoom: 67%;" />![img](https://doc.shiyanlou.com/document-uid370051labid48timestamp1490234378903.png)

<img src="https://doc.shiyanlou.com/document-uid370051labid48timestamp1490234378903.png" alt="img" style="zoom:75%;" />

<img src="https://doc.shiyanlou.com/document-uid370051labid48timestamp1490234181565.png" alt="img" style="zoom:75%;" />

**要求**：

1.MySQL 服务处于运行状态

2.新建数据库的名称为 gradesystem

3.gradesystem 包含三个表：student、course、mark；

- student 表包含 3 列：sid(主键)、sname、gender；
- course 表包含 2 列：cid(主键)、cname；
- mark 表包含 4 列：mid(主键)、sid、cid、score ，注意与其他两个表主键之间的关系。
- 建立表时注意 id 自增和键约束
- 每个表插入语句可通过一条语句完成

```bash
sudo service mysql start
mysql -u root
```

```mysql
CREATE DATABASE gradesystem;

use gradesystem

CREATE TABLE student(
    sid int NOT NULL AUTO_INCREMENT,
    sname varchar(20) NOT NULL,
    gender varchar(10) NOT NULL,
    PRIMARY KEY(sid)
    );

CREATE TABLE course(
    cid int NOT NULL AUTO_INCREMENT,
    cname varchar(20) NOT NULL,
    PRIMARY KEY(cid)
    );

CREATE TABLE mark(
    mid int NOT NULL AUTO_INCREMENT,
    sid int NOT NULL,
    cid int NOT NULL,
    score int NOT NULL,
    PRIMARY KEY(mid),
    FOREIGN KEY(sid) REFERENCES student(sid),
    FOREIGN KEY(cid) REFERENCES course(cid)
    );

INSERT INTO student VALUES(1,'Tom','male'),(2,'Jack','male'),(3,'Rose','female');

INSERT INTO course VALUES(1,'math'),(2,'physics'),(3,'chemistry');

INSERT INTO mark VALUES(1,1,1,80),(2,2,1,85),(3,3,1,90),(4,1,2,60),(5,2,2,90),(6,3,2,75),(7,1,3,95),(8,2,3,75),(9,3,3,85);
```

**实验四：** 使用连接查询的方式，查询出各员工所在部门的人数与工程数，工程数命名为 `count_project`。（连接 3 个表，并使用 `COUNT` 内置函数）

```mysql
SELECT name, people_num, COUNT(proj_name) AS count_project
  FROM employee, department, project
  WHERE in_dpt = dpt_name AND of_dpt = dpt_name
  GROUP BY name, people_num;
  
+------+------------+---------------+
| name | people_num | count_project |
+------+------------+---------------+
| Alex |         11 |             1 |
| Jack |         12 |             2 |
| Jim  |         11 |             1 |
| Jobs |         12 |             2 |
| Joe  |         12 |             2 |
| Ken  |         11 |             1 |
| Mary |         12 |             2 |
| Mike |         15 |             2 |
| Rick |         10 |             1 |
| Rose |         10 |             1 |
| Tom  |         15 |             2 |
| Tony |         10 |             1 |
+------+------------+---------------+
12 rows in set (0.01 sec)
```

从三张表选择三列数据；第三行设置三列数据的关系，`dpt_name` 是唯一值，`of_dpt` 和 `in_dpt` 的值必取自 `dpt_name` 这列，它们应该有外键关系，但不是必须的；第四行分组，分组不可或缺，否则会出现无意义的数据（一般来说连接查询语句中有 `COUNT` 就会有 `GROUP BY`），报一个 `sql_mode` 引起的错误，至于为什么选择 `name` 和 `people_num` 分组，大家可以试一下同时去掉 `COUNT` 和 `GROUP BY` 语句，看看其中的差异即可理解。

**实验6：**建立员工名字 `employee.name` 和对应部门人数 `department.people_num` 的视图并展示。

```mysql
CREATE VIEW name_people_num (name, people_num)
  AS SELECT name, people_num FROM employee, department
  WHERE in_dpt = dpt_name;
```

```mysql
SELECT * FROM name_people_num;
+------+------------+
| name | people_num |
+------+------------+
| Alex |         11 |
| Ken  |         11 |
| Mike |         11 |
| Jack |         12 |
| Jobs |         12 |
| Joe  |         12 |
| Mary |         12 |
| Tony |         10 |
| Rose |         10 |
| Rick |         10 |
| Tom  |         15 |
| Jim  |         15 |
+------+------------+
12 rows in set (0.00 sec)
```

