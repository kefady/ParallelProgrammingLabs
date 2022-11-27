namespace Lab2
{
    public class T1
    {
        public static void Run(Data data)
        {
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is started.");

            data.sem1.WaitOne(); // Чекати сигнал від T2 про введення даних
            data.sem2.WaitOne(); // Чекати сигнал від T4 про введення даних

            data.mutex1.WaitOne();
            int d1 = data.d; // Копія d1 = d
            data.mutex1.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'd' is copied.");

            data.firstCalculation(d1, 0, data.H); // Обчислення 1
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": first calulation is finished.");

            data.sem3.WaitOne(); // Чекати сигнал від T2 про завершення обчислення Ah

            data.secondCalculation(0, data.H * 2); // Обчислення 2
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": second calulation is finished.");

            data.sem4.WaitOne(); // Чекати сигнал від T3 про завершення обчислення A2h

            data.thirdCalculation(); // Обчислення 3
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": third calulation is finished.");

            int a1 = data.fourthCalculation(0, data.H); // Обчислення 4
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fourth calulation is finished.");

            data.fifthCalculation(a1); // Обчислення 5
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fifth calulation is finished.");

            data.event1.Set(); // Сигнал T2-T4 про завершення обчисленнь a та A

            data.event2.WaitOne(); //Чекати сигнал від T2 про завершення обчислення a
            data.event3.WaitOne(); //Чекати сигнал від T3 про завершення обчислення a
            data.event4.WaitOne(); //Чекати сигнал від T4 про завершення обчислення a

            data.mutex2.WaitOne();
            a1 = data.a; // Копія a1 = a
            data.mutex2.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'a' is copied.");

            data.sixthCalculation(a1, 0, data.H); // Обчислення 6
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": sixth calulation is finished.");

            data.barrier.SignalAndWait(); // Сигнал T4 про завершення обчислення Xh

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is finished.");
        }
    }
}
