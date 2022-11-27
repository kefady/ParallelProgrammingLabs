import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Data {
    private static String[] textArray;

    // get N (matrix and vector size)
    public static int getN(FunctionEnum func) {
        Scanner scanner = new Scanner(System.in);
        String value;
        System.out.print("Введіть розмір матриць та векторів дня функції " + func + ": ");
        while (true) {
            value = scanner.next();
            if (isInt(value) && Integer.parseInt(value) > 0) break;
            else System.out.print("Ви ввели некоректне значення! Спробуйте ще раз: ");
        }
        return Integer.parseInt(value);
    }

    // get a data entry method
    public static InputType getInputType(int N) {
        Scanner scanner = new Scanner(System.in);
        String value;
        if (N > 4) {
            System.out.print("N = " + N + " > 4. Виберіть варіант вводу: \n" +
                    "'random' - генерація випадкових значень;\n" +
                    "'file' - ввід з файлу;\n" +
                    "'same' - встановлення всіх елементів даних заданому значенню.\n" +
                    "Варіант вводу: ");
            while (true) {
                value = scanner.next();
                switch (value) {
                    case "random":
                        return InputType.RANDOM;
                    case "file":
                        return InputType.FILE;
                    case "same":
                        return InputType.SAME;
                    default:
                        System.out.print("Ви ввели некоректне значення! Спробуйте ще раз: ");
                        break;
                }
            }
        } else return InputType.KEYBOARD;
    }

    // input matrix from file
    public static double[][] inputMatrixFromFile(int N) {
        double[][] MA = new double[N][N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (textArray.length > index && isDouble(textArray[index])) {
                    MA[i][j] = Double.parseDouble(textArray[index]);
                } else {
                    MA[i][j] = 0;
                }
                index++;
            }
        }
        return MA;
    }

    // input vector from file
    public static double[] inputVectorFromFile(int N) {
        double[] A = new double[N];
        for (int i = 0; i < N; i++) {
            if (textArray.length > i && isDouble(textArray[i])) {
                A[i] = Double.parseDouble(textArray[i]);
            } else {
                A[i] = 0;
            }
        }
        return A;
    }

    // load file from pc
    public static void loadFile(String name) {
        Scanner scanner = new Scanner(System.in);
        String value;
        System.out.print("Введіть шлях до файлу з даннми для " + name + ": ");
        while (true) {
            value = scanner.next();
            Path path = Path.of(value);
            if (Files.exists(path)) {
                try {
                    String text;
                    text = new String(Files.readAllBytes(path));
                    text = text.replaceAll("\\s", "");
                    textArray = text.split(";");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            } else System.out.print("Ви ввели невірний шлях! Спробуйте ще раз: ");
        }
    }

    // input matrix from console
    public static double[][] inputMatrix(int N, String name) {
        double[][] MA = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print("Введіть значення " + name + "[" + i + "][" + j + "]: ");
                MA[i][j] = getValue();
            }
        }
        return MA;
    }

    // input vector from console
    public static double[] inputVector(int N, String name) {
        double[] A = new double[N];
        for (int i = 0; i < N; i++) {
            System.out.print("Введіть значення " + name + "[" + i + "]: ");
            A[i] = getValue();
        }
        return A;
    }

    // read value from console and check it
    public static double getValue() {
        Scanner scanner = new Scanner(System.in);
        String value;
        while (true) {
            value = scanner.next();
            if (isDouble(value)) break;
            else System.out.print("Ви ввели некоректне значення! Спробуйте ще раз: ");
        }
        return Double.parseDouble(value);
    }

    // F1: D = (SORT(A + B) + C)*(MA*ME)
    public static double[] calculateFunc1(double[] A, double[] B, double[] C, double[][] MA, double[][] ME, int N) {
        double[] S = sumVector(A, B, N);
        Arrays.sort(S);
        return multMatrixByVector(multMatrixByMatrix(MA, ME, N), sumVector(S, C, N), N);
    }

    // output result of function 1
    public static void outputFunc1(double[] D, FunctionEnum func) {
        System.out.println("Результат обчислення функції " + func + ": ");
        System.out.print("{");
        for (int i = 0; i < D.length; i++) {
            System.out.format("%.1f", D[i]);
            if (i < D.length - 1) System.out.print("; ");
        }
        System.out.print("}\n");
    }

    // F2: MF = k*MG – h*MK*ML
    public static double[][] calculateFunc2(double[][] MG, double[][] MK, double[][] ML, double k, double h, int N) {
        return diffMatrix(multMatrixByNumber(MG, k, N), multMatrixByNumber(multMatrixByMatrix(MK, ML, N), h, N), N);
    }

    // output result of function 2
    public static void outputFunc2(double[][] MF, FunctionEnum func) {
        System.out.println("Результат обчислення функції " + func + ": ");
        for (double[] doubles : MF) {
            System.out.print("{");
            for (int j = 0; j < doubles.length; j++) {
                System.out.format("%.1f", doubles[j]);
                if (j < MF[j].length - 1) System.out.print("; ");
            }
            System.out.print("}\n");
        }
    }

    // F3: O = MAX(MP*MR)*V
    public static double[] calculateFunc3(double[][] MP, double[][] MR, double[] V, int N) {
        return multVectorByNumber(V, getMaxMatrixElement(multMatrixByMatrix(MP, MR, N)), N);
    }

    // output result of function 3
    public static void outputFunc3(double[] O, FunctionEnum func) {
        System.out.println("Результат обчислення функції " + func + ": ");
        System.out.print("{");
        for (int i = 0; i < O.length; i++) {
            System.out.format("%.1f", O[i]);
            if (i < O.length - 1) System.out.print("; ");
        }
        System.out.print("}\n");
    }

    // generate matrix with random values
    public static double[][] generateRandomMatrix(int N) {
        double[][] MA = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MA[i][j] = Math.random() * 20 - 10;
            }
        }
        return MA;
    }

    // generate vector with random values
    public static double[] generateRandomVector(int N) {
        double[] A = new double[N];
        for (int i = 0; i < N; i++) {
            A[i] = Math.random() * 20 - 10;
        }
        return A;
    }

    // matrix by number multiplication
    private static double[][] multMatrixByNumber(double[][] MA, double a, int N) {
        double[][] MB = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MB[i][j] = MA[i][j] * a;
            }
        }
        return MB;
    }

    // matrix by vector multiplication
    private static double[] multMatrixByVector(double[][] MA, double[] A, int N) {
        double[] MB = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MB[i] += MA[i][j] * A[j];
            }
        }
        return MB;
    }

    // matrix by matrix multiplication
    private static double[][] multMatrixByMatrix(double[][] MA, double[][] MB, int N) {
        double[][] MC = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int n = 0; n < N; n++) {
                    MC[i][j] += MA[i][n] * MB[n][j];
                }
            }
        }
        return MC;
    }

    // matrix subtraction
    private static double[][] diffMatrix(double[][] MA, double[][] MB, int N) {
        double[][] MC = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MC[i][j] = MA[i][j] - MB[i][j];
            }
        }
        return MC;
    }

    // finding the largest value of a matrix
    private static double getMaxMatrixElement(double[][] MA) {
        double value = MA[0][0];
        for (double[] doubles : MA) {
            for (double currentValue : doubles) {
                if (value < currentValue) value = currentValue;
            }
        }
        return value;
    }

    // multiplication of two vectors
    private static double[] multVectorByNumber(double[] A, double a, int N) {
        double[] B = new double[N];
        for (int i = 0; i < N; i++) {
            B[i] = A[i] * a;
        }
        return B;
    }

    // addition of two vectors
    private static double[] sumVector(double[] A, double[] B, int N) {
        double[] C = new double[N];
        for (int i = 0; i < N; i++) {
            C[i] = A[i] + B[i];
        }
        return C;
    }

    // check if the value is a type Int
    private static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    // check if the value is a type Double
    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }
}
