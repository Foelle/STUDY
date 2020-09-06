package com.imooc.season3;

public class HelloWorld {
    public static void main(String[] args) {
        //Java文件名
        String fileName = "HellloWorld.java";
        //邮箱
        String email = "laurenyan@imooc.com";
        //判断  .java文件名是否正确，合法的文件名应该以.java结尾
        /*
        参考步骤：
        1、获取文件名中最后一次出现 “.”的位置索引
        2、根据 “.” 的位置索引，获取文件的后缀内容
        3、判断"."位置索引值是否正常以及文件后缀名的内容
        */

        //获取文件名中最后一次出现 “.”的位置索引
        int index =fileName.indexOf('.');

        //获取文件的后缀内容
        String prefix = fileName.substring(index+1,fileName.length());

        //判断必须包含”."且其位置不能出现在首位以及文件后缀名为”java“
        if (index>0 && prefix.equals("java")){
            System.out.println("Java文件名正确");
        }else{
            System.out.println("Java文件名无效");
        }

        //判断邮箱格式是否正确：合法的邮箱名中至少包含了”@“,并且"@"是在"."之前
        /*
        参考步骤：
        1、获取文件名中出现 “@”的位置索引
        2、获取邮箱中"."的位置索引
        3、判断"@"的位置索引值是否正常以及"@"是在"."之前
        */


        //获取文件名中出现 “@”的位置
        int index2 =email.indexOf('@');

        //获取邮箱中"."的位置索引
        int index3 =email.indexOf('.');

        //判断必须包含"@"且"@"是在"."之前
        if(index2!=-1&&index3>index2){
            System.out.println("邮箱格式正确");
        }else{
            System.out.println("邮箱格式无效");
        }
    }
}
