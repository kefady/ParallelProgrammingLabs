import java.util.Arrays;

// створення  потокового класу для функції  F1
public class T1 extends Thread {
    static int N; // matrix and vector size
    static double[] A;
    static double[] B;
    static double[] C;
    static double[][] MA;
    static double[][] ME;
    static double[] D; //result func1
    static InputType inputType;

    @Override
    public void run() {
        System.out.println("Lab0.T1 is started");

        // input data
        func1_input_data();

        //calculate
        D = Data.calculateFunc1(A, B, C, MA, ME, N);

        //output result
        Data.outputFunc1(D, FunctionEnum.F1);

        System.out.println("Lab0.T1 is finished");
    }

    // input all data
    private static void func1_input_data() {
        N = Data.getN(FunctionEnum.F1);
        inputType = Data.getInputType(N);
        A = new double[N];
        B = new double[N];
        C = new double[N];
        MA = new double[N][N];
        ME = new double[N][N];
        if (inputType == InputType.KEYBOARD) {
            A = Data.inputVector(N, "A");
            System.out.println(Arrays.toString(A));
            B = Data.inputVector(N, "B");
            System.out.println(Arrays.toString(B));
            C = Data.inputVector(N, "C");
            System.out.println(Arrays.toString(C));
            MA = Data.inputMatrix(N, "MA");
            for (double[] doubles : MA) {
                System.out.println(Arrays.toString(doubles));
            }
            ME = Data.inputMatrix(N, "ME");
            for (double[] doubles : ME) {
                System.out.println(Arrays.toString(doubles));
            }
        } else if (inputType == InputType.RANDOM) {
            A = Data.generateRandomVector(N);
            B = Data.generateRandomVector(N);
            C = Data.generateRandomVector(N);
            MA = Data.generateRandomMatrix(N);
            ME = Data.generateRandomMatrix(N);
        } else if (inputType == InputType.SAME) {
            System.out.print("Введіть значення для всіх елементів функції " + FunctionEnum.F1 + ": ");
            double value = Data.getValue();
            Arrays.fill(A, value);
            Arrays.fill(B, value);
            Arrays.fill(C, value);
            for (int i = 0; i < N; i++) {
                Arrays.fill(MA[i], value);
                Arrays.fill(ME[i], value);
            }
        } else if (inputType == InputType.FILE) {
            Data.loadFile("A");
            A = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(A));
            Data.loadFile("B");
            B = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(B));
            Data.loadFile("C");
            C = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(C));
            Data.loadFile("MA");
            MA = Data.inputMatrixFromFile(N);
            for (double[] doubles : MA) {
                System.out.println(Arrays.toString(doubles));
            }
            Data.loadFile("ME");
            ME = Data.inputMatrixFromFile(N);
            for (double[] doubles : ME) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }

}
