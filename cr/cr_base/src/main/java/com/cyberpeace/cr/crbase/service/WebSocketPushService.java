package com.cyberpeace.cr.crbase.service;

import java.util.List;

public interface WebSocketPushService {
     void notice(List<String> userList,Integer code,String businessId,String url);
}
