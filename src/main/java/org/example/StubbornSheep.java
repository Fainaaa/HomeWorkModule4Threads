package org.example;

public class StubbornSheep extends Sheep{
    private boolean needSleep;
    public StubbornSheep(String name, BridgeEntrance in, BridgeEntrance out, boolean needSleep) {
        super(name, in, out);
        this.needSleep = needSleep;
    }

    @Override
    public void run() {
        System.out.println(TimeViewer.view() + "\t" +  name + " подошел к мосту");
        try {
            in.lockInterruptibly();
            System.out.println(TimeViewer.view() + "\t" + name + " занял " + in);
            if(needSleep) {
                Thread.sleep(300);
            }
            out.lockInterruptibly();
            System.out.println(TimeViewer.view() + "\t" + name + " занял " + out);
            System.out.println(TimeViewer.view() + "\t" + name + " прошел мост");
        }
        catch (InterruptedException e) {
            System.out.println(TimeViewer.view() + "\tПроизошло исключение в методе Run объекта " + name + ", т.к. его " +
                    "работа была прервана в классе Main. Это исключение не вызывает остановку программы, " +
                    "чтобы мы могли перейти к следующему заданию");
        }
    }
}
