/**
 * Паралельне програмування
 * Лабораторна робота №0
 * Варіанти 1.14, 2.7, 3.6
 * F1: D = (SORT(A + B) + C)*(MA*ME)
 * F2: MF = k*MG – h*MK*ML
 * F3: O = MAX(MP*MR)*V
 * Ващук Кирил ІО-02
 * Дата 28.09.2022
 **/
public class Lab0 {
    public static void main(String[] args) {
        System.out.println("main is started");

        T1 thread1 = new T1();
        Thread thread2 = new Thread(new T2(thread1));
        T3 thread3 = new T3(thread1, thread2);

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("main is finished");
    }
}
