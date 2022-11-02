package com.cyberpeace.bs.service;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.cyberpeace.bs.service.bo.traffic.UserTrafficBo;

public interface UserTrafficService {

	/**
	 * 列出全部前台用户流量发生
	 * @return 前台用户流量发生
	 */
	List<UserTrafficBo> listTraffic();

	/**
	 * 批量删除前台用户流量发生
	 * @param resourceIds 用户流量发生id列表
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 创建前台用户流量发生
	 * @param userBehaviorId 用户行为模型id
	 */
	void create(String userBehaviorId);

	/**
	 * 启动流量发生
	 * @param resourceId 用户流量id
	 */
	void startTraffic(String resourceId);

	/**
	 * 停止流量发生
	 * @param resourceId 前台用户流量id 
	 */
	void stopTraffic(String resourceId);

	/**
	 * 获取定时器集合
	 * @return 定时器集合
	 */
	Map<String, Timer> getTimerMap();

	/**
	 * 发送行为流量之后，更新traffic进度
	 * @param trafficId 流量发生编号
	 * @param index 最近一次的行为编号
	 */
	void updateProcess(String trafficId, Integer index);
}
