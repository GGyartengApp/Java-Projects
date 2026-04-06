public class LUPDecomposition {

    private Matrix L, U;
    private int[] P;
    private int n;

    // Constructor: performs LUP decomposition
    public LUPDecomposition(Matrix A) {
        n = A.getRows();
        U = A.copy();
        L = new Matrix(n, n);
        P = new int[n];

        // Initialize permutation array
        for (int i = 0; i < n; i++) {
            P[i] = i;
        }

        for (int i = 0; i < n; i++) {
            // Find pivot
            double max = 0;
            int pivot = i;
            for (int k = i; k < n; k++) {
                if (Math.abs(U.get(k, i)) > max) {
                    max = Math.abs(U.get(k, i));
                    pivot = k;
                }
            }

            if (max == 0) {
                throw new ArithmeticException("Matrix is singular ❌");
            }

            // Swap rows in U
            if (pivot != i) {
                U.swapRows(i, pivot);

                // Swap in permutation array
                int temp = P[i];
                P[i] = P[pivot];
                P[pivot] = temp;

                // Swap previous columns in L
                for (int j = 0; j < i; j++) {
                    double t = L.get(i, j);
                    L.set(i, j, L.get(pivot, j));
                    L.set(pivot, j, t);
                }
            }

            // Set diagonal of L
            L.set(i, i, 1);

            // Compute multipliers and eliminate
            for (int j = i + 1; j < n; j++) {
                double factor = U.get(j, i) / U.get(i, i);
                L.set(j, i, factor);
                for (int k = i; k < n; k++) {
                    U.set(j, k, U.get(j, k) - factor * U.get(i, k));
                }
            }
        }
    }

    // Solve Ax = b using this LUP decomposition
    public double[] solve(double[] b) {
        double[] y = getY(b);
        return MatrixOperations.backSubstitution(U, y);
    }

    // Get permuted b vector
    public double[] getPermutedB(double[] b) {
        double[] Pb = new double[n];
        for (int i = 0; i < n; i++) {
            Pb[i] = b[P[i]];
        }
        return Pb;
    }

    // Get intermediate solution y (Ly = Pb)
    public double[] getY(double[] b) {
        double[] Pb = getPermutedB(b);
        return MatrixOperations.forwardSubstitution(L, Pb);
    }

    // Getters
    public Matrix getL() { return L; }
    public Matrix getU() { return U; }
    public int[] getP() { return P; }
}