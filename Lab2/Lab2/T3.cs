namespace Lab2
{
    public class T3
    {
        public static void Run(Data data)
        {
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is started.");

            data.sem1.WaitOne(); // Чекати сигнал від T2 про введення даних
            data.sem2.WaitOne(); // Чекати сигнал від T4 про введення даних

            data.mutex1.WaitOne();
            int d3 = data.d; // Копія d3 = d
            data.mutex1.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'd' is copied.");

            data.firstCalculation(d3, data.H * 2, data.H * 3); // Обчислення 1
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": first calulation is finished.");

            data.sem5.WaitOne(); // Чекати сигнал від T4 про завершення обчислення Ah

            data.secondCalculation(data.H * 2, data.H * 4); // Обчислення 2
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": second calulation is finished.");

            data.sem4.Release(); // Сигнал T1 про завершення обчислення A2h

            int a3 = data.fourthCalculation(data.H * 2, data.H * 3); // Обчислення 4
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fourth calulation is finished.");

            data.fifthCalculation(a3); // Обчислення 5
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fifth calulation is finished.");

            data.event3.Set(); // Сигнал T1, T2 та T4 про завершення обчисленнь a

            data.event1.WaitOne(); //Чекати сигнал від T1 про завершення обчислення a та A
            data.event2.WaitOne(); //Чекати сигнал від T2 про завершення обчислення a
            data.event4.WaitOne(); //Чекати сигнал від T4 про завершення обчислення a

            data.mutex2.WaitOne();
            a3 = data.a; // Копія a3 = a
            data.mutex2.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'a' is copied.");

            data.sixthCalculation(a3, data.H * 2, data.H * 3); // Обчислення 6
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": sixth calulation is finished.");

            data.barrier.SignalAndWait(); // Сигнал T4 про завершення обчислення Xh

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is finished.");
        }
    }
}
