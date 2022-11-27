import java.util.Date;

public class T2 extends Thread {
    private final Data data;

    public T2(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        int id = 1;
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": почав виконання.");
        data.resourceMonitor.setB(Data.generateVector()); // Введення B
        data.resourceMonitor.setMM(Data.generateMatrix()); // Введення MM
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": ввід даних завершено.");
        data.inputOutputMonitor.inputSignal(); // Сигнал T1, T3 та T4 про введення даних
        data.inputOutputMonitor.waitInputSignal(); // Чекати сигнали від T1 та T3 про введення даних
        int a2 = data.resourceMonitor.minB(id); // Обчислення 1: a2 = min(Bh)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 1 завершено.");
        data.resourceMonitor.compare_a(a2); // Обчислення 2: a = min(a, a2)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 2 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1, T3 та T4 про завершення обчислення a
        data.resourceMonitor.calculateA(id); // Обчислення 3: Ah = B * MRh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 3 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1, T3 та T4 про завершення обчислення Ah
        data.synchronizationMonitor.waitSignal(); // Чекати сигнали від T1, T3 та T4 про завершення обчислень Ah та a.
        a2 = data.resourceMonitor.copy_a(); // Копія a2 = a
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'a' скопійовано.");
        int d2 = data.resourceMonitor.copy_d(); // Копія d2 = d
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'd' скопійовано.");
        data.resourceMonitor.calculateE(id, a2, d2); // Обчислення 4: Eh = A * (MM * MOh) + a2 * Qh * d2
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 4 завершено.");
        data.inputOutputMonitor.outputSignal(); // Сигнал T1 про завершення обчислення Eh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
