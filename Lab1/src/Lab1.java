/**
 * Паралельне програмування
 * Лабораторна робота №1
 * Варіант: 3
 * Завдання: A = max(C * MD) * E * (MA * MB) * d + x * C
 * ПВВ1: E, d (T1)
 * ПВВ2: MB (T2)
 * ПВВ3: C, MA, A (T3)
 * ПВВ4: MD (T4)
 * Ващук Кирил ІО-02
 * Дата 08.11.2022
 **/

public class Lab1 {
    public static void main(String[] args) {
        System.out.println("Lab1 is started");

        int N = Data.inputN();
        InputType inputType = Data.inputType();
        Data data = new Data(N, inputType);

        Thread thread1 = new T1("T1", data);
        Thread thread2 = new T2("T2", data);
        Thread thread3 = new T3("T3", data);
        Thread thread4 = new T4("T4", data);

        double startTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double endTime = System.currentTimeMillis();

        System.out.println("Lab1 is finished");
        System.out.println("Час виконання: " + (endTime - startTime) + " мс");
    }
}
