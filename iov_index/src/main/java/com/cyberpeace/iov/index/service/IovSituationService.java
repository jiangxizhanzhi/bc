package com.cyberpeace.iov.index.service;

import com.alibaba.fastjson.JSONObject;

public interface IovSituationService {

    void pushSituationWsMsg(JSONObject object);
}
