public class utils {
    public static void displaySolution(double[] solution) {
        System.out.println("\nSolution:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
        }
    }

    public static void displaySolution_Y(double[] solution) {
        System.out.println("\nSolution:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf(Colors.CYAN + "y%d = %.2f\n" + Colors.RESET, i + 1, solution[i]);
        }
    }

    public static void Spacer(){
        System.out.println("");

    }

    public static void showLoading(String message, int dotCount, int ms) throws InterruptedException {
        // Print the message with animated dots
        System.out.print(message);
        for (int i = 0; i < dotCount; i++) {
            System.out.print(".");
            Thread.sleep(ms);
        }

        // Clear the message dynamically based on its length
        int totalLength = message.length() + dotCount;
        System.out.print("\r"); // move cursor to start
        for (int i = 0; i < totalLength; i++) {
            System.out.print(" "); // overwrite with spaces
        }
        System.out.print("\r"); // move cursor back to start
    }

    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }
}
