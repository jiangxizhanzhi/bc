package com.cyberpeace.cr.crbase.service;

import java.util.List;

/**
 * WebSocket推送Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface WebSocketPushService {
     /**
      * 通知
      *
      * @param userList   用户列表
      * @param code       代号
      * @param businessId 业务id
      * @param url        网址
      * @throws
      * @see List<String>
      * @see Integer
      * @see String
      * @see String
      */
     void notice(List<String> userList,Integer code,String businessId,String url);
}
