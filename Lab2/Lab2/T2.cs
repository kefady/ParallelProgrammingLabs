namespace Lab2
{
    public class T2
    {
        public static void Run(Data data)
        {
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is started.");

            data.B = data.GenerateArray(); // Введення B
            data.MX = data.GenerateMatrix(); // Введення MX
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": input data is finished.");

            data.sem1.Release(3); // Сигнал T1, T3 та T4 про введення даних

            data.sem2.WaitOne(); // Чекати сигнал від T4 про введення даних

            data.mutex1.WaitOne();
            int d2 = data.d; // Копія d2 = d
            data.mutex1.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'd' is copied.");

            data.firstCalculation(d2, data.H, data.H * 2); // Обчислення 1
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": first calulation is finished.");

            data.sem3.Release(); // Сигнал T1 про завершення обчислення Ah

            int a2 = data.fourthCalculation(data.H, data.H * 2); // Обчислення 4
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fourth calulation is finished.");

            data.fifthCalculation(a2); // Обчислення 5
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fifth calulation is finished.");

            data.event2.Set(); // Сигнал T1, T3 та T4 про завершення обчисленнь a

            data.event1.WaitOne(); //Чекати сигнал від T1 про завершення обчислення a та A
            data.event3.WaitOne(); //Чекати сигнал від T3 про завершення обчислення a
            data.event4.WaitOne(); //Чекати сигнал від T4 про завершення обчислення a

            data.mutex2.WaitOne();
            a2 = data.a; // Копія a2 = a
            data.mutex2.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'a' is copied.");

            data.sixthCalculation(a2, data.H, data.H * 2); // Обчислення 6
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": sixth calulation is finished.");

            data.barrier.SignalAndWait(); // Сигнал T4 про завершення обчислення Xh

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is finished.");
        }
    }
}
