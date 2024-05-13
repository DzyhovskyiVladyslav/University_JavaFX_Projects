/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_6_Dzyhovskyi_V_I;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Lab_6_Dzyhovskyi_V_I_Client extends Application {
    
    public static final int START_ONE = 0;
    public static final int STOP_ONE = 1;
    public static final int START_TWO = 2;
    public static final int STOP_TWO = 3;
    public static final int START_THREE = 4;
    public static final int STOP_THREE = 5;
    public static final int START_FOUR = 6;
    public static final int STOP_FOUR = 7;

    private Label statusLabel = new Label("Status");
    private Label portLabel = new Label("Port");
    private Label hostLabel = new Label("Host");
    private Label threadOneLabel = new Label("Прям. 1");
    private Label threadTwoLabel = new Label("Прям. 2");
    private Label threadThreeLabel = new Label("Прям. 3");
    private Label threadFourLabel = new Label("Прям. 4");
    private Label recSLabel = new Label("Площадь прямокутника: ");
    private Label allSRecLabel = new Label("Уся площадь: 0");
    private Label firstSRecLabel = new Label("0");
    private Label secondSRecLabel = new Label("0");
    private Label thirdSRecLabel = new Label("0");
    private Label fourthSRecLabel = new Label("0");

    private TextArea statusArea = new TextArea();
    private TextArea portArea = new TextArea("8000");
    private TextArea hostArea = new TextArea("127.0.0.1");

    private Button connectButton = new Button("З'єднатись");
    private Button disconnectButton = new Button("Від'єднатись");

    private RadioButton startThreadOne = new RadioButton("Start");
    private RadioButton startThreadTwo = new RadioButton("Start");
    private RadioButton startThreadThree = new RadioButton("Start");
    private RadioButton startThreadFour = new RadioButton("Start");

    private RadioButton stopThreadOne = new RadioButton("Stop");
    private RadioButton stopThreadTwo = new RadioButton("Stop");
    private RadioButton stopThreadThree = new RadioButton("Stop");
    private RadioButton stopThreadFour = new RadioButton("Stop");

    private Integer PORT;
    private String HOST;

    private Socket socket;

    private Pane root_pane = new Pane();


    public void start(Stage stage) throws Exception {
        setupScene();
        setupAction();
        stage.setTitle("Лабораторная работа №6. Студент Дзиговський В.І.");
        stage.setResizable(false);
        Scene scene = new Scene(root_pane, 520, 300, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    private void setupAction() {

        connectButton.setOnAction(__ -> {
            try {
                HOST = hostArea.getText();
                PORT = Integer.parseInt(portArea.getText());
                socket = new Socket(HOST, PORT);
                Platform.runLater(() -> statusArea.appendText("\n>>> Connected: " +
                        "\nPort: " + PORT
                        + "\nHost: " + HOST + "\n"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));
                            while (socket.isConnected()) {
                                if (reader.ready()) {
                                    String[] split = reader.readLine().split(",");
                                    switch (split[0]) {
                                        case "1":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    firstSRecLabel.setText(split[1]);
                                                    allSRecLabel.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "2":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    secondSRecLabel.setText(split[1]);
                                                    allSRecLabel.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "3":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    thirdSRecLabel.setText(split[1]);
                                                    allSRecLabel.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "4":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    fourthSRecLabel.setText(split[1]);
                                                    allSRecLabel.setText(split[2]);
                                                }
                                            });
                                            break;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            } catch (Exception ex) {
                Platform.runLater(() -> statusArea.appendText("\n== connection failed: "
                        + "\nPort: " + PORT
                        + "\nHost: " + HOST));
                ex.printStackTrace();
            }
        });
        disconnectButton.setOnAction(__ -> {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadOne.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(START_ONE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadTwo.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(START_TWO);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadThree.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(START_THREE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadFour.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(START_FOUR);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stopThreadOne.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(STOP_ONE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stopThreadTwo.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(STOP_TWO);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stopThreadThree.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(STOP_THREE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stopThreadFour.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(STOP_FOUR);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setupScene() {
        statusLabel.setLayoutY(5);
        statusLabel.setLayoutX(10);
        statusLabel.setWrapText(true);

        statusArea.setLayoutX(10);
        statusArea.setLayoutY(25);
        statusArea.setPrefSize(175, 150);
        statusArea.setWrapText(true);

        connectButton.setLayoutX(400);
        connectButton.setLayoutY(20);
        connectButton.setWrapText(true);
        
        disconnectButton.setLayoutX(400);
        disconnectButton.setLayoutY(80);
        disconnectButton.setWrapText(true);

        allSRecLabel.setLayoutX(10);
        allSRecLabel.setLayoutY(200);
        allSRecLabel.setWrapText(true);

        recSLabel.setLayoutX(10);
        recSLabel.setLayoutY(240);
        recSLabel.setWrapText(true);


        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(10);

        portArea.setWrapText(true);
        portArea.setMaxWidth(150);
        portArea.setMaxHeight(15);
        hostArea.setWrapText(true);
        hostArea.setMaxWidth(150);
        hostArea.setMaxHeight(15);

        gp.add(portLabel, 2, 0);
        gp.add(portArea, 3, 0);
        gp.add(hostLabel, 2, 1);
        gp.add(hostArea, 3, 1);

        gp.setPadding(new Insets(10, 0, 0, 150));

        ToggleGroup tg1 = new ToggleGroup();
        ToggleGroup tg2 = new ToggleGroup();
        ToggleGroup tg3 = new ToggleGroup();
        ToggleGroup tg4 = new ToggleGroup();
        startThreadOne.setToggleGroup(tg1);
        stopThreadOne.setToggleGroup(tg1);
        startThreadTwo.setToggleGroup(tg2);
        stopThreadTwo.setToggleGroup(tg2);
        startThreadThree.setToggleGroup(tg3);
        stopThreadThree.setToggleGroup(tg3);
        startThreadFour.setToggleGroup(tg4);
        stopThreadFour.setToggleGroup(tg4);

        GridPane gp2 = new GridPane();
        gp2.setHgap(20);
        gp2.setVgap(10);

        gp2.add(threadOneLabel, 0, 0);
        gp2.add(threadTwoLabel, 1, 0);
        gp2.add(threadThreeLabel, 2, 0);
        gp2.add(threadFourLabel, 3, 0);

        gp2.add(startThreadOne, 0, 1);
        gp2.add(stopThreadOne, 0, 2);
        gp2.add(startThreadTwo, 1, 1);
        gp2.add(stopThreadTwo, 1, 2);
        gp2.add(startThreadThree, 2, 1);
        gp2.add(stopThreadThree, 2, 2);
        gp2.add(startThreadFour, 3, 1);
        gp2.add(stopThreadFour, 3, 2);

        gp2.add(firstSRecLabel, 0, 3);
        gp2.add(secondSRecLabel, 1, 3);
        gp2.add(thirdSRecLabel, 2, 3);
        gp2.add(fourthSRecLabel, 3, 3);

        gp2.setPadding(new Insets(150, 0, 0, 200));


        root_pane.getChildren().addAll(statusLabel, gp2, connectButton,
                disconnectButton, gp, recSLabel, allSRecLabel, statusArea);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
