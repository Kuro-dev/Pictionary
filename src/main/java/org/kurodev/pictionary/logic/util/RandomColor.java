package org.kurodev.pictionary.logic.util;

public class RandomColor {

    public static String getHex() {

        return getRandomHEX(200, 25) + getRandomHEX(100, 200) + getRandomHEX(100, 200);

    }

    private static String getRandomHEX(int low, int high) {
        return Integer.toHexString((int) (low + (Math.random() * (high - low))));
    }

}
