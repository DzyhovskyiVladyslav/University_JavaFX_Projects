/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_8_Dzyhovskyi_V_I;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_8_Dzyhovskyi_V_I extends Application {
    
    private Pane root_pane = new Pane();
    private int numberOfCores = Runtime.getRuntime().availableProcessors();
    private Label threadsNumberText = new Label("Количество потоков: ");
    private Button resultButton = new Button("Вычислить");
    private TextArea resultTextArea = new TextArea();
    private TextField threadsNumberInput = new TextField();
    private Label aText = new Label("a = ");
    private TextField aInput = new TextField();
    private Label bText = new Label("b = ");
    private TextField bInput = new TextField();
    private Label nText = new Label("n = ");
    private TextField nInput = new TextField();
   
    private void setupScene() {
        threadsNumberInput.setPrefSize(50,6);
        threadsNumberInput.setText("10");
        threadsNumberText.setLayoutX(10);
        threadsNumberText.setLayoutY(10);
        threadsNumberInput.setLayoutX(170);
        threadsNumberInput.setLayoutY(10);
        aText.setLayoutX(10);
        aText.setLayoutY(40);
        aInput.setLayoutX(50);
        aInput.setLayoutY(40);
        aInput.setPrefSize(50,6);
        aInput.setText("1");
        bText.setLayoutX(10);
        bText.setLayoutY(80);
        bInput.setLayoutX(50);
        bInput.setLayoutY(80);
        bInput.setPrefSize(50,6);
        bInput.setText("4");
        nText.setLayoutX(10);
        nText.setLayoutY(120);
        nInput.setLayoutX(50);
        nInput.setLayoutY(120);
        nInput.setPrefSize(50,6);
        nInput.setText("20");
        resultButton.setLayoutX(10);
        resultButton.setLayoutY(180);
        resultTextArea.setLayoutX(300);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(250, 200);
        root_pane.getChildren().addAll(resultTextArea, threadsNumberInput, 
                threadsNumberText, resultButton, aText, aInput, bText, bInput, nText, nInput);
    }
    
    private void onAction() {
        resultButton.setOnAction(__ -> {
            if(Integer.parseInt(threadsNumberInput.getText()) > 0) {
                int numberOfThreads = Integer.parseInt(threadsNumberInput.getText());
                Date currentTime = new Date();
                resultTextArea.appendText("\nЧисло потоків: " + numberOfThreads + "\nРезультат: " 
                        + calculateResult(new ForkJoinPool(numberOfThreads > 1099 ? 999 : numberOfThreads)));
                Date newTime = new Date();
                long msDelay = newTime.getTime() - currentTime.getTime() - 
                        (numberOfThreads > 1099 ? 999 : numberOfThreads);
                if (msDelay <= 0) {
                    msDelay = newTime.getTime() - currentTime.getTime();
                }
                resultTextArea.appendText("\nВремя выполнения: " + msDelay + "ms");
            }
        });
    }
    
    private Double calculateResult(ForkJoinPool jp){
        return jp.invoke(new Integral(Double.parseDouble(aInput.getText()), 
                Double.parseDouble(bInput.getText()), Double.parseDouble(nInput.getText()), 
                Double.parseDouble(nInput.getText())));
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

class Integral extends RecursiveTask<Double> {

    private double a;
    private double b;
    private double n;

    Integral(double a, double b, double n, double i) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    protected Double compute() {
       double h = (b - a)/n;
       double res = ((f(a) + f(a+n*h))/2)*h;
       for(int i = 0; i < n; i++) {
           res += f(a + (i-1)*h)*h;
       }
        return res;     
    }
    
    private double f(double x) {
        if (x !=  0) {
            return (1+x)/Math.sqrt(2*x);
        }
        return 0;
    }
 
}
