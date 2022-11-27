import java.util.Scanner;
import java.util.stream.IntStream;

public class Data {
    public static int H;
    public static int N;
    public static boolean generateRandomValue;
    public InputOutputMonitor inputOutputMonitor;
    public SynchronizationMonitor synchronizationMonitor;
    public ResourceMonitor resourceMonitor;

    public Data(int N, boolean generateRandomValue) {
        Data.N = N;
        Data.generateRandomValue = generateRandomValue;
        H = N / 4;
        inputOutputMonitor = new InputOutputMonitor(3, 3);
        synchronizationMonitor = new SynchronizationMonitor(8);
        resourceMonitor = new ResourceMonitor();
    }

    public static int generateValue() {
        if (Data.generateRandomValue) {
            return (int) (Math.random() * 20 - 10);
        }
        return 1;
    }

    public static int[] generateVector() {
        return IntStream.generate(Data::generateValue).limit(N).toArray();
    }

    public static int[][] generateMatrix() {
        int[][] matrix = new int[N][N];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = generateVector();
        }
        return matrix;
    }

    public static int minValueInVector(int[] vector, int id) {
        int min = vector[id * H];
        for (int i = id * H + 1; i < (id + 1) * H; i++) {
            if (min > vector[i]) {
                min = vector[i];
            }
        }
        return min;
    }

    public static int inputN() {
        int result;
        Scanner scanner = new Scanner(System.in);
        System.out.print(Thread.currentThread().getName() + " -> Введіть значення N: ");
        while (true) {
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                if (result > 1) {
                    return result;
                }
            } else {
                scanner.nextLine();
            }
            System.out.print(Thread.currentThread().getName() + " -> Введене значення некоректне, спробуйте ще раз: ");
        }
    }
}
