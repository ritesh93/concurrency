package multithreading.read_write_lock;

public class Account {

	private int balance;
	
	Account(int balance){
		this.balance = balance;
	}
	
	public synchronized int getBalance() {
		return balance;
	}
	
	public synchronized void withdraw(int amount) {
		balance -= amount;
		System.out.println("withdraw amount " + amount + "balance "+ balance);
	}
	
	public static void main(String [] args) {
		final Account account = new Account(500);
		ReadWriteLock lock = new ReadWriteLock();
		Runnable r1 = ()-> {
			try {
				System.out.println("acquiring write lock");	
				lock.acquireWriteLock();
				account.withdraw(10);
				System.out.println("releasing write lock");
				lock.releaseWriteLock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		
		Runnable r2 = ()->{
			
			try {
				System.out.println("acquiring read lock");
				lock.acquireReadLock();
				System.out.println(account.getBalance());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		
		Runnable secondWrite = ()->{
			System.out.println("acquiring write lock");
			try {
				lock.acquireWriteLock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("write lock acquired");
			lock.releaseWriteLock();
		};
		
		Runnable secondRead = ()->{
			System.out.println("acquiring read lock");
			try {
				lock.acquireReadLock();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(account.getBalance());
		};
		
		Runnable releaseReadLock = ()->{
			lock.releaseReadLock();
			System.out.println("releasing read lock");
		};
		
		Thread writeThread = new Thread(r1);
		Thread readThread = new Thread(r2);
		Thread secondWriteThread = new Thread(r1);
		Thread secondReadThread = new Thread(secondRead);
		Thread releaseRead1 = new Thread(releaseReadLock);
		Thread releaseRead2 = new Thread(releaseReadLock);
		
		writeThread.start();
		readThread.start();
		secondReadThread.start();
		try {
			secondWriteThread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		secondWriteThread.start();
		
		releaseRead1.start();
		releaseRead2.start();
		
		try {
			writeThread.join();
			secondWriteThread.join();
			readThread.join();
			secondReadThread.join();
			releaseRead1.join();
			releaseRead2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
