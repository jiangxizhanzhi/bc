package com.cyberpeace.bs.service;

import java.util.List;

import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.Behavior;

/**
 * 龙测接口
 */
public interface LcService {

    /**
     * 获取行为列表
     * @return 行为列表
     */
	List<Behavior> getLcBehaviorList();

    /**
     * 开启单个行为流量
     * @param ip 靶机ip
     * @param behaviorId 行为id
     */
    void startTraffic(String ip, String behaviorId);

    /**
     * 停止单个行为流量
     * @param targetIp 靶标ip
     * @param behaviorId 行为id
     */
    void stopTraffic(String targetIp, String behaviorId);

    /**
     * 获取靶标列表
     * @param search 名称模糊搜索
     * @return 靶标列表
     */
    List<TargetBo> getTargetList(String search);


}
