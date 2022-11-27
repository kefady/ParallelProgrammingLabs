import java.util.Date;

public class T3 extends Thread {
    private final Data data;

    public T3(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println(getName() + " -> " + new Date() + ": почав виконання.");
        try {
            // Введення 'C' та 'MA'
            data.C = data.generateVector();
            data.MA = data.generateMatrix();

            // Сигнал T1, T2 та T4 про введення даних
            data.sem3.release(3);
            System.out.println(getName() + " -> " + new Date() + ": ввід даних завершено.");

            // Чекати сигнали від T1, T2 та T4 про введення даних
            data.sem1.acquire();
            data.sem2.acquire();
            data.sem4.acquire();

            // Обчислення 1: a3 = max(C * MDh)
            int a3 = data.firstCalculation(data.H * 2, data.H * 3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 1 завершено.");

            // Обчислення 2: a = max(a, a3)
            data.secondCalculation(a3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 2 завершено.");

            // Сигнал T1, T2 та T4 про завершення обчислень a
            data.sem7.release(3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 'a' завершено.");

            // Чекати сигнали від T1, T2 та T4 про завершення обчислень a
            data.sem5.acquire();
            data.sem6.acquire();
            data.sem8.acquire();

            // Копія a3 = a
            a3 = data.a.get();
            System.out.println(getName() + " -> " + new Date() + ": копія 'a'.");

            // Копія d3 = d
            int d3;
            synchronized (data) {
                d3 = data.d;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'd'.");

            // Копія x3 = x
            int x3;
            synchronized (data) {
                x3 = data.x;
            }
            System.out.println(getName() + " -> " + new Date() + ": копія 'x'.");

            // Обчислення 3: Ah = a3 * E * (MA * MBh) * d3 + x3 * Ch
            data.thirdCalculation(a3, d3, x3, data.H * 2, data.H * 3);
            System.out.println(getName() + " -> " + new Date() + ": обчислення 'Ah' завершено.");

            // Чекати сигнали від T1, T2, T4 про завершення обчислень Ah
            data.sem9.acquire(3);

            //Виведення результату A
            System.out.println(getName() + " -> " + new Date() + ": вивід результату.");
            data.output();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println(getName() + " -> " + new Date() + ": закінчив роботу.");
    }
}
