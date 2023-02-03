package com.cyberpeace.bs.service.bo;

import java.util.List;
import java.util.TimerTask;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.simulator.VariableBo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BsTimerTask extends TimerTask {

    private final String deviceIp;

    private final String deviceId;

    private final Boolean isEntity;

    private final String behaviorId;

    private final String simulatorId;

    private final String variable;


	public BsTimerTask(UserSimulator simulator, String behaviorId, String variable) {
		this.deviceId = simulator.getDeviceId();
		this.deviceIp = simulator.getDeviceIp();
		this.isEntity = simulator.getIsEntity();
        this.behaviorId = behaviorId;
		this.simulatorId = simulator.getResourceId();
        this.variable = variable;
    }

    @Override
    public void run() {
        try {
			log.info("timer task [{}] of user simulator [{}] starts with variable:[{}]", behaviorId,
					simulatorId, variable);

            ReleaseTaskReq req = new ReleaseTaskReq();
            req.setIsUsingIp(!isEntity);
            if(isEntity){
                req.setDeviceId(deviceId);
            } else {
                req.setDeviceId(BsConstants.DEFAULT_DEVICE_ID);
            }
            req.setIp(deviceIp);
            req.setFlowChart(behaviorId);
            List<VariableBo> variableBos = JsonUtil.toJsonObjectArray(variable, VariableBo.class);
            variableBos.forEach(variableBo -> req.addVariable(variableBo.getName(), variableBo.getValue()));
            LcService lcService = SpringContextUtil.getBeanByClass(LcService.class);
            lcService.startBehavior(req);

            UserSimulatorService userSimulatorService = SpringContextUtil
                    .getBeanByClass(UserSimulatorService.class);
			userSimulatorService.updateProcess(simulatorId, behaviorId);
        } catch (Exception e) {
            log.error("User simulator timer task failed: ", e);
        }
    }
}
