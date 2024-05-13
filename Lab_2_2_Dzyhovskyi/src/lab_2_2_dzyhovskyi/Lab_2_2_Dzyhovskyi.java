/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_2_dzyhovskyi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/*
Вариант 5 = 0 : length = 160px, width = 25px, color = black
 */
public class Lab_2_2_Dzyhovskyi extends Application {
    
    private Pane root_pane = new Pane();
    private TextArea resultTextArea = new TextArea();
    private int n;
    private ArrayList<ArrayList<Double>> A = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> A1 = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> A2 = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> B2 = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> C2 = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> Y3 = new ArrayList<ArrayList<Double>>();
    
    private ArrayList<Double> b = new ArrayList<Double>();
    private ArrayList<Double> b1 = new ArrayList<Double>();
    private ArrayList<Double> c1 = new ArrayList<Double>();
    private ArrayList<Double> y1 = new ArrayList<Double>();
    private ArrayList<Double> y2 = new ArrayList<Double>();
    private ArrayList<Double> x = new ArrayList<Double>();
    
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
    
    public void writeVector(String name, ArrayList<Double> matrix) {
        resultTextArea.appendText(name + ":\n"); 
        for(int i = 0; i < n; i++) {
            resultTextArea.appendText("| " + matrix.get(i).toString() + " |\n");
        }
    }
   
    public void generate() {
        final Random random = new Random();
        n = random.nextInt(3) + 2;
        resultTextArea.appendText("n: "+ n + "\n");
        generateMatrix(A, random);
        writeMatrix("A", A);
        generateMatrix(A1, random);
        writeMatrix("A1", A1);
        generateMatrix(A2, random);
        writeMatrix("A2", A2);
        generateMatrix(B2, random);
        writeMatrix("B2", B2);
        generateVector(b1, random);
        writeVector("b1", b1);
        generateVector(c1, random);
        writeVector("c1", c1);
        generateB();
        writeVector("b", b);
        generateC2();
        writeMatrix("C2", C2);
    }
    
    public void generateC2(){
        for(int i = 1; i <= n; i++) {
           ArrayList<Double> row = new ArrayList<Double>();
           for(int j = 1; j <= n; j++) {
               row.add((double)Math.round((1/(double)(i+2*j))*100)/100);
           } 
           C2.add(row);
        }
    }
    
    public void generateB() { 
        for(int i = 1; i <= n; i++){
            if(i%2 == 0){
                b.add((double)Math.round((1/(Math.pow(i, 2)+2))*100)/100);
            } else {
                 b.add((double)Math.round((1/(double) i)*100)/100);
            }
        }
    }
    
    public void generateVector(ArrayList<Double> vector, Random random) {
        for(int i = 0; i < n; i++) {
            vector.add((double)Math.round(99*random.nextDouble())/10);
        } 
    }
    
    public void generateMatrix(ArrayList<ArrayList<Double>> matrix, Random random) {
        for(int i = 0; i < n; i++) {
           ArrayList<Double> row = new ArrayList<Double>();
           for(int j = 0; j < n; j++) {
               row.add((double)Math.round(10*random.nextDouble()));
           } 
           matrix.add(row);
        }
    }
    
    public void calculate() {
        y1 = mulMatOnVec(A, b);
        writeVector("y1", y1);
        y2 = mulMatOnVec(A1, sumVec(b1, c1));
        writeVector("y2", y2);
        Y3 = mulMatOnMat(A2, subMat(B2, C2));
        writeMatrix("Y3", Y3);
        for (int K = 1; K < 10; K++) {
            x = sumVec(mulMatOnVec(mulMatOnMat(Y3, Y3), y2), 
                    mulMatOnVec(Y3, sumVec(y1, mulCofOnVec(K, y2))));
            resultTextArea.appendText("K = " + K + ":\n");
            writeVector("x", x);
        }
    }
    
    public ArrayList<Double> mulCofOnVec(double a, ArrayList<Double> b) {
        ArrayList<Double> c = new  ArrayList<Double>();
        for(int i = 0; i < n; i++) {
            c.add((double)Math.round(a*b.get(i)*100)/100);
         }
        return c;
    }
    
    public ArrayList<ArrayList<Double>>  mulMatOnMat(ArrayList<ArrayList<Double>> 
            a, ArrayList<ArrayList<Double>> b) {
        ArrayList<ArrayList<Double>> c = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i < n; i++){
            ArrayList<Double> row = new ArrayList<Double>();
            for(int j = 0; j < n; j++){
                double element = 0;
                for (int r = 0; r < n; r++) {
                   element += a.get(i).get(r)*b.get(r).get(j);
                }
                row.add((double)Math.round(element*100)/100);
            }
            c.add(row); 
        }
        return c;
    }
    
    public ArrayList<ArrayList<Double>> subMat(ArrayList<ArrayList<Double>> a, 
            ArrayList<ArrayList<Double>> b) {
        ArrayList<ArrayList<Double>> c = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i < n; i++){
            ArrayList<Double> row = new ArrayList<Double>();
            for(int j = 0; j < n; j++){
                row.add((double) Math.round((a.get(i).get(j)-b.get(i).get(j))*100)/100);
            }
            c.add(row);
        }
        return c;
    }
        
    public ArrayList<Double> sumVec(ArrayList<Double> a,  ArrayList<Double> b) {
         ArrayList<Double> c = new  ArrayList<Double>();
         for(int i = 0; i < n; i++) {
             c.add((double) Math.round((a.get(i) + b.get(i))*100)/100);
         }
         return c;
    }
    
    public ArrayList<Double> mulMatOnVec(ArrayList<ArrayList<Double>> a, ArrayList<Double> b) {
        ArrayList<Double> c = new  ArrayList<Double>();
        for(int i = 0; i < n; i++) {
            double element = 0;
            for(int j = 0; j < n; j++) {
                element += a.get(i).get(j)*b.get(j);
            }
            c.add((double)Math.round(element*100)/100);
         }
        return c;
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
        primaryStage.setScene(new Scene(root_pane, 600, 600, Color.TRANSPARENT));
        primaryStage.show();
       generate();
        calculate();
    }
    
    public void setMatrix(ArrayList<ArrayList<Double>> matrix) {
        for(int i = 0; i < n; i++) {
           ArrayList<Double> row = new ArrayList<Double>();
           for(int j = 0; j < n; j++) {
               row.add((double)i+j+1);
           } 
           matrix.add(row);
        }
    }
    
    public void setVector(ArrayList<Double> vector) {
        for(int i = 0; i < n; i++) {
            vector.add((double)i);
        } 
    }

    public static void main(String[] args) {
        launch(args);
    }

}