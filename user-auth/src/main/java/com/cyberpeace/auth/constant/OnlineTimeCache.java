package com.cyberpeace.auth.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineTimeCache {
    public static final Map<String, Date> loginTimeMap = new ConcurrentHashMap<>();

    public static  Date remove(String userId) {
        return loginTimeMap.remove(userId);
    }

    public static void setLoginTime(String userId, Date loginTime) {
        loginTimeMap.put(userId, loginTime);
    }
}
