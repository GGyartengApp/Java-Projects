public class MatrixOperations {

    //ADDITION FUNCTION
    public static Matrix add(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalArgumentException("Matrices must have same dimensions.");
        }

        Matrix result = new Matrix(a.getRows(), a.getColumns());

        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                result.set(i, j, a.get(i, j) + b.get(i, j));
            }
        }
        return result;
    }

    //MULTIPLICATION FUNCTION
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("Invalid dimensions for multiplication.");
        }

        Matrix result = new Matrix(a.getRows(), b.getColumns());

        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < a.getColumns(); k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return  result;
    }

    //SQUARE FUNCTION
    public static boolean isSquare (Matrix m){
        return m.getRows() == m.getColumns();
    }

    //DETERMINANT
    public static double determinant (Matrix m){
            if (!isSquare(m)) throw new IllegalArgumentException("Matrix must be square");
            Matrix copy = m.copy();
            double det = 1;
            int n = m.getRows();

            for (int i = 0; i < n; i++) {
                // Pivoting
                int pivotRow = i;
                while (pivotRow < n && copy.get(pivotRow, i) == 0) pivotRow++;
                if (pivotRow == n) return 0;
                if (pivotRow != i) {
                    copy.swapRows(i, pivotRow);
                    det *= -1;
                }

                det *= copy.get(i, i);

                // Eliminate below
                for (int j = i + 1; j < n; j++) {
                    double factor = copy.get(j, i) / copy.get(i, i);
                    for (int k = i; k < n; k++) {
                        copy.set(j, k, copy.get(j, k) - factor * copy.get(i, k));
                    }
                }
            }

            return det;
        }

    //TRANSPOSE
    public static Matrix transpose(Matrix m) {
    Matrix result = new Matrix(m.getColumns(), m.getRows());

    for (int i = 0; i < m.getRows(); i++) {
        for (int j = 0; j < m.getColumns(); j++) {
            result.set(j, i, m.get(i, j));
        }
    }

    return result;
}

    //HELPER
    public static Matrix getMinor(Matrix m, int row, int col) {
        int n = m.getRows();
        Matrix minor = new Matrix(n - 1, n - 1);

        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;

            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;

                minor.set(r, c, m.get(i, j));
                c++;
            }
            r++;
        }

        return minor;
    }

    //COFACTOR FOR ADJOINT
    public static Matrix cofactor(Matrix m) {
        if (!isSquare(m)) {
            throw new IllegalArgumentException("Matrix must be square");
        }

        int n = m.getRows();
        Matrix cof = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Matrix minor = getMinor(m, i, j);
                double sign = ((i + j) % 2 == 0) ? 1 : -1;

                cof.set(i, j, sign * determinant(minor));
            }
        }

        return cof;
    }

    //ADJOINT
    public static Matrix adjoint(Matrix m) {
        if (!isSquare(m)) {
            throw new IllegalArgumentException("Matrix must be square");
        }

        return transpose(cofactor(m));
    }

    //INVERSE
    public static Matrix inverse(Matrix m) {
        if (!isSquare(m)) {
            throw new IllegalArgumentException("Matrix must be square");
        }

        double det = determinant(m);

        if (det == 0) {
            throw new ArithmeticException("Matrix has no inverse (det = 0)");
        }

        Matrix adj = adjoint(m);
        int n = m.getRows();
        Matrix inv = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv.set(i, j, adj.get(i, j) / det);
            }
        }

        return inv;
    }

    //FORWARD SUBSTITUTION
    public static double[] forwardSubstitution(Matrix L, double[] b) {
        int n = L.getRows();
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L.get(i, j) * y[j];
            }
            y[i] = b[i] - sum;
        }

        return y;
    }

    //BACKWARD SUBSTITUTION
    public static double[] backSubstitution(Matrix U, double[] y) {
        int n = U.getRows();
        double[] x = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U.get(i, j) * x[j];
            }
            x[i] = (y[i] - sum) / U.get(i, i);
        }

        return x;
    }

    //SOLVE_FULL_DECOMPOSTITION
    public static double[][] solveLUFull(Matrix A, double[] b) {
        LUDecomposition lu = new LUDecomposition(A);

        Matrix L = lu.getL();
        Matrix U = lu.getU();

        double[] y = forwardSubstitution(L, b);
        double[] x = backSubstitution(U, y);

        return new double[][] { y, x };
    }
    
}