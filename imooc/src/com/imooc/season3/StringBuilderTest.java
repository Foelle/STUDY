package com.imooc.season3;

public class StringBuilderTest {
    public static void main(String[] args) {
        //需要创建一个内容可变的字符串对象，应优先考虑使用 StringBuilder 类
        StringBuilder str=new StringBuilder();
        //追加字符串
        str.append("jaewkjldfxmopzdm");
        //从后往前每隔三位插入逗号
        for(int i=str.length()-3;i>0;i=i-3){
            str.insert(i,",");
        }

        //将StringBuilder对象转换成String对象并输出
        System.out.println(str.toString());
    }
}
