/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dzyhovskyi_dz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Dzyhovskyi_DZ extends Application {
    
    private Pane root_pane = new Pane();
    Group group = new Group();
    private TextArea resultTextArea;
    private Button sortButton;
    private Label nText;
    private TextField nInput;
    
    private int n;

    private void writeMatrix(String name, ArrayList<ArrayList<Integer>> matrix) {
        resultTextArea.appendText(name + ":\n"); 
        for(int i = 0; i < n; i++) {
            resultTextArea.appendText("| ");
            for(int j = 0; j < n; j++) {
                resultTextArea.appendText(matrix.get(i).get(j).toString() + " ");
            }
            resultTextArea.appendText("|\n");
        }
    }
    
    
    private void generateMatrix(ArrayList<ArrayList<Integer>> matrix, Random random) {
        for(int i = 0; i < n; i++) {
           ArrayList<Integer> row = new ArrayList<Integer>();
           for(int j = 0; j < n; j++) {
               row.add((int)Math.round(10*random.nextDouble()));
           } 
           matrix.add(row);
        }
    }
    
        public ArrayList<ArrayList<Integer>>  mulMat(ArrayList<ArrayList<Integer>> 
            a, ArrayList<ArrayList<Integer>> b) {
        ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < n; i++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int j = 0; j < n; j++){
                int element = 0;
                for (int r = 0; r < n; r++) {
                   element += a.get(i).get(r)*b.get(r).get(j);
                }
                row.add(element);
            }
            c.add(row); 
        }
        return c;
    }
    
    public ArrayList<ArrayList<Integer>> subMat(ArrayList<ArrayList<Integer>> a, 
            ArrayList<ArrayList<Integer>> b) {
        ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < n; i++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int j = 0; j < n; j++){
                row.add(a.get(i).get(j)-b.get(i).get(j));
            }
            c.add(row);
        }
        return c;
    }
    
        public ArrayList<ArrayList<Integer>> sumMat(ArrayList<ArrayList<Integer>> a, 
            ArrayList<ArrayList<Integer>> b) {
        ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < n; i++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int j = 0; j < n; j++){
                row.add(a.get(i).get(j)+b.get(i).get(j));
            }
            c.add(row);
        }
        return c;
    }
    
    private void setScene() {
        nText = new Label("n = ");
        nInput = new TextField();
        nInput.setPrefSize(50,6);
        nInput.setText("3");
        sortButton = new Button();
        sortButton.setText("Сгенерувати та розрахувати");
        resultTextArea = new TextArea();
        resultTextArea.setLayoutX(25);
        resultTextArea.setLayoutY(20);
        resultTextArea.setPrefSize(550, 650);
        resultTextArea.setWrapText(true);
        GridPane gp = new GridPane();
        gp.setHgap(50);
        gp.setVgap(10);
        gp.setPadding(new Insets(700, 0, 0, 20));
        GridPane gp2 = new GridPane();
        gp2.add(nText, 0, 0);
        gp2.add(nInput, 1, 0);
        gp.add(gp2, 2, 0);
        gp.add(sortButton, 2, 1); 
        group.getChildren().addAll(gp);
        root_pane.getChildren().addAll(resultTextArea, group);
    }
    
    private void setAction() {
        sortButton.setOnAction((ActionEvent event) -> {
            ArrayList<ArrayList<Integer>> МА = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> MB = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> MC = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> MT = new ArrayList<ArrayList<Integer>>();
            n = Integer.parseInt(nInput.getText());
            final Random random = new Random();
            resultTextArea.appendText("n: "+ n + "\n");
            generateMatrix(МА, random);
            writeMatrix("МА", МА);
            generateMatrix(MB, random);
            writeMatrix("MB", MB);
            generateMatrix(MC, random);
            writeMatrix("MC", MC);
            writeMatrix("MA-MB", subMat(МА, MB));
            writeMatrix("MA+MC", sumMat(МА, MC));
            MT = mulMat(subMat(МА, MB), sumMat(МА, MC));
            writeMatrix("MT", MT);
        });
    }

    
   
        

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Лабораторная работа №2.2. Дзиговський В.І.");
        primaryStage.setResizable(true);
        setScene();
        setAction();
        primaryStage.setScene(new Scene(root_pane, 600, 800, Color.TRANSPARENT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }   
}
