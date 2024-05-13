/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_6_Dzyhovskyi_V_I;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_6_Dzyhovskyi_V_I_Server extends Application {
    private boolean[] flag_direction = {true, true, true, true};
    private Thread tr[] = {createTread(0), createTread(1), createTread(2), createTread(3)};
    private final double MAX_LENGHT = 110;
    private final double width = 30;
    private final Color color = Color.GREY;

    private Rectangle createRectangle(double startX, double startY, double height,
                                      double width, Color color){
        Rectangle r = new Rectangle(startX, startY, width, height);
        r.setFill(color);
        return r;
    }

    Rectangle[] rectangle = {createRectangle(10, 50, 0, width, color),
        createRectangle(110, 50, 0, width, color), 
        createRectangle(210, 50, 0, width, color), 
        createRectangle(310, 50, 0, width, color)};
    Label[] label = {new Label("0px"),new Label("0px"),new Label("0px"),new Label("0px")};

    Label totalS = new Label("0px");

    private Pane root_pane = new Pane();

    private Label portLabel = new Label("Port 8000");
    private Label connectionsLabel = new Label("Connections: 0");

    private TextArea serverStatusTextArea = new TextArea();

    private Task<Void> server_task;
    private final int PORT = 8000;

    private Socket socket;

    private CallBacks impl = new CallBacks() {

        public double tickPixels(int num){
            double result = 0;
            result = rectangle[num].getHeight()* rectangle[num].getWidth();
            label[num].setText(result + "px");
            return result;
        }

        @Override
        public void pulseRectangle(int num){
            
            if(rectangle[num].getHeight() <= 1){
                flag_direction[num] = true;
            }
            if(rectangle[num].getHeight() >= MAX_LENGHT) {
                flag_direction[num] = false;
            }
            if(flag_direction[num] == true){
                rectangle[num].setHeight(rectangle[num].getHeight() + 1);
            }
            if(flag_direction[num] == false){
                rectangle[num].setHeight(rectangle[num].getHeight() - 1);
            }
        }

        @Override
        public double updateTotal() {
            double value = rectangle[0].getHeight() * rectangle[0].getWidth() + rectangle[1].getHeight() *
                    rectangle[1].getWidth() + rectangle[2].getHeight() * rectangle[2].getWidth() +
                    rectangle[3].getHeight() * rectangle[3].getWidth();
            totalS.setText("Total: " + value + "px");
            return value;
        }

        @Override
        public void updateConnections(int value) {
            connectionsLabel.setText("Connections: "+value);
        }


        @Override
        public void updateRightText(String s) {
            serverStatusTextArea.appendText("\n"+ s);
        }
    };
    
    public void start(Stage primaryStage) throws Exception{
        setupScene();
        primaryStage.setTitle("Лабораторная работа №6. Студент Дзиговський В.І.");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();
        setupServer();
    }

    private void setupScene() {

        portLabel.setLayoutX(375);
        portLabel.setLayoutY(10);

        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(20);
        gridPane.setLayoutY(20);
        gridPane.setHgap(55);
        gridPane.setVgap(20);
        for(int i = 0; i < 4; i++){
            label[i].setLayoutY(20);
            label[i].setLayoutX(20 + i*75);
            rectangle[i].setX(20 + i*75);
            rectangle[i].setY(40);
        }

        totalS.setLayoutX(100);
        totalS.setLayoutY(200);

        connectionsLabel.setLayoutX(475);
        connectionsLabel.setLayoutY(10);

        serverStatusTextArea.setPrefSize(200, 150);
        serverStatusTextArea.setWrapText(true);
        serverStatusTextArea.setLayoutY(40);
        serverStatusTextArea.setLayoutX(350);

        root_pane.getChildren().addAll(serverStatusTextArea,
                connectionsLabel,portLabel, gridPane,totalS, rectangle[0],rectangle[1],
                rectangle[2],rectangle[3], label[0], label[1], label[2], label[3]);
    }

    private void setupServer() {
            server_task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        ServerSocket serverSocket = new ServerSocket(PORT);

                        while (true) {
                            socket = serverSocket.accept();
                            new Thread(new ServerTask(socket, impl)).start();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        server_task.cancel();
                    }
                    return null;
                }
            };
            new Thread(server_task).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    private Thread createTread(int num) {
    Thread thread = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(ServerTask.running[num]){
                            impl.pulseRectangle(num);
                            double res = impl.tickPixels(num);
                            double total = impl.updateTotal();
                            }
                        }
                    });
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            }
        };
    thread.start();
    return thread;
    }
}
