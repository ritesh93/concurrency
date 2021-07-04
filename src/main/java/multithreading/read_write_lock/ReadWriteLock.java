package multithreading.read_write_lock;

public class ReadWriteLock {
	private boolean isWriteLocked=false;
	private int readers;
	
	public synchronized void acquireWriteLock() throws InterruptedException {
		while(isWriteLocked || readers!=0) {
			wait();
		}
		isWriteLocked = true;
	}
	
	public synchronized void releaseWriteLock() {
		isWriteLocked = false;
		notify();
	}
	
	public synchronized void acquireReadLock() throws InterruptedException {
		while(isWriteLocked) {
			wait();
		}
		readers++;
	}
	
	public synchronized void releaseReadLock() {
		readers--;
		notify();
	}
}
