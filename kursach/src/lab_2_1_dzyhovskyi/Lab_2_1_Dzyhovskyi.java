/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_1_dzyhovskyi;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static lab_2_1_dzyhovskyi.Lab_2_1_Dzyhovskyi.copy;
import static lab_2_1_dzyhovskyi.Lab_2_1_Dzyhovskyi.matrixDeterminant;
import static lab_2_1_dzyhovskyi.Lab_2_1_Dzyhovskyi.resultTextArea;


/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_2_1_Dzyhovskyi extends Application {
    
    private Pane root_pane = new Pane();
    public static TextArea resultTextArea = new TextArea();
    
    public static double matrixDeterminant(double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow(-1, (double) i) * matrixDeterminant(temporary);
        }
        return (result);
    }

    public static double[][] copy(double[][] matrix) {
        double[][] copy = new double[4][4];
        for (int idx = 0; idx < 4; ++idx)
            copy[idx] = matrix[idx].clone();
        return copy;
    }
    
    public void iteration() {
        double x1 = 0, x2 = 0, x3 = 0, x4 = 0;
        double[][] A = {{5, 3, -2, 1}, {7, -13, 3, 1}, {1, -2, 15, -7}, {3, -8, 2, 14}};
        double[] B = {14, -4, 14, 22};
        double[] Xn = {0, 0, 0, 0};
        double eps = 0.000001;
        int count = 0;
        long startTime = System.currentTimeMillis();
        resultTextArea.appendText("Метод простих ітерацій\n");
        do {
            Xn[0] = (B[0]-(x2*A[0][1]+x3*A[0][2]+x4*A[0][3]))/A[0][0];
            Xn[1] = (B[1]-(x1*A[1][0]+x3*A[1][2]+x4*A[1][3]))/A[1][1];
            Xn[2] = (B[2]-(x1*A[2][0]+x2*A[2][1]+x4*A[2][3]))/A[2][2];
            Xn[3] = (B[3]-(x1*A[3][0]+x2*A[3][1]+x3*A[3][2]))/A[3][3];
            count = count + 1;
            if(Math.abs(Xn[0]-x1) < eps && Math.abs(Xn[1]-x2) < eps 
                    && Math.abs(Xn[2]-x3) < eps && Math.abs(Xn[3]-x4) < eps) {
                break;
            }
            x1 = Xn[0];
            x2 = Xn[1];
            x3 = Xn[2];
            x4 = Xn[3];
        }while(true);
        resultTextArea.appendText("X1 = " + x1 + "\n");
        resultTextArea.appendText("X2 = " + x2 + "\n");
        resultTextArea.appendText("X3 = " + x3 + "\n");
        resultTextArea.appendText("X4 = " + x4 + "\n");
        long timeSpent = System.currentTimeMillis() - startTime;
        resultTextArea.appendText("Час " + timeSpent + " мілісекунд\n");
        resultTextArea.appendText("Кількість ітерацій " + count + "\n");
    }
    
    public void zeidel() {
        double x1 = 14, x2 = -4, x3 = 14, x4 = 22;
        double[][] A = {{8, 5, -10, 8}, {1, 5, 3, -12}, {3, -2, 5, 9}, {10, -2, -15, 29}};
        double[] B = {14, 15, -12, -13};
        double[] Xk = {0, 0, 0, 0};
        double eps = 0.000001;
        int count = 0;
        double max;
        long startTime = System.currentTimeMillis();
        resultTextArea.appendText("Метод Зейделя\n");
        do {
            Xk[0] = (B[0] - x2*A[0][1]-x3*A[0][2]-x4*A[0][3])/A[0][0];
            Xk[1] = (B[1] - Xk[0]*A[1][0]-x3*A[1][2]-x4*A[1][3])/A[1][1];
            Xk[2] = (B[2] - Xk[0]*A[2][0]-Xk[1]*A[2][1]-x4*A[2][3])/A[2][2];
            Xk[3] = (B[3] - Xk[0]*A[3][0]-Xk[1]*A[3][1]-Xk[2]*A[3][2])/A[3][3];
            max = Math.abs(Xk[0] - x1);
            if (Math.abs(Xk[1]-x2) > max) {
                max = Math.abs(Xk[1]-x2);
            }    
            if (Math.abs(Xk[2]-x3) > max) {
                max = Math.abs(Xk[2]-x3);
            }     
            if (Math.abs(Xk[3]-x4) > max) {
                max = Math.abs(Xk[3]-x4);
            }   
            x1 = Xk[0];
            x2 = Xk[1];
            x3 = Xk[2];
            x4 = Xk[3];
            count++;
        }while(max > eps);
        resultTextArea.appendText("X1 = " + x1 + "\n");
        resultTextArea.appendText("X2 = " + x2 + "\n");
        resultTextArea.appendText("X3 = " + x3 + "\n");
        resultTextArea.appendText("X4 = " + x4 + "\n");
        long timeSpent = System.currentTimeMillis() - startTime;
        resultTextArea.appendText("Час " + timeSpent + " мілісекунд\n");
        resultTextArea.appendText("Кількість ітерацій " + count + "\n");
    }
    
    public void linier() {
        double x1, x2, x3, x4;
        double[][] A = {{5, 3, -2, 1}, {7, -13, 3, 1}, {1, -2, 15, -7}, {3, -8, 2, 14}};
        int[] B = {14, -4, 14, 22};
        resultTextArea.appendText("Послідовно\n");
        long startTime = System.currentTimeMillis();
        double[][] A1 = copy(A);
        double[][] A2 = copy(A);
        double[][] A3 = copy(A);
        double[][] A4 = copy(A);
            for (int i = 0; i < 4; i++) {
                A1[i][0] = B[i];
            }
            for (int i = 0; i < 4; i++) {
                A2[i][1] = B[i];
            }
            for (int i = 0; i < 4; i++) {
                A3[i][2] = B[i];
            }
            for (int i = 0; i < 4; i++) {
                A4[i][3] = B[i];
            }
            double determ = matrixDeterminant(A);
            x1 = matrixDeterminant(A1) / determ;
            x2 = matrixDeterminant(A2) / determ;
            x3 = matrixDeterminant(A3) / determ;
            x4 = matrixDeterminant(A4) / determ;
            resultTextArea.appendText("X1 = " + x1 + "\n");
            resultTextArea.appendText("X2 = " + x2 + "\n");
            resultTextArea.appendText("X3 = " + x3 + "\n");
            resultTextArea.appendText("X4 = " + x4 + "\n");
            long timeSpent = System.currentTimeMillis() - startTime;
            resultTextArea.appendText("Час " + timeSpent + " мілісекунд\n");
    }
    
    @Override
    public void start(Stage primaryStage) {
        resultTextArea.setLayoutX(25);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(550, 450);
        resultTextArea.setWrapText(true);
        root_pane.getChildren().addAll(resultTextArea);
        primaryStage.setTitle("Лабораторная работа №2.1. Дзиговський В.І.");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root_pane, 600, 500, Color.TRANSPARENT));
        primaryStage.show();
        iteration();
        zeidel();
        linier();
        new ForkJoinPool(10).invoke(new Paralel());
 

    }

    public static void main(String[] args) {
        launch(args);
    }


}

