public class GaussianElimination {

    public void eliminate(Matrix m) {
        int n = m.getRows();

        for (int k = 0; k < n - 1; k++) {

            // Pivot check
            if (m.get(k, k) == 0) {
                for (int i = k + 1; i < n; i++) {
                    if (m.get(i, k) != 0) {
                        m.swapRows(k, i);
                        break;
                    }
                }
            }

            // Elimination
            for (int i = k + 1; i < n; i++) {
                double factor = m.get(i, k) / m.get(k, k);
                for (int j = k; j < m.getColumns(); j++) { // include last column!
                    double newValue = m.get(i, j) - factor * m.get(k, j);
                    m.set(i, j, newValue);
                }
            }

            // Show step
            System.out.println("Step " + (k + 1) + ":");
            m.print();
        }
    }

    public double[] getSolution(Matrix m) {
        int n = m.getRows();
        int cols = m.getColumns(); // last column = augmented
        double[] x = new double[n];

        // Start from the last row and move upwards
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += m.get(i, j) * x[j];
            }
            double val = (m.get(i, cols - 1) - sum) / m.get(i, i);
            if (Math.abs(val) < 1e-10) val = 0; // round near-zero
            x[i] = val;
        }

        return x;
    }
}