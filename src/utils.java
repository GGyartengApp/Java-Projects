public class utils {
    public static void displaySolution(double[] solution) {
        System.out.println("\nSolution:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
        }
    }
}
