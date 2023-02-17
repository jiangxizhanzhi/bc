package com.cyberpeace.bs.service;

import java.util.List;

import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.bo.meter.SimulatorTimer;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.common.model.ListRsp;

public interface UserSimulatorService {

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
	 * 批量停止行为仪表
	 * @param simulatorIds
	 */
	void batchStopSimulator(List<String> simulatorIds);

	/**
	 * 发送执行行为请求之后，更新simulator进度
	 * @param simulatorId 行为仪表id
	 * @param behaviorId 记录行为id
	 */
	void updateProcess(String simulatorId, String behaviorId);

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
	ListRsp<BehaviorMeterBo> pageBehaviorMeters(MeterPageReq pageReq);

	/**
	 * 获取仪表详情
	 * @param simulatorId
	 * @return
	 */
	SimulatorDetail getDetail(String simulatorId);

	/**
	 * 关闭场景时删除关联仪表
	 * @param sceneIdList 场景id
	 */
	void deleteBySceneId(List<String> sceneIdList);

	/**
	 * 获取仪表关联的全部场景
	 * @return
	 */
	List<SceneIdName> getSceneList();

	/**
	 * 获取仪表，
	 * @param resourceId
	 * @return
	 */
	UserSimulator getOrException(String resourceId);

	/**
	 * 获取行为仪表监控数据
	 * @param resourceId
	 * @return
	 */
	MonitorDataRes getMonitorData(String resourceId, Integer duration);

	/**
	 * 获取仪表关联的全部场景id
	 * @return
	 */
	List<String> getSceneIdList();

	/**
	 * 更新单个仪表包含的用户行为
	 * @param modifyBo
	 */
	void modifySimulator(SimulatorModifyBo modifyBo);

	/**
	 * 动态更新场景时，更新行为仪表
	 * @param updateBo
	 */
	void updateBatch(SimulatorAddBo updateBo);

	/**
	 * 展示现存的定时任务
	 * @return
	 */
	List<SimulatorTimer> showTimers();

	/**
	 * 获取实时总带宽
	 * @return
	 */
	Integer getBandwidthSum();

	/**
	 * 动态更新场景时, 删除特定节点的仪表
	 * @param nodeIds
	 */
	void deleteByNodeId(List<String> nodeIds);
}
