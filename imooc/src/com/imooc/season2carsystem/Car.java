package com.imooc.season2carsystem;

public class Car {
    private String carName;//车的名字
    private int carprice;    //单价

    public Car(String name,int price){
        carName=name;
        carprice=price;

    }

    public String getCarName() {
        return carName;
    }

    public int getCarprice() {
        return carprice;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCarprice(int carprice) {
        this.carprice = carprice;
    }
}
