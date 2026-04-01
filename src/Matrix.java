import java.util.Scanner;

public class Matrix {
    private double[][] data;
    private int rows, columns;

    //Constructor
    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        data = new double[rows][columns];
    }

    public void input(Scanner sc){
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = sc.nextDouble();
            }
        }
    }

    public void print(){
        for(double[] row : data){
            for (double val: row){
                System.out.printf("%8.2f", val);
            }
            System.out.println();
        }
        System.out.println();
    }

    public double get(int i, int j){
        return data[i][j];
    }

    public void set(int i, int j, double value){
        data[i][j] = value;
    }


}
