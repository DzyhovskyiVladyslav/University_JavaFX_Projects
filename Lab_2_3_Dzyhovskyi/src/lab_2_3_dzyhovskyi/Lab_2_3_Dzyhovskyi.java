/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_3_dzyhovskyi;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_2_3_Dzyhovskyi extends Application {
    
    
    private Pane root_pane = new Pane();
    private TextArea resultTextArea = new TextArea();
    private int n;
    private ArrayList<ArrayList<Double>> A = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> C = new ArrayList<ArrayList<Double>>();
    
    public void writeMatrix(String name, ArrayList<ArrayList<Double>> matrix) {
        resultTextArea.appendText(name + ":\n"); 
        for(int i = 0; i < n; i++) {
            resultTextArea.appendText("| ");
            for(int j = 0; j < n; j++) {
                resultTextArea.appendText(matrix.get(i).get(j).toString() + " ");
            }
            resultTextArea.appendText("|\n");
        }
    }
   
    public void generate() {
        final Random random = new Random();
        n = random.nextInt(4) + 2;
        resultTextArea.appendText("n: "+ n + "\n");
        generateMatrixA();
        generateMatrixB();
    }   
    
    public void generateMatrixA() {
        for(int i = 1; i <= n; i++) {
           ArrayList<Double> row = new ArrayList<Double>();
           for(int j = 1; j <= n; j++) {
               if(i == j) {
                row.add((double)i*(i+1));
               } else {
                   row.add((double)0);
               }
           } 
           A.add(row);
        }
        writeMatrix("A", A);
    }
    
    public void generateMatrixB() {
        for(int i = 0; i < n; i++) {
           ArrayList<Double> row = new ArrayList<Double>();
           for(int j = 0; j < n; j++) {
               if(i+j < n) {
                    row.add((double)new Random().nextInt(100));
               } else {
                   row.add((double)0);
               }
           } 
           B.add(row);
        }
        writeMatrix("B", B);
    }
    
    public void countOneTime() {
        resultTextArea.appendText("Одноразове присвоєння\n");
        ArrayList<ArrayList<Double>> C = new ArrayList<ArrayList<Double>>();
        int counter = 0;
            for (int i = 0; i < n; i++) {
                ArrayList<Double> row = new ArrayList<Double>();
                for (int j = 0; j < n; j++) {
                    double element = 0;
                    for (int r = 0; r < n; r++) {
                        element += A.get(i).get(r)*B.get(r).get(j);
                        counter++;
                    }
                    row.add(element);
                }
                C.add(row);
            }
            writeMatrix("C", C);
            resultTextArea.appendText("Кількість операцій: " + counter + "\n");
    }
    
    public void countRecursive() {
        resultTextArea.appendText("Локально-рекрсивне\n");
        ArrayList<ArrayList<Double>> C = new ArrayList<ArrayList<Double>>();
        int counter = 0;
            for (int i = 0; i < n; i++) {
                ArrayList<Double> row = new ArrayList<Double>();
                for (int j = 0; j < n; j++) {
                    row.add(A.get(i).get(i)*B.get(i).get(j));
                    counter++;
                }
                C.add(row);
            }
            writeMatrix("C", C);
            resultTextArea.appendText("Кількість операцій: " + counter);
    }
   
    
    @Override
    public void start(Stage primaryStage) {
        resultTextArea.setLayoutX(25);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(550, 450);
        resultTextArea.setWrapText(true);
        root_pane.getChildren().addAll(resultTextArea);
        primaryStage.setTitle("Лабораторная работа №2.2. Дзиговський В.І.");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root_pane, 600, 500, Color.TRANSPARENT));
        primaryStage.show();
        generate();
        countOneTime();
        countRecursive();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
