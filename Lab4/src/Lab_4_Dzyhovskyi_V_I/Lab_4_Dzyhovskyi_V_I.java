//==============================================================================
// Лабораторна работа №4
// class: Lab_4_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_4_Dzyhovskyi_V_I;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public class Lab_4_Dzyhovskyi_V_I extends Application {
    
    private Pane root_pane = new Pane();
    private int numberOfCores = Runtime.getRuntime().availableProcessors();
    private Label arraySizeText = new Label("Размер массива");
    private Label threadsNumberText = new Label("Количество потоков ");
    private Button resultButton = new Button("Вычислить");
    private CheckBox[] coreArray = new CheckBox[8];
    private TextArea resultTextArea = new TextArea();
    private TextField arraySizeInput = new TextField();
    private TextField threadsNumberInput = new TextField();
    private int numberOfActiveCores = 0;

    
    private void setupScene() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10,10,10, 10));
        GridPane gp1 = new GridPane();
        gp1.setPadding(new Insets(10,10,10, 10));
        gp1.setHgap(30);
        gp1.setVgap(10);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                int n = 2*(i+1) - 2 + j;
                coreArray[n] = new CheckBox(Integer.toString(n+1));
                gp1.add(coreArray[n], i, j);
            }
        }
        arraySizeInput.setPrefSize(50,6);
        arraySizeInput.setText("10");
        threadsNumberInput.setPrefSize(50,6);
        threadsNumberInput.setText("10");
        GridPane gp2 = new GridPane();
        gp2.setHgap(20);
        gp2.setVgap(5);
        gp2.setPadding(new Insets(10,10,10, 10));
        gp2.add(arraySizeText,0,0);
        gp2.add(threadsNumberText,0,1);
        gp2.add(threadsNumberInput,1,1);
        gp2.add(arraySizeInput,1,0);
        gp2.add(resultButton,0,3);
        for(int i = numberOfCores; i < coreArray.length; i++){
            coreArray[i].setDisable(true);
        }
        resultTextArea.setLayoutX(300);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(250, 200);
        resultTextArea.setText("Лабораторная работа № 4");
        resultTextArea.setWrapText(true);
        gp.add(gp1, 0, 0);
        gp.add(gp2, 0, 1);
        root_pane.getChildren().addAll(gp, resultTextArea);
    }
    
    private void onAction() {
        resultButton.setOnAction(__ -> {
            if(numberOfActiveCores > 0 && Integer.parseInt(threadsNumberInput.getText()) > 0 && Integer.parseInt(arraySizeInput.getText()) > 0 ) {
                int arrSize = Integer.parseInt(arraySizeInput.getText());
                int numberOfThreads = Integer.parseInt(threadsNumberInput.getText()) * numberOfActiveCores;
                int[] array = new int[arrSize];
                Random random = new Random();
                for(int i = 0; i < array.length; i++){
                    array[i] = random.nextInt(array.length);
                }
                Date currentTime = new Date();
                resultTextArea.appendText("\nЧисло процессоров: " + numberOfActiveCores + "\nРезультат: " + calculateResult(array, new ForkJoinPool(numberOfThreads)));
                Date newTime = new Date();
                long msDelay = newTime.getTime() - currentTime.getTime();
                resultTextArea.appendText("\nВремя выполнения: " + msDelay + "ms");
            }
        });
        for (CheckBox box : coreArray) {
            box.setOnAction(__ -> {
                if(box.isSelected()) {
                    numberOfActiveCores++;
                } else {
                    numberOfActiveCores--;
                }
            });
        }
    }
    
    private Double calculateResult(int[] numbers, ForkJoinPool jp){
        return jp.invoke(new Min(numbers, 0, numbers.length));
    }

    @Override
    public void start(Stage primaryStage) {
        setupScene();
        onAction();
        primaryStage.setTitle("Лабораторная работа №4. Дзиговський В.І.");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Min extends RecursiveTask<Double> {
    int front;
    int rear;
    int[] array;

    Min(int[] array, int front, int rear) {
        this.array = array;
        this.front = front;
        this.rear = rear;
    }

    protected Double compute() {
        if(rear - front <= 10) {
            double min = array[front];
            for(int i = front; i <rear; ++i)
                if(min >array[i])
                    min = array[i];
            return min;
            } else {
            int mid = front + (rear - front) / 2;
            Min left  = new Min(array, front, mid);
            Min right = new Min(array, mid, rear);
            left.fork();
            double rightResult = right.compute();
            double leftResult  = left.join();
            if(leftResult < rightResult)
                return leftResult;
            else
                return rightResult;
        }
    }
}
