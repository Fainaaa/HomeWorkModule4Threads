package org.example;

public class PoliteSheep extends Sheep {
    public PoliteSheep(String name, BridgeEntrance in, BridgeEntrance out) {
        super(name, in, out);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(TimeViewer.view() + "\t" + name + " пытается пройти " + in);
            in.tryLock();
            System.out.println(TimeViewer.view() + "\t" + name + " проходит " + in);
            try {
                Thread.sleep(100);
                System.out.println(TimeViewer.view() + "\t" + name + " пытается пройти " + out);
                if (out.tryLock()) {
                    System.out.println(TimeViewer.view() + "\t" + name + " проходит " + out);
                    System.out.println(TimeViewer.view() + "\t" + name + " прошел мост.");
                    out.unlock();
                    in.unlock();
                    return;
                } else {
                    System.out.println(TimeViewer.view() + "\t" + name + " не получает доступ к " + out + " и поэтому " +
                            "уступает и " + in + ", полностью освобождя мост");
                    Thread.sleep(100);
                    in.unlock();
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException("Произошло исключение в методе Run объекта " + name + ", \nт.к. его " +
                        "работа была прервана в классе Main. \nЭто исключение вызывает остановку программы, " +
                        "т.к. это последнее задание");
            }
        }
    }
}
