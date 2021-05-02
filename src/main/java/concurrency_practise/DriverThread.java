package concurrency_practise;


public class DriverThread extends Thread{
    public static void main(String[] args){
        SavingAccount account = new SavingAccount(100);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    System.out.println(Thread.currentThread().getName() +"WithDraw 20 rupees");
                    account.withDraw(20);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread husbandThread = new Thread(runnable, "husband");
        Thread wifeThread = new Thread(runnable, "wife");
        husbandThread.start();
        wifeThread.start();
    }
}

class SavingAccount {

    private volatile int balance;

    SavingAccount(int balance){
        this.balance = balance;
    }

    public void withDraw(int amount){
        synchronized (this){
            if(amount <= balance){
                balance -= amount;
                System.out.println(Thread.currentThread().getName()+" current  balance"+ balance);
            }
            else{
                System.out.println(Thread.currentThread().getName()+" Low  balance"+ balance);
            }
        }
    }
}
