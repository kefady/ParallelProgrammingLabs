import java.util.Date;
import java.util.Scanner;

/**
 * Паралельне програмування
 * Лабораторна робота №3
 * Варіант: 17
 * Завдання: E = (B * MR) * (MM * MO) + min(B) * Q * d
 * ПВВ1: MR, Q, E
 * ПВВ2: B, MM
 * ПВВ3: d, MO
 * Ващук Кирил ІО-02
 * Дата 24.11.2022
 **/

public class Lab3 {
    static {Thread.currentThread().setName("Lab3");}

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": почав виконання.");

        int N = Data.inputN();

        Data data = new Data(N, false);

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

        System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": закінчив роботу.");
        System.out.println(Thread.currentThread().getName() + " -> Час виконання: " + (endTime - startTime) + " мс.");
    }
}
