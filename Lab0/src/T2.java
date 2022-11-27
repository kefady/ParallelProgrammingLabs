import java.util.Arrays;

// створення  потокового класу для функції  F2
public class T2 implements Runnable {
    static double k;
    static double h;
    static int N;
    static double[][] MF;
    static double[][] MG;
    static double[][] MK;
    static double[][] ML;
    static InputType inputType;
    Thread thread1;

    public T2(Thread thread1) {
        this.thread1 = thread1;
    }

    @Override
    public void run() {
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("Lab0.T2 is started");

        // input data
        func2_input_data();

        //calculate
        MF = Data.calculateFunc2(MG, MK, ML, k, h, N);

        //output result
        Data.outputFunc2(MF, FunctionEnum.F2);

        System.out.println("Lab0.T2 is finished");
    }

    // input all data
    private static void func2_input_data() {
        N = Data.getN(FunctionEnum.F2);
        inputType = Data.getInputType(N);
        MG = new double[N][N];
        MK = new double[N][N];
        ML = new double[N][N];
        if (inputType == InputType.KEYBOARD) {
            System.out.print("Введіть значення k: ");
            k = Data.getValue();
            System.out.print("Введіть значення h: ");
            h = Data.getValue();
            MG = Data.inputMatrix(N, "MG");
            for (double[] doubles : MG) {
                System.out.println(Arrays.toString(doubles));
            }
            MK = Data.inputMatrix(N, "MK");
            for (double[] doubles : MK) {
                System.out.println(Arrays.toString(doubles));
            }
            ML = Data.inputMatrix(N, "ML");
            for (double[] doubles : ML) {
                System.out.println(Arrays.toString(doubles));
            }
        } else if (inputType == InputType.RANDOM) {
            k = Math.random();
            h = Math.random();
            MG = Data.generateRandomMatrix(N);
            MK = Data.generateRandomMatrix(N);
            ML = Data.generateRandomMatrix(N);
        } else if (inputType == InputType.SAME) {
            System.out.print("Введіть значення для всіх елементів функції " + FunctionEnum.F2 + ": ");
            double value = Data.getValue();
            k = value;
            h = value;
            for (int i = 0; i < N; i++) {
                Arrays.fill(MG[i], value);
                Arrays.fill(MK[i], value);
                Arrays.fill(ML[i], value);
            }
        } else if (inputType == InputType.FILE) {
            Data.loadFile("k");
            k = Data.inputVectorFromFile(N)[0];
            System.out.println(k);
            Data.loadFile("h");
            h = Data.inputVectorFromFile(N)[0];
            System.out.println(h);
            Data.loadFile("MG");
            MG = Data.inputMatrixFromFile(N);
            for (double[] doubles : MG) {
                System.out.println(Arrays.toString(doubles));
            }
            Data.loadFile("MK");
            MK = Data.inputMatrixFromFile(N);
            for (double[] doubles : MK) {
                System.out.println(Arrays.toString(doubles));
            }
            Data.loadFile("ML");
            ML = Data.inputMatrixFromFile(N);
            for (double[] doubles : ML) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }
}
