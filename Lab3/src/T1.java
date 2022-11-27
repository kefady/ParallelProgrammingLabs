import java.util.Arrays;
import java.util.Date;

public class T1 extends Thread {
    private final Data data;

    public T1(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        int id = 0;
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": почав виконання.");
        data.resourceMonitor.setQ(Data.generateVector()); // Введення Q
        data.resourceMonitor.setMR(Data.generateMatrix()); // Введення MR
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": ввід даних завершено.");
        data.inputOutputMonitor.inputSignal(); // Сигнал T2-T4 про введення даних
        data.inputOutputMonitor.waitInputSignal(); // Чекати сигнали від T2 та T3 про введення даних
        int a1 = data.resourceMonitor.minB(id); // Обчислення 1: a1 = min(Bh)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 1 завершено.");
        data.resourceMonitor.compare_a(a1); // Обчислення 2: a = min(a, a1)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 2 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T2-T4 про завершення обчислення a
        data.resourceMonitor.calculateA(id); // Обчислення 3: Ah = B * MRh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 3 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T2-T4 про завершення обчислення Ah
        data.synchronizationMonitor.waitSignal(); // Чекати сигнали від T2-T4 про завершення обчислень Ah та a.
        a1 = data.resourceMonitor.copy_a(); // Копія a1 = a
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'a' скопійовано.");
        int d1 = data.resourceMonitor.copy_d(); // Копія d1 = d
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'd' скопійовано.");
        data.resourceMonitor.calculateE(id, a1, d1); // Обчислення 4: Eh = A * (MM * MOh) + a1 * Qh * d1
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 4 завершено.");
        data.inputOutputMonitor.waitOutputSignal(); // Чекати сигнали від T2- T4 про завершення обчислення Eh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": E = " + Arrays.toString(data.resourceMonitor.getE()));
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
