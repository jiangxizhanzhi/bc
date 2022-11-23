package com.cyberpeace.bs.service;

import java.util.Collection;
import java.util.List;

import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.demo.ReleaseTaskReqDemo;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.model.Behavior;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.model.ListRsp;

/**
 * 龙测接口
 */
public interface LcService {

    /**
	 * 获取行为列表
	 * @return 行为列表
	 * @param pageReq
	 */
	ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq);

    /**
	 * 开启单个行为流量
	 * @param ip 靶机ip
	 * @param behaviorId 行为id
	 * @param variable 行为变量
	 */
	void startBehavior(ReleaseTaskReq req);

    /**
     * 停止单个行为流量
     * @param targetIp 靶标ip
     * @param behaviorId 行为id
     */
	void stopBehavior(String targetIp, String behaviorId);

    /**
     * 获取靶标列表
     * @param search 名称模糊搜索
     * @return 靶标列表
     */
	List<TargetBo> getDeviceList(String search);

	/**
	 * 根据id获取用户行为列表
	 * @param behaviorIds id集合
	 * @return 用户行为列表
	 */
	List<Behavior> getLcBehaviorListByIds(Collection<String> behaviorIds, Boolean withVariable);

	VariableListBo getVariableList(String behaviorId);

	void updateVariable(VariableModifyReq req);

	void startDemoBehavior(ReleaseTaskReqDemo req);

	String getDeviceId();

}
