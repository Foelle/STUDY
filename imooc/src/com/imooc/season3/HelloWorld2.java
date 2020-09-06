package com.imooc.season3;

public class HelloWorld2 {
    public static void main(String[] args) {
        String s ="ajefrueoaeifblafoafeoaqkdanieavuavem";

        int num=0;
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)=='a'){
                num++;
            }
        }
        System.out.println("字符a的出现次数："+num);
    }
}
