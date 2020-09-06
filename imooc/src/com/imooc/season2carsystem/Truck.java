package com.imooc.season2carsystem;

public class Truck extends Car {
    private int cargo;    //载货量

    public Truck(String name, int price,int cargo) {
        super(name, price);
        this.cargo=cargo;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
}
