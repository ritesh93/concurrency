package concurrency_practise;

public class GateDriverThread {

    public static void main(String[] args){
        Gate gate = new Gate(3);
        Runnable r = () -> {
            try {
                gate.waitForMaxCount();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread t1 = new Thread(r, "t1");
        Thread t2 = new Thread(r, "t2");
        Thread t3 = new Thread(r, "t3");
        t1.start();
        t2.start();
        t3.start();
        Thread thd = new Thread(new Runnable() {
            @Override
            public void run() {
                gate.openGates();
            }
        }, "mainThread");
        thd.start();
    }
}
