public class utils {
    public static void displaySolution(double[] solution) {
        System.out.println("\nSolution:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
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
