//==============================================================================
// Лабораторна работа №5
// class: Lab_5_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_5_Dzyhovskyi_V_I;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public class ServerTask implements Runnable{
     static int numberOfConnections = 0;
    private Socket socket;
    private CallBacks callBacks;

    @Override
    public void run() {
        boolean isSusccess = false;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int operation = 0;
            double firstOperand, secondOperand;
            callBacks.updateServerStatusTextArea("Connected");
            numberOfConnections++;
            while(true) {
                Platform.runLater(() -> callBacks.updateConnections(ServerTask.numberOfConnections));
                System.out.println((operation = dataInputStream.readInt()) + ", " + (firstOperand = dataInputStream.readDouble()) +
                        ", " + (secondOperand = dataInputStream.readDouble()));
                double result = calculateResult(operation, firstOperand, secondOperand);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                pw.print(result +"\n");
                pw.flush();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private double calculateResult(int operation, double firstOperand, double secondOperand) {
        double result = 0;
        switch (operation){
            case 1:
                result = firstOperand - secondOperand;
                callBacks.updateServerTextArea("Операнды: " + firstOperand + ", " 
                        + secondOperand + "\nРазница: " + result);
                break;
            case 2:
                result = firstOperand / secondOperand;
                callBacks.updateServerTextArea("Операнды: " + firstOperand + ", " 
                        + secondOperand + "\nДеление: " + result);
                break;
            case 3:
                result = Math.cos(firstOperand);
                callBacks.updateServerTextArea("Операнды: " + firstOperand + ", " 
                        + secondOperand + "\nКосинус: " + result);
                break;
            case 4:
                result = 1 / Math.tan(firstOperand);
                callBacks.updateServerTextArea("Операнды: " + firstOperand + ", " 
                        + secondOperand + "\nКотангенс: " + result);
                break;
        }
        return result;
    }

    public ServerTask(Socket socket, CallBacks callBacks) {
        this.socket = socket;
        this.callBacks = callBacks;
    }
}
