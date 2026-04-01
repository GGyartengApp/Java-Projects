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
}