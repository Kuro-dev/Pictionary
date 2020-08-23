package org.kurodev.pictionary.overlay;

import javax.swing.*;
import java.io.Serializable;

public class SpinnerMinuteModel extends AbstractSpinnerModel implements Serializable {

    int counter = 80;

    public static String asTime(int i) {
        String min = "" + i / 60;
        String sec = "" + i % 60;
//                    if (min.equals("")) min = "00";
        if (min.length() == 1) min = "0" + min;
        if (sec.length() == 1) sec = "0" + sec;
        return min + ":" + sec;
    }

    public Object getValue() {
        return asTime(counter);
    }

    public void setValue(Object value) {
        fireStateChanged();
    }

    public Object getNextValue() {
        counter++;
        return getValue();
    }

    public Object getPreviousValue() {
        counter--;
        return getValue();
    }

//    public void addChangeListener(ChangeListener l) {
//
//    }
//
//    public void removeChangeListener(ChangeListener l) {
//
//    }
}
