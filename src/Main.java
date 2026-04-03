import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====**** Linear Algebra Calculator ****====");
            System.out.println("1. General Matrix Operations");
            System.out.println("2. Determinant");
            System.out.println("3. Gaussian Elimination");
            System.out.println("4. Gauss-Jordan (RREF)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            if (choice == 5) {
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
                        System.out.println("\n--- General Matrix Operations ---");
                        System.out.println("1. Matrix Multiplication");
                        System.out.println("2. Matrix Addition");
                        System.out.println("0. Back");
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
                                utils.Spacer();


                                Matrix result = MatrixOperations.add(matrix, m2);

                                utils.showLoading("Multiplying", 3, 500);
                                System.out.println("Result:");
                                result.print();
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
                    System.out.println("Final Matrix after Gaussian Elimination:");
                    matrix.print();
                    double[] solution = gauss.getSolution(matrix); // make sure this exists in GaussJordan
                    utils.displaySolution(solution);
                }

                case 4 -> { // Gauss-Jordan
                    GaussJordan gj = new GaussJordan(matrix);
                    gj.reduceToRREF();
                    System.out.println("Matrix in Reduced Row Echelon Form:");
                    matrix.print();
                    double[] solution = gj.getSolution(); // make sure this exists in GaussJordan
                    utils.displaySolution(solution);
                }

                default -> System.out.println("Invalid choice ❌");
            }
        }

        sc.close();
    }
}