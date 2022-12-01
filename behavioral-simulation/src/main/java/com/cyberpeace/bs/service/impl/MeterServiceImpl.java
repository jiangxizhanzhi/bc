package com.cyberpeace.bs.service.impl;

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
		taskReq.setDeviceId(simulator.getDeviceId());
		taskReq.setDeviceIp(simulator.getDeviceIp());
		taskReq.setIsUsingIp(!simulator.getIsEntity());
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
		stopTaskReq.setDeviceId(new Integer(simulator.getDeviceId()));
		stopTaskReq.setIp(simulator.getDeviceIp());
		lcService.stopBehavior(stopTaskReq);
	}
}

enum TargetType implements GenericEnum {

	ALL(0, "全部类型"), DEFAULT(1, "默认"), IXIA(2, "ixia仪表"), LONGCE(3, "龙测仪表");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	TargetType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
