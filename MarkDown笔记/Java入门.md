## Season 3

#### Chapter 1 异常与异常处理

#### Chapter 2 字符串

#### Chapter 3 Java中的常用类

#### Chapter 4 Java中的集合框架（上）

##### 4-1 集合框架概述

###### 集合的概念

➢Java中的集合类：是一种工具类，储存任意数量的具有共同属性的对象

###### 集合的作用

➢在类的内部，对数据进行组织；

➢简单而快速的搜索大数量的条目；

➢有的集合接口，提供了一系列排列有序的元素，并且可以在序列中间快速的插入或者删除有关元素；

➢有的集合接口，提供了映射关系，可以通过关键字（key）去快速查找到对应的唯一对象,而这个关键字可以是任意类型。

###### 集合与数组的区别

➢数组的长度固定,集合长度可变

➢数组只能通过下标访问元素,类型固定,而有的集合可以通过任意类型查找所映射的具体对象

###### 集合框架体系结构

![image-20200910222420064](C:\Users\Hsueh Sun\AppData\Roaming\Typora\typora-user-images\image-20200910222420064.png)

➢Collection和Map是两个根接口，List、Queue和Set是Collection的子接口；

➢List和Queue存储的元素排列有序，可以重复，而Set中的元素无序，不可重复；

➢List和Set比较常用，List--序列，Queue--队列，Set--集；

➢实现类：ArrayList---数组序列，LinkedList--链表，HashSet--哈希集，HashMap--哈希表；

➢Map类提供映射的关系，<key,value>两个对象为一个映射，存储数据。Entry类的实例为映射，Entry类是Map的内部类

##### 4-2 Collection接口&List接口简介

###### Collection接口

➢是List、 Set和Queue接口的父接口。

➢定义了可用于操作List、Set和Queue的方法——增删改查。

###### List接口及其实现类——ArrayList

➢List是元素有序并且可以重复的集合，被称为序列；

➢List可以精确的控制每个元素的插入位置,或删除某个位置元素。

➢ArrayList——数组序列，是List的一个重要实现类；

➢ArrayList底层是由数组实现的。

###### 实现功能——模拟学生选课功能

◆选择课程(往集合中添加课程)

◆删除所选的某门课程(删除集合中的元素)

◆查看所选课程

◆修改所选课程

##### 4-3 学生选课——创建学生类和课程类

###### 创建课程类

```java
package com.imooc.collection;

public class Course {
/*
* 课程类
* */
    public String id;
    public String name;

    public Course(String id,String name){
        this.id = id;
        this.name = name;
    }
}
```

实际开发中，需要对**属性私有化**，修改为

```java
package com.imooc.collection;

public class Course {
/*
* 课程类
* */

    /*实际开发代码，属性私有化*/
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
```

###### 创建学生类

```java
package com.imooc.collection;

import java.util.HashSet;
import java.util.Set;
/*
* 学生类
* */
public class Student {

    public String name;
    public String id;
    public Set courses;

    public Student(String id,String name){
        this.id = id;
        this.name = name;
        this.courses=new HashSet();//后续介绍Set相关
    }

}
```

##### 4-4 学生选课——添加课程 Ⅰ



#### Chapter 5 Java中的集合框架（中）

#### Chapter 6 Java中的集合框架（下）

#### Chapter 7 简易扑克牌游戏