using System.Diagnostics;

/**
 * Паралельне програмування
 * Лабораторна робота #2
 * Варіант: 23
 * Завдання: A = sort(d * B + Z * (MM * MX)) * min(B)
 * ПВВ2: B, MX (T2)
 * ПВВ4: d, Z, MM (T4)
 * Ващук Кирил ІО-02
 * Дата 12.11.2022
**/


namespace Lab2
{
    class Lab2
    {
        static void Main(string[] args)
        {
            Thread.CurrentThread.Name = "Lab2";

            Console.Write("Press any key to continue...");
            Console.Read();

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is started.");

            int N = 2000;
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": N = " + N);
            Data data = new Data(N: N, generateRandomValue: false);

            Thread thread1 = new Thread(() => T1.Run(data)) { Name = "T1" };
            Thread thread2 = new Thread(() => T2.Run(data)) { Name = "T2" };
            Thread thread3 = new Thread(() => T3.Run(data)) { Name = "T3" };
            Thread thread4 = new Thread(() => T4.Run(data)) { Name = "T4" };

            Stopwatch timer = new Stopwatch();
            timer.Start();

            // Запуск потоків на виканання
            thread1.Start();
            thread2.Start();
            thread3.Start();
            thread4.Start();

            // Синхронізація потоків з головним потоком (Main)
            thread1.Join();
            thread2.Join();
            thread3.Join();
            thread4.Join();

            timer.Stop();

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is finished.");
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": execution time "+ timer.Elapsed.Minutes + "m " + timer.Elapsed.Seconds + "s " + timer.Elapsed.Milliseconds + "ms.");
        }
    }
}