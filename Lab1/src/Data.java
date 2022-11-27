import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Data {
    public final int H;
    public final int N;
    public final int P = 4;
    public final InputType inputType;
    public int d;
    public int x;
    public int[] A; // результат
    public int[] C;
    public int[] E;
    public int[][] MA;
    public int[][] MB;
    public int[][] MD;
    public AtomicInteger a;
    public Semaphore sem1 = new Semaphore(0, true);
    public Semaphore sem2 = new Semaphore(0, true);
    public Semaphore sem3 = new Semaphore(0, true);
    public Semaphore sem4 = new Semaphore(0, true);
    public Semaphore sem5 = new Semaphore(0, true);
    public Semaphore sem6 = new Semaphore(0, true);
    public Semaphore sem7 = new Semaphore(0, true);
    public Semaphore sem8 = new Semaphore(0, true);
    public Semaphore sem9 = new Semaphore(0, true);

    public Data(int n, InputType inputType) {
        N = n;
        this.inputType = inputType;
        H = N / P;
        d = 0;
        x = 1;
        A = new int[N];
        C = new int[N];
        E = new int[N];
        MA = new int[N][N];
        MB = new int[N][N];
        MD = new int[N][N];
        a = new AtomicInteger(0);
    }

    // Обчислення 1: ai = max(C * MDh)
    public int firstCalculation(int from, int to) {
        int[] vector = multVectorBySubMatrix(C, MD, from, to);
        int maxValue = vector[0];
        if (vector.length > 1) {
            for (int i = 1; i < vector.length; i++) {
                if (vector[i] > maxValue) {
                    maxValue = vector[i];
                }
            }
        }
        return maxValue;
    }

    // Обчислення 2: a = max(a, ai)
    public void secondCalculation(int ai) {
        int result = Math.max(a.get(), ai);
        this.a.set(result);
    }

    // Обчислення 3: Ah = ai * E * (MA * MBh) * di + xi * Ch
    public void thirdCalculation(int ai, int di, int xi, int from, int to) {
        int[] tmpResult1 = multVectorByNumber(E, ai * di, 0, N);
        int[] tmpResult2 = new int[N];
        int index = 0;
        for (int i = from; i < to; i++) {
            int m = 0;
            for (int j = 0; j < N; j++) {
                int s = 0;
                for (int k = 0; k < N; k++) {
                    s += MB[i][k] * MA[k][j];
                }
                m += s * tmpResult1[j];
            }
            tmpResult2[index] = m;
            index++;
        }
        int[] tmpResult3 = multVectorByNumber(C, xi, from, to);
        index = 0;
        for (int i = from; i < to; i++) {
            A[i] = tmpResult2[index] + tmpResult3[index];
            index++;
        }
    }

    // Вивід результату
    public void output() {
        System.out.println("A = " + Arrays.toString(A));
    }

    // Генерування випадкового значення в діапазоні [-10;10]
    public int generateRandomValue() {
        return (int) (Math.random() * 20 - 10);
    }

    // Генерування числа відповідно до способу вводу
    public int generateValue() {
        if (inputType == InputType.RANDOM) {
            return generateRandomValue();
        }
        return 1;
    }

    // Генерування вектора відповідно до способу вводу
    public int[] generateVector() {
        if (inputType == InputType.RANDOM) {
            return IntStream.generate(this::generateRandomValue).limit(N).toArray();
        }
        return IntStream.generate(() -> 1).limit(N).toArray();
    }

    // Генерування матриці відповідно до способу вводу
    public int[][] generateMatrix() {
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            matrix[i] = generateVector();
        }
        return matrix;
    }

    // Ввід значення N
    public static int inputN() {
        System.out.print("Введіть значення N -> ");
        return inputValue(2, Integer.MAX_VALUE);
    }

    // Ввід значення inputType
    public static InputType inputType() {
        System.out.print("""
                Оберіть спосіб вводу даних:
                0 - згенерувати значення випадковим чином;
                1 - встановити всі значення рівними '1'
                Введіть значення ->\s""");
        int value = inputValue(0, 1);
        if (value == 0) return InputType.RANDOM;
        return InputType.ALL_ONE;
    }

    // Множення вектора на матрицю
    private int[] multVectorBySubMatrix(int[] vector, int[][] subMatrix, int from, int to) {
        int[] result = new int[to - from];
        int index = 0;
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                result[index] += vector[j] * subMatrix[i][j];
            }
            index++;
        }
        return result;
    }

    // Множення вектора на скаляр
    private int[] multVectorByNumber(int[] vector, int number, int from, int to) {
        int[] result = new int[to - from];
        int index = 0;
        for (int i = from; i < to; i++) {
            result[index] = vector[i] * number;
            index++;
        }
        return result;
    }

    // Метод для зчитування значення з консолі
    private static int inputValue(int from, int to) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String value = scanner.next();
            if (isInt(value) && (Integer.parseInt(value) >= from) && Integer.parseInt(value) <= to) {
                return Integer.parseInt(value);
            } else {
                System.out.print("Ви ввели некоректне значення. Спробуйте ще раз -> ");
            }
        }
    }

    // Перевірка, чи є введення значення типом Integer
    private static boolean isInt(String value) {
        if (value == null) return false;
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }
}

enum InputType {
    RANDOM,
    ALL_ONE
}
