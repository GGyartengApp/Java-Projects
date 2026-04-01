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



    public double get(int i, int j){
        return data[i][j];
    }

    public void set(int i, int j, double value){
        data[i][j] = value;
    }

    public void swapRows(int r1, int r2){
        double[] temp = data[r1];
        data[r1] = data[r2];
        data[r2] = temp;
    }

    public int getColumns(){
        return columns;
    }

    public int getRows(){
        return rows;
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

}
