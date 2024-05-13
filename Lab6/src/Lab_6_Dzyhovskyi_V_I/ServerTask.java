/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_6_Dzyhovskyi_V_I;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class ServerTask implements Runnable {

    public static boolean[] running = {false, false, false, false};
    static int numberOfConnections = 0;
    private Socket socket;
    private CallBacks callBacks;
    PrintWriter pw = null;

    @Override
    public void run() {
        boolean isSusccess = false;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int action = 0;
            double firstOperand, secondOperand;
            callBacks.updateRightText(">>> Connected");
            numberOfConnections++;
            pw = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                Platform.runLater(() -> callBacks.updateConnections(numberOfConnections));
                System.out.println((action = dataInputStream.readInt()));

                switch (action){
                    case Lab_6_Dzyhovskyi_V_I_Client.START_ONE:
                        running[0] = true;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.STOP_ONE:
                        running[0] = false;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.START_TWO:
                        running[1] = true;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.STOP_TWO:
                        running[1] = false;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.START_THREE:
                        running[2] = true;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.STOP_THREE:
                        running[2] = false;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.START_FOUR:
                        running[3] = true;
                        break;
                    case Lab_6_Dzyhovskyi_V_I_Client.STOP_FOUR:
                        running[3] = false;
                        break;
                }
                pw.flush();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public ServerTask(Socket socket, CallBacks callBacks) {
        this.socket = socket;
        this.callBacks = callBacks;
    }
}
