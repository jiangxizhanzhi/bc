package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

import java.util.List;
import java.util.Map;

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
	 * 启动用户模拟
	 * @param resourceId 用户模拟id
	 */
	void startSimulator(String resourceId);

	/**
	 * 批量启动仪表
	 * @param simulatorIds
	 */
	void batchStartSimulator(List<String> simulatorIds);

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

	/**
	 * 配管批量创建仪表
	 * @param addBo 配管发来的请求体
	 */
	void batchCreate(SimulatorAddBo addBo);

	/**
	 * 统计当前仪表数量
	 * @return
	 */
	Integer countSimulators();

	/**
	 * 统计当前活跃行为仪表
	 * @return
	 */
	Integer countActiveSimulators();

	/**
	 * 分页列出行为模拟仪表
	 * @return
	 * @param pageReq
	 */
	ListRsp<BehaviorMeterBo> pageBehaviorMeters(PageReqBase pageReq);

	/**
	 * 获取仪表详情
	 * @param simulatorId
	 * @return
	 */
	SimulatorDetail getDetail(String simulatorId);
}
