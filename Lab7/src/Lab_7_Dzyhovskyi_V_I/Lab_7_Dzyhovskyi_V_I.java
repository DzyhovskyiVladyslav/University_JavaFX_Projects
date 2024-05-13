/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_7_Dzyhovskyi_V_I;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_7_Dzyhovskyi_V_I extends Application {
    

    private boolean order = false;

    private static int DEFAULT_VALUE = 0;
    Label label1 = new Label("Value");
    TextArea log1 = new TextArea();
    Button pause1 = new Button("Pause");
    Button resume1 = new Button("Resume");



    Pane root_pane = new Pane();

    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!order) {
                            DEFAULT_VALUE++;
                            log1.appendText(DEFAULT_VALUE + " <=> ");
                            order = true;
                        }
                    }
                });
                try {
                    Thread.sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (order) {
                            int result = 0;
                            for(int i = DEFAULT_VALUE; i > 0; i--) {
                                result += i;
                            }
                            log1.appendText(result + "\n");
                            order = false;
                        }
                    }
                });
                try {
                    Thread.sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    public void start(Stage stage) throws Exception{
        setupScene();
        setupAction();
        stage.setTitle("Лабораторная работа №7. Студент Дзиговський В.І.");
        stage.setResizable(false);
        javafx.scene.Scene scene = new Scene(root_pane, 350, 250, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        thread1.start();
        thread2.start();
    }

    private void setupAction() {
        pause1.setOnAction(__ -> {
            thread1.suspend();
        });
        resume1.setOnAction(__ -> {
            thread1.resume();
        });
    }


    private void setupScene() {
        GridPane gp = new GridPane();
        log1.setMaxHeight(150);
        log1.setMaxWidth(250);
        log1.setWrapText(true);

        pause1.setLayoutX(25);
        pause1.setLayoutY(5);
        resume1.setLayoutX(100);
        resume1.setLayoutY(5);


        gp.add(log1,0,1);
        gp.add(label1,0,0);
  

        gp.setVgap(10);
        gp.setHgap(50);

        gp.setLayoutX(25);
        gp.setLayoutY(50);


        root_pane.getChildren().addAll(gp, resume1, pause1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
