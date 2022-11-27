import java.util.Date;

public class T2 extends Thread {
    private final Data data;

    public T2(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println(getName() + " -> " + new Date() + ": почав виконання.");
        try {
            // Введення 'MB'
            data.MB = data.generateMatrix();

            // Сигнал T1, T3 та T4 про введення даних
            data.sem2.release(3);
            System.out.println(getName() + " -> " + new Date() + ": ввід даних завершено.");

            // Чекати сигнали від T1, T3 та T4 про введення даних
            data.sem1.acquire();
            data.sem3.acquire();
            data.sem4.acquire();

            // Обчислення 1: a2 = max(C * MDh)
            int a2 = data.firstCalculation(data.H, data.H * 2);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 1 завершено.");

            // Обчислення 2: a = max(a, a2)
            data.secondCalculation(a2);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 2 завершено.");

            // Сигнал T1, T3 та T4 про завершення обчислень a
            data.sem6.release(3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 'a' завершено.");

            // Чекати сигнали від T1, T3 та T4 про завершення обчислень a
            data.sem5.acquire();
            data.sem7.acquire();
            data.sem8.acquire();

            // Копія a2 = a
            a2 = data.a.get();
            System.out.println(getName() + " -> " + new Date() + ": копія 'a'.");

            // Копія d2 = d
            int d2;
            synchronized (data) {
                d2 = data.d;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'd'.");

            // Копія x2 = x
            int x2;
            synchronized (data) {
                x2 = data.x;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'x'.");

            // Обчислення 3: Ah = a2 * E * (MA * MBh) * d2 + x2 * Ch
            data.thirdCalculation(a2, d2, x2, data.H, data.H * 2);

            // Сигнал T3 про завершення обчислень Ah
            data.sem9.release(1);
            System.out.println(getName() + " -> " + new Date() + " обчислення 'Ah' завершено.");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println(getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
