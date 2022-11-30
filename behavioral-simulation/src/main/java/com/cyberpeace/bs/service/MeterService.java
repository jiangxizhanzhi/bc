package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;

import java.util.List;

public interface MeterService {
    /**
     * 获取行为设备列表
     * @param type 设备类型
     * @param search 名称搜索
     * @return 设备列表
     */
	List<TargetBo> getMeters(Integer type, String search);

    void startSingleBehavior(SingleBehaviorReq req);

    void stopSingleBehavior(SingleBehaviorReq req);
}
