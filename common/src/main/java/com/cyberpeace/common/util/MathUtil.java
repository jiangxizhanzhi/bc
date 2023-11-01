package com.cyberpeace.common.util;

import java.util.Random;

public class MathUtil {

    private static final Random random = new Random();

    private MathUtil() {
    }

    public static int getRandomInt(int value) {
        return random.nextInt(value);
    }
}
