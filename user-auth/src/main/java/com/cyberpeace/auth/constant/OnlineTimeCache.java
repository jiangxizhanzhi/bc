package com.cyberpeace.auth.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OnlineTimeCache {
    private static final Map<String, Date> loginTimeMap = new HashMap<>();

    public static Date remove(String userId) {
        return loginTimeMap.remove(userId);
    }

    public static void setLoginTime(String userId, Date loginTime) {
        loginTimeMap.put(userId, loginTime);
    }
}
