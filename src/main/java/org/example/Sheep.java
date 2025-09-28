package org.example;

public abstract class Sheep extends Thread {
    protected String name;
    protected BridgeEntrance in;
    protected BridgeEntrance out;

    public Sheep(String name, BridgeEntrance in, BridgeEntrance out){
        this.name = name;
        this.in = in;
        this.out = out;
    }

}
