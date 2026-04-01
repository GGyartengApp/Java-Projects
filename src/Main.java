import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int n = sc.nextInt();

        Matrix matrix = new Matrix(n, n + 1);

        System.out.println("Enter the augmented matrix:");
        matrix.input(sc);

        System.out.println("\nInitial Matrix:");
        matrix.print();

        GaussianElimination solver = new GaussianElimination();
        solver.eliminate(matrix);

        System.out.println("Final Matrix:");
        matrix.print();
    }
}