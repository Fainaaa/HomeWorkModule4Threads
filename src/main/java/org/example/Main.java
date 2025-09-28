package org.example;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println("\nДемонстрация DeadLock на примере двух упрямых барашков, пытающихся перейти мост\n");
            deadLockExample();
            Thread.sleep(1000);

            System.out.println("\nПоочередный вывод цифр 1 и 2, начиная с 1, потоки ждут друг друга\n");
            queueThreadWorkExample();
            Thread.sleep(1000);

            System.out.println("\nДемонстрация LiveLock на примере двух вежливых барашков, пытающихся перейти мост\n");
            liveLockExample();
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }

    }

    public static void deadLockExample(){
        BridgeEntrance northEntrance = new BridgeEntrance("Северный вход");
        BridgeEntrance southEntrance = new BridgeEntrance("Южный вход");
        StubbornSheep sheep1 = new StubbornSheep("Барашек 1", northEntrance, southEntrance, true);
        StubbornSheep sheep2 = new StubbornSheep("Барашек 2", southEntrance, northEntrance, false);
        sheep1.start();
        sheep2.start();

        try {
            Thread.sleep(3000);
            System.out.println("\n" + TimeViewer.view() + "\t" + "Немного дали потокам поработать");
            if (sheep1.isAlive() || sheep2.isAlive()) {
                System.out.println(TimeViewer.view() + "\t" + "Работа потоков не завершена, барашки заблокировали друг друга на мосту");

                System.out.println(TimeViewer.view() + "\t" + "Прерываем выполнение");
                sheep1.interrupt();
                sheep2.interrupt();
            } else {
                System.out.println(TimeViewer.view() + "\t" + "Работа потоков завершена");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void liveLockExample(){
        BridgeEntrance eastEntrance = new BridgeEntrance("Восточный вход");
        BridgeEntrance westEntrance = new BridgeEntrance("Западный вход");

        PoliteSheep sheep3 = new PoliteSheep("Барашек 3", eastEntrance, westEntrance);
        PoliteSheep sheep4 = new PoliteSheep("Барашек 4", westEntrance, eastEntrance);

        sheep3.start();
        sheep4.start();

        try {
            Thread.sleep(3000);
            System.out.println("\n" + TimeViewer.view() + "\t" + "Немного дали потокам поработать");
            if (sheep3.isAlive() || sheep4.isAlive()) {
                System.out.println(TimeViewer.view() + "\t" + "Работа потоков не завершена, барашки не блокируют друг " +
                        "друга намертво, но и пройти мост целиком не могут, т.к. уступают друг другу постоянно");

                System.out.println(TimeViewer.view() + "\t" + "Прерываем выполнение");
                sheep3.interrupt();
                sheep4.interrupt();
            } else {
                System.out.println(TimeViewer.view() + "\t" + "Работа потоков завершена");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void queueThreadWorkExample(){
        Object object = new Object();
        AtomicBoolean needOne = new AtomicBoolean(true);
        Thread tr1 = new Thread(() -> {
            synchronized (object){
                try{
                    while (true){
                        if (!needOne.get())
                            object.wait();
                        else{
                            System.out.print("1 ");
                            needOne.set(false);
                            object.notify();
                        }
                    }
                }
                catch (InterruptedException e){
                    System.out.println("\n" + Thread.currentThread().getName() + " был прерван для демонстрации следующего задания");
                }
            }
        });

        Thread tr2 = new Thread(() -> {
            synchronized (object){
                try{
                    while (true){
                        if (needOne.get())
                            object.wait();
                        else{
                            System.out.print("2 ");
                            needOne.set(true);
                            object.notify();
                        }
                    }
                }
                catch (InterruptedException e){
                    System.out.println("\n" + Thread.currentThread().getName() + " был прерван для демонстрации следующего задания");
                }
            }
        });

        tr1.start();
        tr2.start();

        try {
            Thread.sleep(3000);
            tr1.interrupt();
            tr2.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }


}