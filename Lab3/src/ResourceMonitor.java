public class ResourceMonitor {
    private int a;
    private int d;
    private final int[] A = new int[Data.N];
    private int[] B;
    private final int[] E = new int[Data.N];
    private int[] Q;
    private int[][] MM;
    private int[][] MO;
    private int[][] MR;
    private boolean aFirstCompare = true;

    public synchronized void setQ(int[] vector) {
        Q = vector;
    }

    public synchronized void setMR(int[][] matrix) {
        MR = matrix;
    }

    public synchronized void setB(int[] vector) {
        B = vector;
    }

    public synchronized void setMM(int[][] matrix) {
        MM = matrix;
    }

    public synchronized void set_d(int value) {
        d = value;
    }

    public synchronized void setMO(int[][] matrix) {
        MO = matrix;
    }

    public synchronized int copy_a() {
        return a;
    }

    public synchronized int copy_d() {
        return d;
    }

    public int minB(int id) {
        return Data.minValueInVector(B, id);
    }

    public synchronized void compare_a(int a) {
        if (aFirstCompare) {
            this.a = a;
            aFirstCompare = false;
        } else {
            if (this.a > a) {
                this.a = a;
            }
        }
    }

    public void calculateA(int id) {
        for (int i = id * Data.H; i < (id + 1) * Data.H; i++) {
            for (int j = 0; j < Data.N; j++) {
                A[i] += B[j] * MR[i][j];
            }
        }
    }

    public void calculateE(int id, int a, int d) {
        for (int i = id * Data.H; i < (id + 1) * Data.H; i++) {
            int m = 0;
            for (int j = 0; j < Data.N; j++) {
                int s = 0;
                for (int k = 0; k < Data.N; k++) {
                    s += MO[i][k] * MM[k][j];
                }
                m += s * A[j];
            }
            E[i] = m + Q[i] * a * d;
        }
    }

    public synchronized int[] getE() {
        return E;
    }
}
