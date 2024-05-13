//==============================================================================
// Лабораторна работа №5
// class: Lab_5_Dzyhovskyi_V_I
// Copyright (c) 2021 Dzyhovskyi V. I.
//==============================================================================
package Lab_5_Dzyhovskyi_V_I;

/**
 *
 * @author Dzyhovskyi V. I.
 */
public interface CallBacks {
    public void updateServerTextArea(String s);
    public void updateServerStatusTextArea(String s);
    public void returnResult(int operation, double value);
    public void updateConnections(int value);
}
