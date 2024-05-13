/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_6_Dzyhovskyi_V_I;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public interface CallBacks  {
    public void updateRightText(String s);
    public void updateConnections(int value);
    public void pulseRectangle(int num);
    public double updateTotal();
    public double tickPixels(int num);
}
