/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dzyhovskyi_task4;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Dzyhovskyi_Task4 extends Application {
    
    private Pane root_pane = new Pane();
    private Label threadsNumberText = new Label("N = ");
    private Button resultButton = new Button("Розрахувати");
    private TextArea resultTextArea = new TextArea();
    private TextField threadsNumberInput = new TextField();

    
    private void setupScene() {
        threadsNumberInput.setPrefSize(50,6);
        threadsNumberInput.setText("10");
        threadsNumberText.setLayoutX(10);
        threadsNumberText.setLayoutY(15);
        threadsNumberInput.setLayoutX(40);
        threadsNumberInput.setLayoutY(10);
        resultButton.setLayoutX(10);
        resultButton.setLayoutY(50);
        resultTextArea.setLayoutX(150);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(350, 200);
        root_pane.getChildren().addAll(resultTextArea, threadsNumberInput, threadsNumberText, resultButton);
    }
    
    private void onAction() {
        resultButton.setOnAction(__ -> {
            if(Integer.parseInt(threadsNumberInput.getText()) > 0) {
                int n = Integer.parseInt(threadsNumberInput.getText());
                ArrayList<Integer> A = createArray(n);
                ArrayList<Integer> B = createArray(n);
                ArrayList<Integer> C = createArray(n);
                if (n < 10) {
                    resultTextArea.appendText("\nA = " + A + "\nB = " + B + "\nC = " + C 
                            + "\nРезультат: " + calculateResult(new ForkJoinPool(20),n,A,B,C));
                }
                else {
                    resultTextArea.appendText("\nРезультат: " + calculateResult(new ForkJoinPool(20),n,A,B,C));
                }
            }
        });
    }
    
    private ArrayList<Integer> createArray(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(Math.round((float) (Math.random()*10)));
        }
        return res;
    }
    
    private ArrayList<Integer> calculateResult(ForkJoinPool jp, int n, ArrayList<Integer>
            A, ArrayList<Integer> B, ArrayList<Integer> C){
        return jp.invoke(new Result(n, A, B, C));
    }

    @Override
    public void start(Stage primaryStage) {
        setupScene();
        onAction();
        primaryStage.setTitle("Лабораторная работа №8. Дзиговський В.І.");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Result extends RecursiveTask<ArrayList<Integer>> {

    int n;
    ArrayList<Integer> A;
    ArrayList<Integer> B;
    ArrayList<Integer> C;

    Result(int n, ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
        this.n = n;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    protected ArrayList<Integer> compute() {
        int max = new ForkJoinPool(20).invoke(new Max(A));
        ArrayList<Integer> res = new ForkJoinPool(20).invoke(new Formul(n,A,B,C));
        for(int i = 0; i < n; i++) {
            res.set(i, res.get(i)*max);
        }
        return res;
    }  
}

class Max extends RecursiveTask<Integer> {

    ArrayList<Integer> A;

    Max( ArrayList<Integer> A) {
        this.A = A;
    }

    protected Integer compute() {
        int max = 0;
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) > max) {
                max = A.get(i);
            }
        }
        return max;
    }    
}

class Formul extends RecursiveTask<ArrayList<Integer>> {

    int n;
    ArrayList<Integer> A;
    ArrayList<Integer> B;
    ArrayList<Integer> C;

    Formul(int n, ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
        this.n = n;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    protected ArrayList<Integer> compute() {
        ArrayList<Integer> res = new ArrayList();
        for(int i = 0; i < n; i++){
            res.add(A.get(i)-B.get(i)+C.get(i));
        }
        return res;
    }  
}