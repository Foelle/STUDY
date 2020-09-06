package com.imooc.season2carsystem;

public class Bus extends Car {
    private int capacity; //载人数


    public Bus(String name, int price ,int capacity) {
        super(name, price);
        this.capacity=capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
