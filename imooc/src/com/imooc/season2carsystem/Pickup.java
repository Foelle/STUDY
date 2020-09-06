package com.imooc.season2carsystem;

public class Pickup extends Car {
    private int cargo;  //载货量
    private int capacity; //载人数

    public Pickup(String name,int price,int capacity,int cargo){
        super(name,price);
        this.capacity=capacity;
        this.cargo=cargo;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public int getCargo() {
        return cargo;
    }
}
