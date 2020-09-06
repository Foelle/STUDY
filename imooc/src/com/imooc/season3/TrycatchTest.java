package com.imooc.season3;

public class TrycatchTest {
    public static void main(String[] args) {
        TrycatchTest tct =new TrycatchTest();
        int result = tct.test();
        System.out.println("test()执行完毕！返回值为："+result);
        int result2 = tct.test2();
        System.out.println("test2()执行完毕！返回值为："+result2);
        int result3 = tct.test3();
        System.out.println("test3()执行完毕！返回值为："+result2);
    }

    /*divider(除数)
     *result(结果)
     * try-catch捕获while循环
     * 每次循环，divider减一，result=result+100/divider
     * 如果：捕获异常，打印输出“异常抛出”,返回-1
     *否则：返回result
     * */
    public int test (){
        int divider = 10;
        int result = 100;
        try{
            while(divider > -1){
                divider--;
                result = result + 100/divider;
            }
            return result;
        }catch(Exception e){
            e.printStackTrace(); //打印toString()结果和栈层次到System.err，即错误输出流
            System.out.println("抛出异常！");
            return -1;
        }
    }

    /*divider(除数)
     *result(结果)
     * try-catch捕获while循环
     * 每次循环，divider减一，result=result+100/divider
     * 如果：捕获异常，打印输出“异常抛出”,返回99
     *否则：返回result
     * finally:打印输出"这是finally",同时打印输出result的值
     * */
    public int test2() {
        int divider = 10;
        int result = 100;
        try {
            while (divider > -1) {
                divider--;
                result = result + 100 / divider;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(); //打印toString()结果和栈层次到System.err，即错误输出流
            System.out.println("抛出异常！");
            return result = 999;
        } finally {
            System.out.println("这是finally的值");
            System.out.println("result的值为：" + result);
        }
    }

        /*divider(除数)
         *result(结果)
         * try-catch捕获while循环
         * 每次循环，divider减一，result=result+100/divider
         * 如果：捕获异常，打印输出“异常抛出”,返回99
         *否则：返回result
         * finally:打印输出"这是finally",同时打印输出result的值
         * 最后，返回1111作为结果
         * */
        public int test3(){
            int divider = 10;
            int result = 100;
            try{
                while(divider > -1){
                    divider--;
                    result = result + 100/divider;
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("循环抛出异常！");
            }finally{
                System.out.println("这是finally！");
                System.out.println("这是result的值："+result);
            }
            System.out.println("test3()已经运行完了~");
            return 1111;
        }


    }

