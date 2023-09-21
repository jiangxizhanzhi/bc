package com.cyberpeace.common.util;

import java.util.UUID;

public class IDGeneratorUtil {

    private IDGeneratorUtil(){}

    public static String getID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
