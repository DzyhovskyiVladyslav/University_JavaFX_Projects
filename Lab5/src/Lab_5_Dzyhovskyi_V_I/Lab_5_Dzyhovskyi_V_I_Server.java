//==============================================================================
// Лабораторна работа №5
// class: Lab_5_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_5_Dzyhovskyi_V_I;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public class Lab_5_Dzyhovskyi_V_I_Server extends Application {
    
    private Pane root_pane = new Pane();
    private Label serverLabel = new Label("Server");
    private Label portLabel = new Label("Port 8000");
    private Label connectionsLabel = new Label("Connections: 0");
    private TextArea serverTextArea = new TextArea();
    private TextArea serverStatusTextArea = new TextArea();
    private Task<Void> server_task;
    private final int PORT = 8000;
    private Socket socket;

    private CallBacks impl = new CallBacks() {
        @Override
        public void returnResult(int operation, double value) {

        }

        @Override
        public void updateConnections(int value) {
            connectionsLabel.setText("Connections: " + value);
        }

        @Override
        public void updateServerTextArea(String s) {
            serverTextArea.appendText(s + "\n");
        }

        @Override
        public void updateServerStatusTextArea(String s) {
            serverStatusTextArea.appendText(s + "\n");
        }
    };
    
    public void start(Stage primaryStage) throws Exception{
        setupScene();
        primaryStage.setTitle("Лабораторная работа №5 Дзыговский В.И.");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_pane, 450, 200, Color.TRANSPARENT));
        primaryStage.show();
        setupServer();
    }

    private void setupScene() {
        serverLabel.setLayoutX(25);
        serverLabel.setLayoutY(10);

        portLabel.setLayoutX(150);
        portLabel.setLayoutY(10);

        connectionsLabel.setLayoutX(275);
        connectionsLabel.setLayoutY(10);

        serverTextArea.setPrefSize(200,150);
        serverTextArea.setWrapText(true);
        serverTextArea.setLayoutY(30);
        serverTextArea.setLayoutX(25);

        serverStatusTextArea.setPrefSize(150, 150);
        serverStatusTextArea.setWrapText(true);
        serverStatusTextArea.setLayoutY(30);
        serverStatusTextArea.setLayoutX(275);

        root_pane.getChildren().addAll(serverTextArea, serverStatusTextArea,
                connectionsLabel,portLabel,serverLabel);
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
}
