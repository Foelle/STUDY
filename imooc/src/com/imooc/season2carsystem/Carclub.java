package com.imooc.season2carsystem;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Carclub {

    /* 根据所学知识,编写一个控制台版的"答答租车系统"
        功能:
        1、展示所有可租车辆
        2、选择车型、租车量
        3、展示租车清单，包含:总金额、总载货量及其车型或总载人量及其车型
        五种车型：1大客车，2大卡车，3小客车，4小卡车，5轿车
        请选择车辆->请输入该车编号->请选择数量->结算输出总金额
        块注释 Alt Shitf A
        */
    public static void main(String[] args) {
        Car availableCars[]=new Car[]{
                new Bus("奥迪A4",500,4),
                new Bus("长安",400,4),
                new Bus("雪铁龙",300,4),
                new Pickup("长城",350,2,3),
                new Pickup("黄河",370,2,3),
                new Truck("北安",400,5),
                new Truck("畅威",980,10)
        };

        System.out.println("欢迎使用某租车系统\n");

        Scanner input = new Scanner(System.in);
        System.out.println("是否需要租车业务？（1.是/2.否）");
        String zuche = input.nextLine();    //关于Java的获取输入问题 https://www.cnblogs.com/AIchangetheworld/p/11291336.html
        //此处需要输入异常处理
        while(zuche!=null){
            int chose = Integer.parseInt(zuche);  //String强制转换成int
            if (chose==1) {
                System.out.println("可供租选的车辆表如下：");
                for(int i=availableCars.length;i>0;--i){
                    Car car = availableCars[i];
                    System.out.println((i-1)+".\t");
                    System.out.println(car.getCarName()+"\t\t");
                    System.out.println(car.getCarprice()+"元/天\t");
                    if (availableCars[i] instanceof Truck) {
                        System.out.println("载货"+((Truck)car).getCargo()+"吨\n");
                    }else if (availableCars[i] instanceof Bus) {
                        System.out.println("载人"+((Bus)car).getCapacity()+"人\n");
                    }else if (availableCars[i] instanceof Pickup) {
                        System.out.print("载人："+((Pickup)car).getCapacity()+"人 载货："+((Pickup)car).getCargo()+"吨\n");
                    }
                }

                System.out.print("请输入您要租汽车的数量（0 ~ "+availableCars.length+"）：>>> ");
                int rentNumber = input.nextInt();
                /*此处需要进行异常处理，判定输入是否合法*/
                List<Integer> carsAlreadyPicked = new ArrayList<>();
                for (int i = 1; i <= rentNumber; i++) {
                    System.out.print("请输入第"+i+"辆车的序号：>>> ");
                    int carID = input.nextInt();
                    /*此处需要进行异常处理，判定输入是否合法*/
                    carsAlreadyPicked.add(carID);
                }

                System.out.print("请输入租车天数（> 0）：>>> ");
                int rentDay = input.nextInt();
                /*此处需要进行异常处理，判定输入是否合法*/

                int totalExpense = 0, totalBusCapacity = 0, totalCargo = 0;
                for (int id : carsAlreadyPicked) {
                    totalExpense += availableCars[id - 1].getCarprice();
                }
                totalExpense *= rentDay;
                System.out.println("您的账单：");
                System.out.println("***可载人的车有：");
                for (int id : carsAlreadyPicked) {
                    Car car = availableCars[id-1];
                    if (availableCars[id-1] instanceof Bus || availableCars[id-1] instanceof Pickup) {
                        System.out.print(car.getCarName()+"\t");
                        totalBusCapacity += (availableCars[id-1] instanceof Bus) ? ((Bus)car).getCapacity() : ((Pickup)car).getCapacity();
                    }
                }
                    System.out.print("共载人："+totalBusCapacity+"人\n");

                System.out.println("***载货的车有：");
                for (int id : carsAlreadyPicked) {
                    Car car = availableCars[id-1];
                    if (availableCars[id-1] instanceof Truck || availableCars[id-1] instanceof Pickup) {
                        System.out.print(car.getCarName()+"\t");
                        totalCargo += (availableCars[id-1] instanceof Truck) ? ((Truck)car).getCargo() : ((Pickup)car).getCargo();
                    }
                }
                System.out.print("共载货："+(double)totalCargo+"吨\n");

                System.out.print("***租车总价格：");
                System.out.print(totalExpense+"元\n");

                input.close();
                System.out.println("再见！欢迎下次使用！");

            } else if (chose==2){
                System.out.println("正在退出...请稍等");
                break;
            }
        }

        System.out.println("已退出租车系统！");
    }
}
