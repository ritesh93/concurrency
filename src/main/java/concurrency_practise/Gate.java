package concurrency_practise;

public class Gate {
    private int maxCount;
    private int currentCount;
    Gate(int count){
        this.maxCount = count;
        this.currentCount = 0;
    }

    public void waitForMaxCount() throws InterruptedException {
        synchronized (this){
            currentCount++;
            System.out.println(Thread.currentThread().getName()+" incrementing current count "+currentCount);
            Thread.sleep(100);
            while(currentCount<maxCount){
                wait();
            }
        }
    }

    public void openGates(){
        while(currentCount < maxCount){
            try {
                System.out.println("waiting for the count");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this){
            System.out.println("opening the gates");
            notifyAll();
        }
    }
}
