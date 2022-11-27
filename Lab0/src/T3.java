import java.util.Arrays;

// створення  потокового класу для функції  F3
public class T3 extends Thread {
    static int N;
    static double[] O;
    static double[] V;
    static double[][] MP;
    static double[][] MR;
    static InputType inputType;
    Thread thread1;
    Thread thread2;

    public T3(Thread thread1, Thread thread2) {
        this.thread1 = thread1;
        this.thread2 = thread2;
    }

    @Override
    public void run() {
//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("Lab0.T3 is started");

        // input data
        func3_input_data();

        //calculate
        O = Data.calculateFunc3(MP, MR, V, N);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //output result
        Data.outputFunc3(O, FunctionEnum.F3);

        System.out.println("Lab0.T3 is finished");
    }

    // input all data
    private static void func3_input_data() {
        N = Data.getN(FunctionEnum.F3);
        inputType = Data.getInputType(N);
        V = new double[N];
        MP = new double[N][N];
        MR = new double[N][N];
        if (inputType == InputType.KEYBOARD) {
            V = Data.inputVector(N, "V");
            System.out.println(Arrays.toString(V));
            MP = Data.inputMatrix(N, "MP");
            for (double[] doubles : MP) {
                System.out.println(Arrays.toString(doubles));
            }
            MR = Data.inputMatrix(N, "MR");
            for (double[] doubles : MR) {
                System.out.println(Arrays.toString(doubles));
            }
        } else if (inputType == InputType.RANDOM) {
            V = Data.generateRandomVector(N);
            MP = Data.generateRandomMatrix(N);
            MR = Data.generateRandomMatrix(N);
        } else if (inputType == InputType.SAME) {
            System.out.print("Введіть значення для всіх елементів функції " + FunctionEnum.F3 + ": ");
            double value = Data.getValue();
            Arrays.fill(V, value);
            for (int i = 0; i < N; i++) {
                Arrays.fill(MP[i], value);
                Arrays.fill(MR[i], value);
            }
        } else if (inputType == InputType.FILE) {
            Data.loadFile("V");
            V = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(V));
            Data.loadFile("MP");
            MP = Data.inputMatrixFromFile(N);
            for (double[] doubles : MP) {
                System.out.println(Arrays.toString(doubles));
            }
            Data.loadFile("MR");
            MR = Data.inputMatrixFromFile(N);
            for (double[] doubles : MR) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }
}