package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class BridgeEntrance extends ReentrantLock {
    private String name;

    public BridgeEntrance(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
