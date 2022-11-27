import java.util.Date;

public class T1 extends Thread {
    private final Data data;

    public T1(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println(getName() + " -> " + new Date() + ": почав виконання.");
        try {
            // Введення 'E' та 'd'
            data.E = data.generateVector();
            data.d = data.generateValue();

            // Сигнал T2-T4 про введення даних
            data.sem1.release(3);
            System.out.println(getName() + " -> " + new Date() + ": ввід даних завершено.");

            // Чекати сигнали від T2-T4 про введення даних
            data.sem2.acquire();
            data.sem3.acquire();
            data.sem4.acquire();

            // Обчислення 1: a1 = max(C * MDh)
            int a1 = data.firstCalculation(0, data.H);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 1 завершено.");

            // Обчислення 2: a = max(a, a1)
            data.secondCalculation(a1);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 2 завершено.");

            // Сигнал T2-T4 про завершення обчислень a
            data.sem5.release(3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 'a' завершено.");

            // Чекати сигнали від T2-T4 про завершення обчислень a
            data.sem6.acquire();
            data.sem7.acquire();
            data.sem8.acquire();

            // Копія a1 = a
            a1 = data.a.get();
            System.out.println(getName() + " -> " + new Date() + ": копія 'a'.");

            // Копія d1 = d
            int d1;
            synchronized (data) {
                d1 = data.d;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'd'.");

            // Копія x1 = x
            int x1;
            synchronized (data) {
                x1 = data.x;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'x'.");

            // Обчислення 3: Ah = a1 * E * (MA * MBh) * d1 + x1 * Ch
            data.thirdCalculation(a1, d1, x1, 0, data.H);

            // Сигнал T3 про завершення обчислень Ah
            data.sem9.release(1);
            System.out.println(getName() + " -> " + new Date() + " обчислення 'Ah' завершено.");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println(getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
