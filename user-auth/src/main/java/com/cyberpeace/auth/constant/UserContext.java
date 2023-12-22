package com.cyberpeace.auth.constant;

import com.cyberpeace.auth.domain.SysUser;

/**
 * @author :   SN
 * @date :   2023/12/22 11:54
 * @desc :
 **/
public class UserContext {

    private static final ThreadLocal<SysUser> userContext = new ThreadLocal<>();

    private UserContext(){}

    public static void setUser(SysUser user){
        userContext.set(user);
    }

    public static SysUser getUser(){
        return userContext.get();
    }

    public static void clearUser(){
        userContext.remove();
    }
}
