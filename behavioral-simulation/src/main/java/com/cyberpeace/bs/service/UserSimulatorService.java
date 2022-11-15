package com.cyberpeace.bs.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.bs.service.bo.simulator.BsTimer;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorAddReq;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorBo;

public interface UserSimulatorService {

	/**
	 * 列出全部前台用户流量发生
	 * @return 前台用户流量发生
	 */
	List<UserSimulatorBo> listSimulator();

	/**
	 * 批量删除前台用户流量发生
	 * @param resourceIds 用户流量发生id列表
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 创建前台用户流量发生
	 * @param addReq 用户模拟创建请求体
	 */
	void create(UserSimulatorAddReq addReq);

	/**
	 * 启动流量发生
	 * @param resourceId 用户流量id
	 */
	void startSimulator(String resourceId);

	/**
	 * 停止流量发生
	 * @param resourceId 前台用户流量id 
	 */
	void stopSimulator(String resourceId);

	/**
	 * 获取定时器集合
	 * @return 定时器集合
	 */
	Map<String, BsTimer> getTimerMap();

	/**
	 * 发送行为流量之后，更新traffic进度
	 * @param trafficId 流量发生编号
	 * @param index 最近一次的行为编号
	 */
	void updateProcess(String trafficId, Integer index);
}
