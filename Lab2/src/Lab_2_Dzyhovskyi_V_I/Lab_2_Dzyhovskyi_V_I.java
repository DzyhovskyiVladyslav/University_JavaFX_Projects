//==============================================================================
// Лабораторна работа №2
// class: Lab_2_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_2_Dzyhovskyi_V_I;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public class Lab_2_Dzyhovskyi_V_I extends Application {
    
    Pane root_pane = new Pane();
    Group group = new Group();
    Rectangle[] rect = new Rectangle[4];
    Text[] rectText = new Text[4];
    Text sumText;
    Thread[] th = new Thread[5];
    Color color = Color.GREY;
    boolean[] direction = new boolean[4];
    boolean shutdown = false;
    int MAX_HEIGHT = 130;
    
    private void createThreads(){
        for(int i = 0; i < 4; i++)  {
            th[i] = new Thread(createRectTask(i));
            th[i].setName("Поток");
            th[i].start();
        }
        th[4] = new Thread(createLastTask());
        th[4].setName("Поток");
        th[4].start();
    }
    
    private Task createRectTask(int taskNumber){
        direction[taskNumber] = true;
        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                while (!shutdown) {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            if (direction[taskNumber]) {
                               rect[taskNumber].setHeight(rect[taskNumber].getHeight() + 1);
                            } else {
                                rect[taskNumber].setHeight(rect[taskNumber].getHeight() - 1);
                            }
                            if (rect[taskNumber].getHeight() <= 1) {
                               direction[taskNumber] = true;
                            }
                            if (rect[taskNumber].getHeight() >= MAX_HEIGHT) {
                               direction[taskNumber] = false;
                            }
                            double s = rect[taskNumber].getWidth() * rect[taskNumber].getHeight();
                            rectText[taskNumber].setText(Double.toString(s));
                        }
                    });
                    Thread.sleep(10);
                }
                return null;
            }
        };
        return task;
    }
    
    private Task createLastTask(){
        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                while (!shutdown) {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            double sum = 0;
                            for (int i = 0; i < 4; i++) {
                                sum += rect[i].getWidth() * rect[i].getHeight();
                            }
                            sumText.setText("Общая площадь: " + sum + " пикселей");
                        }
                    });
                    Thread.sleep(10);
                }
                return null;
            }
        };
        return task;
    }
    
    private Rectangle createRectangle(double beg_x, double beg_y, double w, double h, Color color) {
        Rectangle r = new Rectangle(beg_x, beg_y, w, h);
        r.setFill(color);
        return r;
    }
    
    private Text createDisplay(Color color) {
        Text t = new Text();
        t.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t.setFill(color);
        return t;
    }
    
    private void createGraphNodes() {
        double dx = 20;
        for (int i = 0; i < 4; i++) {
            rect[i] = createRectangle(dx, 40, 40, (int) (Math.random()*(MAX_HEIGHT - 1) + 1), color);
            rectText[i] = new Text(dx, 20, "0");       
            rectText[i].setFont(Font.font("Arial", FontWeight.BOLD, 16));
            rectText[i].setFill(Color.RED);
            dx += 100;
            group.getChildren().addAll(rect[i], rectText[i]);
        }
        sumText = new Text(60, MAX_HEIGHT + 80, "0");       
        sumText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sumText.setFill(Color.RED);
        group.getChildren().add(sumText);
        root_pane.getChildren().addAll(group);
    }
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Лабораторная работа №1. студ. Дзыговского В.И.");
        stage.setResizable(false);
        createGraphNodes();
        createThreads();
        Scene scene = new Scene (root_pane, 400, MAX_HEIGHT + 100, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
               shutdown = true;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    } 
}
