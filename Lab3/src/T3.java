import java.util.Date;

public class T3 extends Thread {
    private final Data data;

    public T3(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        int id = 2;
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": почав виконання.");
        data.resourceMonitor.set_d(Data.generateValue()); // Введення d
        data.resourceMonitor.setMO(Data.generateMatrix()); // Введення MO
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": ввід даних завершено.");
        data.inputOutputMonitor.inputSignal(); // Сигнал T1, T2 та T4 про введення даних
        data.inputOutputMonitor.waitInputSignal(); // Чекати сигнали від T1 та T2 про введення даних
        int a3 = data.resourceMonitor.minB(id); // Обчислення 1: a3 = min(Bh)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 1 завершено.");
        data.resourceMonitor.compare_a(a3); // Обчислення 2: a = min(a, a3)
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 2 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1, T2 та T4 про завершення обчислення a
        data.resourceMonitor.calculateA(id); // Обчислення 3: Ah = B * MRh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 3 завершено.");
        data.synchronizationMonitor.signal(); // Сигнал T1, T2 та T4 про завершення обчислення Ah
        data.synchronizationMonitor.waitSignal(); // Чекати сигнали від T1, T2 та T4 про завершення обчислень Ah та a.
        a3 = data.resourceMonitor.copy_a(); // Копія a3 = a
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'a' скопійовано.");
        int d3 = data.resourceMonitor.copy_d(); // Копія d3 = d
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": 'd' скопійовано.");
        data.resourceMonitor.calculateE(id, a3, d3); // Обчислення 4: Eh = A * (MM * MOh) + a3 * Qh * d3
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": обчислення 4 завершено.");
        data.inputOutputMonitor.outputSignal(); // Сигнал T1 про завершення обчислення Eh
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
