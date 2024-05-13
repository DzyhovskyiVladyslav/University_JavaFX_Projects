//==============================================================================
// Лабораторна работа №5
// class: Lab_5_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_5_Dzyhovskyi_V_I;

import java.io.BufferedReader;
import java.io.DataInputStream;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public class Lab_5_Dzyhovskyi_V_I_Client extends Application {
    
    private Label clientLabel = new Label("Client");
    private Label portLabel = new Label("Порт");
    private Label hostLabel = new Label("Хост");
    private Label firstOperandLabel = new Label("Операнд 1");
    private Label secondOperandLabel = new Label("Операнд 2");
    private TextArea statusArea = new TextArea();
    private TextField portArea = new TextField("8000");
    private TextField hostArea = new TextField("127.0.0.1");
    private TextField firstArea = new TextField("10");
    private TextField secondArea = new TextField("20");
    private Button connectButton = new Button("Connect");
    private Button disconnectButton = new Button("Disconnect");
    private Button submitButton = new Button("Решить");
    private RadioButton subRb = new RadioButton("Sub");
    private RadioButton divRb = new RadioButton("Div");
    private RadioButton cosRb = new RadioButton("Cos");
    private RadioButton ctanRb = new RadioButton("Ctan");
    private int action_flag = 1;
    private int PORT = 8000;
    private String HOST = "127.0.0.1";
    private Socket socket;
    private Pane root_pane= new Pane();

    @Override
    public void start(Stage stage) throws Exception{
        setupScene();
        setupAction();
        stage.setTitle("Лабораторная работа №5 Дзыговский В.И.");
        stage.setResizable(false);
        Scene scene = new Scene(root_pane, 500 ,220, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    private void setupAction() {
        subRb.setOnAction(__ -> {
            action_flag = 1;
        });
        divRb.setOnAction(__ -> {
            action_flag = 2;
        });
        cosRb.setOnAction(__ -> {
            action_flag = 3;
        });
        ctanRb.setOnAction(__ -> {
            action_flag = 4;
        });
        connectButton.setOnAction(__ -> {
            try{
                socket = new Socket(HOST, PORT);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                Platform.runLater(() -> statusArea.appendText("Connected: \nPort: " 
                    + PORT + "\nHost: " + HOST + "\n"));
            }catch (Exception ex){
                Platform.runLater(() -> statusArea.appendText("Сonnection failed: \nPort: " 
                    + PORT + "\nHost: " + HOST + "\n"));
                ex.printStackTrace();
            }
        });
        disconnectButton.setOnAction(__ -> {
            try{
            if(socket != null)
                socket.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
        submitButton.setOnAction(__ -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    while (socket.isConnected()) {
                        if (reader.ready()) {
                            switch (action_flag){
                                case 1:
                                    statusArea.appendText("Разница: " + reader.readLine() + "\n");
                                    break;
                                case 2:
                                    statusArea.appendText("Деление: " + reader.readLine() + "\n");
                                    break;
                                case 3:
                                    statusArea.appendText("Косинус: " + reader.readLine() + "\n");
                                    break;
                                case 4:
                                    statusArea.appendText("Котангенс: " + reader.readLine() + "\n");
                                    break;
                            }
                        }
                    }
                        }catch (IOException ex){
                            System.out.println("IOException");
                            ex.printStackTrace();
                        }
                    }

            }).start();
            try {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                outputStream.writeInt(action_flag);

                outputStream.writeDouble(Double.parseDouble(firstArea.getText()));
                outputStream.writeDouble(Double.parseDouble(secondArea.getText()));
                outputStream.flush();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });
    }

    private void setupScene() {
        clientLabel.setLayoutY(5);
        clientLabel.setLayoutX(10);
        clientLabel.setWrapText(true);
        statusArea.setLayoutX(10);
        statusArea.setLayoutY(25);
        statusArea.setPrefSize(175,140);
        connectButton.setLayoutX(400);
        connectButton.setLayoutY(10);
        connectButton.setWrapText(true);
        disconnectButton.setLayoutY(50);
        disconnectButton.setLayoutX(400);
        disconnectButton.setWrapText(true);
        submitButton.setLayoutY(170);
        submitButton.setLayoutX(400);
        submitButton.setWrapText(true);
        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(10);
        portArea.setMaxWidth(75);
        portArea.setMaxHeight(15);
        hostArea.setMaxWidth(75);
        hostArea.setMaxHeight(15);
        firstArea.setMaxWidth(75);
        firstArea.setMaxHeight(15);
        secondArea.setMaxWidth(75);
        secondArea.setMaxHeight(15);
        gp.add(portLabel,2,0);
        gp.add(portArea,3,0);
        gp.add(hostLabel,2,1);
        gp.add(hostArea,3,1);
        gp.add(firstOperandLabel,2,2);
        gp.add(firstArea,3,2);
        gp.add(secondOperandLabel,2,3);
        gp.add(secondArea,3,3);
        gp.setPadding(new Insets(10,0,0,50));
        ToggleGroup tg = new ToggleGroup();
        subRb.setToggleGroup(tg);
        subRb.setSelected(true);
        divRb.setToggleGroup(tg);
        cosRb.setToggleGroup(tg);
        ctanRb.setToggleGroup(tg);
        gp.add(subRb,0,4);
        gp.add(divRb,1,4);
        gp.add(cosRb,2,4);
        gp.add(ctanRb,3,4);
        root_pane.getChildren().addAll(clientLabel, statusArea, connectButton,
                disconnectButton, submitButton, gp);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
