package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.lc.LcActualDevice;
import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.lc.StopTaskReq;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.model.ListRsp;

import java.util.Collection;
import java.util.List;

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
	 */
	void startBehavior(ReleaseTaskReq req);

    /**
     * 停止单个行为流量
     */
	void stopBehavior(StopTaskReq req);

	/**
	 * 根据id获取用户行为列表
	 * @param behaviorIds id集合
	 * @return 用户行为列表
	 */
	List<LcBehaviorRspBo> getLcBehaviorListByIds(Collection<String> behaviorIds,
			boolean withVariable);

	List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds);

	VariableListBo getVariableList(String behaviorId);

	/**
	 * 编辑行为变量
	 * @param req
	 */
	void updateVariable(VariableModifyReq req);

	String getDeviceId();

	/**
	 * 获取实体设备
	 * @param types
	 * @return
	 */
	List<LcActualDevice> getActualDevices(List<Integer> types);
}
