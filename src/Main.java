import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====**** Linear Algebra Calculator ****====");
            utils.Spacer();
            System.out.println("1. General Matrix Operations");
            System.out.println("2. Determinant");
            System.out.println("3. Gaussian Elimination");
            System.out.println("4. Gauss-Jordan (RREF)");
            System.out.println("5. LU Decomposition");
            System.out.println("6. Solve System using LU");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("Exiting... 👋");
                break;
            }


            utils.delay(3);
            System.out.print("Enter number of rows: ");
            int rows = sc.nextInt();
            utils.Spacer();

            int cols = rows; // determinant only works on square matrices
            if (choice == 3 || choice == 4) {
                System.out.print("Enter number of columns (including augmented column if needed): ");
                cols = sc.nextInt();
                utils.Spacer();
            }

            utils.showLoading("Please wait", 3, 500);
            Matrix matrix = new Matrix(rows, cols);
            System.out.println("Enter matrix elements row by row:");
            matrix.input(sc);

            switch (choice) {
                case 1 -> {
                    while (true) {
                        System.out.println(Colors.RED + "\n--- General Matrix Operations ---");
                        System.out.println("1. Matrix Multiplication");
                        System.out.println("2. Matrix Addition");
                        System.out.println("3. Adjoint");
                        System.out.println("4. Transpose");
                        System.out.println("5. Inverse");
                        System.out.println("0. Back" + Colors.RESET);
                        System.out.print("Enter your choice: ");

                        int opChoice = sc.nextInt();

                        if (opChoice == 0) {
                            System.out.println("Going back... 🔙");
                            break; // exits this inner loop and returns to main menu
                        }

                        switch (opChoice) {

                            case 1 -> { // Multiplication
                                System.out.print("Enter columns of second matrix: ");
                                int cols2 = sc.nextInt();

                                Matrix m2 = new Matrix(matrix.getColumns(), cols2);
                                m2.input(sc);

                                Matrix result = MatrixOperations.multiply(matrix, m2);
                                utils.Spacer();
                                utils.showLoading("Performing Operation", 3, 500);
                                System.out.println("Result:");
                                result.print();
                            }

                            case 2 -> { // Addition
                                Matrix m2 = new Matrix(matrix.getRows(), matrix.getColumns());
                                System.out.println("Enter second matrix:");
                                m2.input(sc);


                                Matrix result = MatrixOperations.add(matrix, m2);
                                utils.Spacer();
                                utils.showLoading("Performing Operation", 3, 500);
                                System.out.println("Result:");
                                result.print();
                            }

                            case 3 -> { // Adjoint
                                if (!MatrixOperations.isSquare(matrix)) {
                                    System.out.println("Matrix must be square to find adjoint ❌");
                                    break;
                                }

                                utils.Spacer();
                                utils.showLoading("Calculating Adjoint", 3, 500);

                                Matrix result = MatrixOperations.adjoint(matrix);

                                System.out.println("Adjoint Matrix:");
                                result.print();
                            }

                            case 4 -> { // Transpose
                                utils.Spacer();
                                utils.showLoading("Transposing Matrix", 3, 500);

                                Matrix result = MatrixOperations.transpose(matrix);

                                System.out.println("Transpose Matrix:");
                                result.print();
                            }
                            case 5 -> { // Inverse
                                if (!MatrixOperations.isSquare(matrix)) {
                                    System.out.println("Matrix must be square to find inverse ❌");
                                    break;
                                }

                                try {
                                    utils.Spacer();
                                    utils.showLoading("Calculating Inverse", 3, 500);

                                    Matrix result = MatrixOperations.inverse(matrix);

                                    System.out.println("Inverse Matrix:");
                                    result.print();

                                } catch (ArithmeticException e) {
                                    System.out.println("Matrix is singular (no inverse exists) ❌");
                                }
                            }


                            default -> System.out.println("Invalid choice ❌");
                        }
                    }
                }
                case 2 -> { // Determinant
                    if (!MatrixOperations.isSquare(matrix)) {
                        System.out.println("Matrix must be square to calculate determinant ❌");
                        break;
                    }
                    double det = MatrixOperations.determinant(matrix);
                    System.out.println("Determinant = " + det);
                }
                case 3 -> { // Gaussian Elimination
                    GaussianElimination gauss = new GaussianElimination();
                    gauss.eliminate(matrix);

                    utils.Spacer();
                    utils.showLoading("Performing Calculation", 3, 500);
                    System.out.println("Final Matrix after Gaussian Elimination:");
                    matrix.print();
                    double[] solution = gauss.getSolution(matrix); // make sure this exists in GaussJordan
                    utils.displaySolution(solution);
                }
                case 4 -> { // Gauss-Jordan
                    GaussJordan gj = new GaussJordan(matrix);
                    gj.reduceToRREF();
                    utils.Spacer();
                    utils.showLoading("Performing Calculation", 3, 500);
                    System.out.println("Matrix in Reduced Row Echelon Form:");
                    matrix.print();
                    double[] solution = gj.getSolution(); // make sure this exists in GaussJordan
                    utils.displaySolution(solution);
                }
                case 5 -> { // LU Decomposition
                    if (!MatrixOperations.isSquare(matrix)) {
                        System.out.println("Matrix must be square ❌");
                        break;
                    }

                    utils.Spacer();
                    utils.showLoading("Performing LU Decomposition", 3, 500);

                    LUDecomposition lu = new LUDecomposition(matrix);

                    System.out.println("L Matrix:");
                    lu.getL().print();

                    System.out.println("U Matrix:");
                    lu.getU().print();
                }
                case 6 -> { // Solve using LU
                    if (!MatrixOperations.isSquare(matrix)) {
                        System.out.println("Matrix must be square ❌");
                        break;
                    }

                    System.out.println("\n--- LU Solve Options ---");
                    System.out.println("1. Normal LU -- (A = LU)");
                    System.out.println("2. LU with Partial Pivoting -- (PA = LU)");
                    System.out.print("Enter your choice: ");

                    int luChoice = sc.nextInt();

                    int n = matrix.getRows();
                    double[] b = new double[n];

                    System.out.println("Enter vector b:");
                    for (int i = 0; i < n; i++) {
                        b[i] = sc.nextDouble();
                    }

                    utils.Spacer();
                    utils.showLoading("Solving system", 3, 500);

                    switch (luChoice) {

                        case 1 -> { // Normal LU
                            double[][] result = MatrixOperations.solveLUFull(matrix, b);

                            double[] y = result[0];
                            double[] x = result[1];

                            LUDecomposition lu = new LUDecomposition(matrix);

                            System.out.println("\nL Matrix:");
                            lu.getL().print();

                            System.out.println("\nU Matrix:");
                            lu.getU().print();

                            System.out.println("\nIntermediate solution (y):");
                            utils.displaySolution_Y(y);

                            System.out.println("\nFinal solution (x):");
                            utils.displaySolution(x);
                        }

                        case 2 -> { // LUP (Partial Pivoting)
                            LUPDecomposition lup = new LUPDecomposition(matrix);

                            double[] x = lup.solve(b);

                            System.out.println("\nL Matrix:");
                            lup.getL().print();

                            System.out.println("\nU Matrix:");
                            lup.getU().print();

                            utils.Spacer();
                            utils.displaySolution_Y(lup.getY(b));

                            System.out.println("\nSolution (x):");
                            utils.displaySolution(x);


                        }

                        default -> System.out.println("Invalid choice ❌");
                    }
                }

                default -> System.out.println("Invalid choice ❌");
            }
        }

        sc.close();
    }
}