public class GaussJordan {

    private Matrix matrix;

    public GaussJordan(Matrix matrix) {
        this.matrix = matrix;
    }

    public void reduceToRREF() {
        int rows = matrix.getRows();
        int cols = matrix.getColumns();
        int lead = 0;

        for (int r = 0; r < rows; r++) {
            if (lead >= cols)
                return;

            int i = r;

            // Find pivot row
            while (Math.abs(matrix.get(i, lead)) < 1e-10) {  // use tolerance instead of exact zero
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols)
                        return;
                }
            }

            // Swap rows
            matrix.swapRows(i, r);

            // Normalize pivot row
            double pivot = matrix.get(r, lead);
            for (int j = 0; j < cols; j++) {
                matrix.set(r, j, matrix.get(r, j) / pivot);
            }

            // Eliminate all other rows
            for (int k = 0; k < rows; k++) {
                if (k != r) {
                    double factor = matrix.get(k, lead);
                    for (int j = 0; j < cols; j++) {
                        double value = matrix.get(k, j) - factor * matrix.get(r, j);
                        // Round tiny values to zero
                        if (Math.abs(value) < 1e-10) value = 0;
                        matrix.set(k, j, value);
                    }
                }
            }

            lead++;
        }
    }

    public double[] getSolution() {
        int rows = matrix.getRows();
        int cols = matrix.getColumns();

        if (cols != rows + 1) {
            throw new IllegalStateException("Matrix must be augmented to extract solution.");
        }

        double[] solution = new double[rows];
        for (int i = 0; i < rows; i++) {
            double val = matrix.get(i, cols - 1);
            if (Math.abs(val) < 1e-10) val = 0;  // round near-zero to 0
            solution[i] = val;
        }

        return solution;
    }

    public void displayResult() {
        matrix.print();
    }
}