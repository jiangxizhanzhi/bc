package com.cyberpeace.cr.crbase.service;

import java.util.List;

/**
 * 蹼插座推Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface WebSocketPushService {
     /**
      * 通知
      *
      * @param userList   用户列表
      * @param code       代号
      * @param businessId 业务id
      * @param url        网址
      */
     void notice(List<String> userList,Integer code,String businessId,String url);
}
