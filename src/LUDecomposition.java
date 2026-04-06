public class LUDecomposition {

    private Matrix L, U;

    public LUDecomposition(Matrix A) {
        int n = A.getRows();

        L = new Matrix(n, n);
        U = new Matrix(n, n);

        for (int i = 0; i < n; i++) {

            // Upper Triangular (U)
            for (int k = i; k < n; k++) {
                double sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += L.get(i, j) * U.get(j, k);
                }
                U.set(i, k, A.get(i, k) - sum);
            }

            // Lower Triangular (L)
            for (int k = i; k < n; k++) {
                if (i == k) {
                    L.set(i, i, 1);
                } else {
                    double sum = 0;
                    for (int j = 0; j < i; j++) {
                        sum += L.get(k, j) * U.get(j, i);
                    }
                    L.set(k, i, (A.get(k, i) - sum) / U.get(i, i));
                }
            }
        }
    }

    public Matrix getL() { return L; }
    public Matrix getU() { return U; }
}