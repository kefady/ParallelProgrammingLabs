public class SynchronizationMonitor {
    private int flag = 0;
    private final int permits;

    public SynchronizationMonitor(int permits) {
        this.permits = permits;
    }

    public synchronized void signal() {
        flag++;
        if (flag >= permits) {
            notifyAll();
        }
    }

    public synchronized void waitSignal() {
        try {
            if (flag < permits) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
