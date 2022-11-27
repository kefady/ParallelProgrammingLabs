import java.util.Date;

public class T4 extends Thread {
    private final Data data;

    public T4(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        int id = 3;
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": почав виконання.");
        data.inputOutputMonitor.waitInputSignal(); // Чекати сигнали від T1-T3 про введення даних
        int a4 = data.resourceMonitor.minB(id); // Обчислення 1: a4 = min(Bh)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 1 завершено.");
        data.resourceMonitor.compare_a(a4); // Обчислення 2: a = min(a, a4)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 2 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1-T3 про завершення обчислення a
        data.resourceMonitor.calculateA(id); // Обчислення 3: Ah = B * MRh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 3 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1-T3 про завершення обчислення Ah
        data.synchronizationMonitor.waitSignal(); // Чекати сигнали від T1-T3 про завершення обчислень Ah та a.
        a4 = data.resourceMonitor.copy_a(); // Копія a4 = a
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'a' скопійовано.");
        int d4 = data.resourceMonitor.copy_d(); // Копія d4 = d
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'd' скопійовано.");
        data.resourceMonitor.calculateE(id, a4, d4); // Обчислення 4: Eh = A * (MM * MOh) + a4 * Qh * d4
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 4 завершено.");
        data.inputOutputMonitor.outputSignal(); // Сигнал T1 про завершення обчислення Eh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
