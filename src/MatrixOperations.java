public class MatrixOperations {

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

    // ✖️ MULTIPLICATION
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

        public static boolean isSquare (Matrix m){
            return m.getRows() == m.getColumns();
        }

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
    }
