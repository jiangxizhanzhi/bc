package com.cyberpeace.bs.service.impl;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.lc.StopTaskReq;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		UserSimulator simulator = userSimulatorService.getOrException(simulatorId);
		ReleaseTaskReq taskReq = new ReleaseTaskReq();
		taskReq.setIsUsingIp(!simulator.getIsEntity());
		if (taskReq.getIsUsingIp()) {
			taskReq.setDeviceId(BsConstants.DEFAULT_DEVICE_ID);
		} else {
			taskReq.setDeviceId(simulator.getDeviceId());
		}
		taskReq.setIp(simulator.getDeviceIp());
		taskReq.setFlowChart(behaviorId);
		req.getVariables().forEach(variableBo -> taskReq.addVariable(variableBo.getName(), variableBo.getValue()));
		lcService.startBehavior(taskReq);
    }

	@Override
	public void stopSingleBehavior(SingleBehaviorReq req) {
		String simulatorId = req.getSimulatorId();
		String behaviorId = req.getBehaviorId();
		UserSimulator simulator = userSimulatorService.getOrException(simulatorId);
		StopTaskReq stopTaskReq = new StopTaskReq();
		stopTaskReq.setChartId(new Long(behaviorId));
		stopTaskReq.setIsUsingIp(!simulator.getIsEntity());
		if (simulator.getIsEntity()) {
			stopTaskReq.setDeviceId(new Integer(simulator.getDeviceId()));
		} else {
			stopTaskReq.setDeviceId(new Integer(BsConstants.DEFAULT_DEVICE_ID));
		}
		stopTaskReq.setIp(simulator.getDeviceIp());
		lcService.stopBehavior(stopTaskReq);
	}
}