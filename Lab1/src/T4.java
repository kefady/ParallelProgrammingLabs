import java.util.Date;

public class T4 extends Thread {
    private final Data data;

    public T4(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println(getName() + " -> " + new Date() + ": почав виконання.");
        try {
            // Введення 'MD'
            data.MD = data.generateMatrix();

            // Сигнал T1-T3 про введення даних
            data.sem4.release(3);
            System.out.println(getName() + " -> " + new Date() + ": ввід даних завершено.");

            // Чекати сигнали від T1-T3 про введення даних
            data.sem1.acquire();
            data.sem2.acquire();
            data.sem3.acquire();

            // Обчислення 1: a4 = max(C * MDh)
            int a4 = data.firstCalculation(data.H * 3, data.H * 4);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 1 завершено.");

            // Обчислення 2: a = max(a, a4)
            data.secondCalculation(a4);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 2 завершено.");

            // Сигнал T1-T3 про завершення обчислень a
            data.sem8.release(3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 'a' завершено.");

            // Чекати сигнали від T1-T3 про завершення обчислень a
            data.sem5.acquire();
            data.sem6.acquire();
            data.sem7.acquire();

            // Копія a4 = a
            a4 = data.a.get();
            System.out.println(getName() + " -> " + new Date() + ": копія 'a'.");

            // Копія d4 = d
            int d4;
            synchronized (data) {
                d4 = data.d;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'd'.");

            // Копія x4 = x
            int x4;
            synchronized (data) {
                x4 = data.x;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'x'.");

            // Обчислення 3: Ah = a4 * E * (MA * MBh) * d4+ x4 * Ch
            data.thirdCalculation(a4, d4, x4, data.H * 3, data.H * 4);

            // Сигнал T3 про завершення обчислень Ah
            data.sem9.release(1);
            System.out.println(getName() + " -> " + new Date() + " обчислення 'Ah' завершено.");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println(getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
