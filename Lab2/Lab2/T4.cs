namespace Lab2
{
    public class T4
    {
        public static void Run(Data data)
        {
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is started.");

            data.d = data.GenerateValue(); // Введення d
            data.Z = data.GenerateArray(); // Введення Z
            data.MM = data.GenerateMatrix(); // Введення MM
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": input data is finished.");

            data.sem2.Release(3); // Сигнал T1-T2 про введення даних

            data.sem1.WaitOne(); // Чекати сигнал від T2 про введення даних

            data.mutex1.WaitOne();
            int d4 = data.d; // Копія d4 = d
            data.mutex1.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'd' is copied.");

            data.firstCalculation(d4, data.H * 3, data.H * 4); // Обчислення 1
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": first calulation is finished.");

            data.sem5.Release(); // Сигнал T3 про завершення обчислення Ah

            int a4 = data.fourthCalculation(data.H * 3, data.H * 4); // Обчислення 4
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fourth calulation is finished.");

            data.fifthCalculation(a4); // Обчислення 5
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": fifth calulation is finished.");

            data.event4.Set(); // Сигнал T1-T3 про завершення обчисленнь a

            data.event1.WaitOne(); //Чекати сигнал від T1 про завершення обчислення a та A
            data.event2.WaitOne(); //Чекати сигнал від T2 про завершення обчислення a
            data.event3.WaitOne(); //Чекати сигнал від T3 про завершення обчислення a

            data.mutex2.WaitOne();
            a4 = data.a; // Копія a4 = a
            data.mutex2.ReleaseMutex();
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": 'a' is copied.");

            data.sixthCalculation(a4, data.H * 3, data.H * 4); // Обчислення 6
            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": sixth calulation is finished.");

            data.barrier.SignalAndWait(); // Чекати сигнал від T1-T3 про завершення обчислення Xh

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": X = [{0}]", string.Join(", ", data.X)); // Виведення результату X

            Console.WriteLine(Thread.CurrentThread.Name + " -> " + DateTime.Now + ": is finished.");
        }
    }
}
