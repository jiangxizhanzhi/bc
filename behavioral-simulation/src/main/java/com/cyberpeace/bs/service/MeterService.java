package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;

public interface MeterService {

    void startSingleBehavior(SingleBehaviorReq req);

    void stopSingleBehavior(SingleBehaviorReq req);
}
