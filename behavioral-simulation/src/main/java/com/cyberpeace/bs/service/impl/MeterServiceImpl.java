package com.cyberpeace.bs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.lc.StopTaskReq;

import lombok.extern.slf4j.Slf4j;

/**
 * 仪表测试Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
@Service
@Slf4j
public class MeterServiceImpl implements MeterService {

	@Autowired
	LcService lcService;

	@Autowired
	UserSimulatorService userSimulatorService;

	@Override
	public void startSingleBehavior(SingleBehaviorReq req) {

		String simulatorId = req.getSimulatorId();
		String behaviorId = req.getBehaviorId();
		// 查询仪表
		UserSimulator simulator = userSimulatorService.getOrException(simulatorId);
		// 组装任务下发请求体
		ReleaseTaskReq taskReq = new ReleaseTaskReq();
		// 区分实体设备和虚拟设备
		taskReq.setIsUsingIp(!simulator.getIsEntity());
		if (taskReq.getIsUsingIp()) {
			taskReq.setDeviceId(BsConstants.DEFAULT_DEVICE_ID);
		} else {
			taskReq.setDeviceId(simulator.getDeviceId());
		}
		taskReq.setIp(simulator.getDeviceIp());
		taskReq.setFlowChart(behaviorId);
		// 设置行为变量
		req.getVariables().forEach(
				variableBo -> taskReq.addVariable(variableBo.getName(), variableBo.getValue()));
		// 下发行为任务
		lcService.startBehavior(taskReq);
	}

	@Override
	public void stopSingleBehavior(SingleBehaviorReq req) {
		String simulatorId = req.getSimulatorId();
		String behaviorId = req.getBehaviorId();
		// 查询仪表
		UserSimulator simulator = userSimulatorService.getOrException(simulatorId);
		// 设置请求体
		StopTaskReq stopTaskReq = new StopTaskReq();
		stopTaskReq.setChartId(new Long(behaviorId));
		// 区分实体设备、虚拟设备
		stopTaskReq.setIsUsingIp(!simulator.getIsEntity());
		if (simulator.getIsEntity()) {
			stopTaskReq.setDeviceId(new Integer(simulator.getDeviceId()));
		} else {
			stopTaskReq.setDeviceId(new Integer(BsConstants.DEFAULT_DEVICE_ID));
		}
		stopTaskReq.setIp(simulator.getDeviceIp());
		// 发送停止任务请求
		lcService.stopBehavior(stopTaskReq);
	}
}