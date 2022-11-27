namespace Lab2
{
    public class Data
    {
        public int N;
        public int P;
        public int H;
        public Boolean generateRandomValue; // Якщо 'true', то всі значення будуть генеруватись випадковим чином, якщо 'false' всі значення рівні '1'
        public int a;
        public int d;
        public int[] A;
        public int[] B;
        public int[] X; // Результат
        public int[] Z;
        public int[,] MM;
        public int[,] MX;
        public Mutex mutex1 = new Mutex();
        public Mutex mutex2 = new Mutex();
        public Semaphore sem1 = new Semaphore(0, 3);
        public Semaphore sem2 = new Semaphore(0, 3);
        public Semaphore sem3 = new Semaphore(0, 1);
        public Semaphore sem4 = new Semaphore(0, 1);
        public Semaphore sem5 = new Semaphore(0, 1);
        public EventWaitHandle event1 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public EventWaitHandle event2 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public EventWaitHandle event3 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public EventWaitHandle event4 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public Barrier barrier = new Barrier(4);
        private readonly object aLock = new object();

        public Data(int N, Boolean generateRandomValue)
        {
            this.N = N;
            this.generateRandomValue = generateRandomValue;
            P = 4;
            H = N / P;
            a = 1;
            A = new int[N];
            X = new int[N];
        }

        // Обчислення 1: Ah = sort(d * Bh + Z * (MM * MXh))
        public void firstCalculation(int d, int lowerLimit, int upperLimit)
        {
            int[] resultArray = new int[upperLimit - lowerLimit];
            int index = 0;
            for (int i = lowerLimit; i < upperLimit; i++)
            {
                resultArray[index] = B[i] * d;  // d * Bh
                index++;
            }
            int[] tmpArray = new int[upperLimit - lowerLimit]; // Z * (MM * MXh)
            index = 0;
            for (int i = lowerLimit; i < upperLimit; i++)
            {
                int m = 0;
                for (int j = 0; j < N; j++)
                {
                    int s = 0;
                    for (int k = 0; k < N; k++)
                    {
                        s += MX[i, k] * MM[k, j];
                    }
                    m += s * Z[j];
                }
                tmpArray[index] = m;
                index++;
            }
            for (int i = 0; i < resultArray.Length; i++)
            {
                resultArray[i] = resultArray[i] + tmpArray[i]; // d * Bh + Z * (MM * MXh)
            }
            Array.Sort(resultArray); // sort(d * Bh + Z * (MM * MXh))
            index = 0;
            for (int i = lowerLimit; i < upperLimit; i++)
            {
                A[i] = resultArray[index];
                index++;
            }
        }

        // Обчислення 2: A2h = sort(Ah, Ah)
        public void secondCalculation(int lowerLimit, int upperLimit)
        {
            int[] array = new int[upperLimit - lowerLimit];
            Array.ConstrainedCopy(A, lowerLimit, array, 0, upperLimit - lowerLimit);
            Array.Sort(array);
            int index = 0;
            for (int i = lowerLimit; i < upperLimit; i++)
            {
                A[i] = array[index];
                index++;
            }
        }

        // Обчислення 3: A = sort(A2h, A2h)
        public void thirdCalculation()
        {
            Array.Sort(A);
        }

        // Обчислення 4: ai = min⁡(Bh)
        public int fourthCalculation(int lowerLimit, int upperLimit)
        {
            int min = B[lowerLimit];
            for (int i = lowerLimit + 1; i < upperLimit; i++)
            {
                if (B[i] < min) { min = B[i]; }
            }
            return min;
        }

        // Обчислення 5: a = min⁡(a, ai)
        public void fifthCalculation(int ai)
        {
            lock (aLock)
            {
                a = Math.Min(a, ai);
            }
        }

        // Обчислення 6: Xh = Ah * a
        public void sixthCalculation(int a, int lowerLimit, int upperLimit)
        {
            for (int i = lowerLimit; i < upperLimit; i++)
            {
                X[i] = A[i] * a;
            }
        }

        // Генерує випадкове число, або одиницю, в залежності від значення 'generateRandomValue'
        public int GenerateValue()
        {
            if (generateRandomValue) { return Random.Shared.Next(-10, 10); }
            else { return 1; }
        }

        // Генерує вектор з випадковими значеннями, або одиницями, в залежності від значення 'generateRandomValue'
        public int[] GenerateArray()
        {
            if (generateRandomValue) { return Enumerable.Repeat(0, N).Select(i => Random.Shared.Next(-10, 10)).ToArray(); }
            else { return Enumerable.Repeat(0, N).Select(i => 1).ToArray(); }

        }

        // Генерує матрицю з випадковими значеннями, або одиницями, в залежності від значення 'generateRandomValue'
        public int[,] GenerateMatrix()
        {
            int[,] matrix = new int[N, N];
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    matrix[i, j] = GenerateValue();
                }
            }
            return matrix;
        }
    }
}