class Paralel extends RecursiveTask<Void> {
 
    protected Void compute() {
        double[][] A = {{5, 3, -2, 1}, {7, -13, 3, 1}, {1, -2, 15, -7}, {3, -8, 2, 14}};
        int[] B = {14, -4, 14, 22};
        long startTime = System.currentTimeMillis();
        Lab_2_1_Dzyhovskyi.resultTextArea.appendText("Паралельно\n");
        Group g = new Group();
            Task<Void> task1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //необхідні обчислення
                                //  System.out.println("1");
                                double[][] A1 = Lab_2_1_Dzyhovskyi.copy(A);
                                for (int i = 0; i < 4; i++) {
                                    A1[i][0] = B[i];
                                }
                                double determ = Lab_2_1_Dzyhovskyi.matrixDeterminant(A);
                                double x1 = Lab_2_1_Dzyhovskyi.matrixDeterminant(A1) / determ;
                                Lab_2_1_Dzyhovskyi.resultTextArea.appendText("X1 = " + x1 + "\n");
                                
                            }
                        });
                        Thread.sleep(10000);
                    }
                }
            };
            Group f = new Group();
            Task<Void> task2 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
 
                                double[][] A2 = Lab_2_1_Dzyhovskyi.copy(A);
                                for (int i = 0; i < 4; i++) {
                                    A2[i][1] = B[i];
                                }
                                double determ = Lab_2_1_Dzyhovskyi.matrixDeterminant(A);
                                double x2 = Lab_2_1_Dzyhovskyi.matrixDeterminant(A2) / determ;                   
                                Lab_2_1_Dzyhovskyi.resultTextArea.appendText("X2 = " + x2 + "\n");
                            }
                        });
                        Thread.sleep(10000);
                    }
                }
            };
            Group h = new Group();
            Task<Void> task3 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                double[][] A3 = Lab_2_1_Dzyhovskyi.copy(A);
                                for (int i = 0; i < 4; i++) {
                                    A3[i][2] = B[i];
                                }
                                double determ = Lab_2_1_Dzyhovskyi.matrixDeterminant(A);
                                double x3 = Lab_2_1_Dzyhovskyi.matrixDeterminant(A3) / determ;
                                Lab_2_1_Dzyhovskyi.resultTextArea.appendText("X3 = " + x3 + "\n");

                            }
                        });

                        Thread.sleep(10000);
                    }
                }
            };
            Group k = new Group();
            Task<Void> task4 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                double[][] A4 = Lab_2_1_Dzyhovskyi.copy(A);
                                for (int i = 0; i < 4; i++) {
                                    A4[i][3] = B[i];
                                }
                                double determ = Lab_2_1_Dzyhovskyi.matrixDeterminant(A);
                                double x4 = Lab_2_1_Dzyhovskyi.matrixDeterminant(A4) / determ;
                                Lab_2_1_Dzyhovskyi.resultTextArea.appendText("X4 = " + x4 + "\n");
                            }
                        });
                        Thread.sleep(10000);
                    }
                }
            };
            Task<Void> lastTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                long timeSpent = System.currentTimeMillis() - startTime;
                                Lab_2_1_Dzyhovskyi.resultTextArea.appendText("Час " + timeSpent + " мілісекунд\n");
                            }
                        });
                        //=== затримка
                        Thread.sleep(1000000);
                    }
                }
            };
            do {
                Thread th1 = new Thread(task1);
                th1.start();
                Thread th2 = new Thread(task2);
                th2.start();
                Thread th3 = new Thread(task3);
                th3.start();
                Thread th4 = new Thread(task4);
                th4.start();
                break;
            }while (true);
            task1.cancel();
            task2.cancel();
            task3.cancel();
            task4.cancel();
            Thread last = new Thread(lastTask);
            last.start();
            return null;
        }
 }