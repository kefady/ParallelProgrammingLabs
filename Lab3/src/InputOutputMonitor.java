public class InputOutputMonitor {
    private int inputFlag = 0;
    private int outputFlag = 0;
    private final int inputPermits;
    private final int outputPermits;

    public InputOutputMonitor(int inputPermits, int outputPermits) {
        this.inputPermits = inputPermits;
        this.outputPermits = outputPermits;
    }

    public synchronized void inputSignal() {
        inputFlag++;
        if (inputFlag >= inputPermits) {
            notifyAll();
        }
    }

    public synchronized void outputSignal() {
        outputFlag++;
        if (outputFlag >= outputPermits) {
            notifyAll();
        }
    }

    public synchronized void waitInputSignal() {
        try {
            if (inputFlag < inputPermits) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void waitOutputSignal() {
        try {
            if (outputFlag < outputPermits) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
