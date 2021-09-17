package com.cyberpeace.integratation.service;

/**
 * 集成中台用户服务
 */
public interface UserCenterService {

    Boolean validateToken(String jwtToken);
}
