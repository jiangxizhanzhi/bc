package com.cyberpeace.bs.service.bo;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.simulator.BsTimer;
import com.cyberpeace.bs.service.bo.simulator.VariableBo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.TimerTask;

@Slf4j
public class BsTimerTask extends TimerTask {

    private final String deviceIp;

    private final String deviceId;

    private final Boolean isEntity;

    private final String behaviorId;

    private final Integer index;

    private final String simulatorId;

    private final BsTimer timer;

    private final String variable;


    public BsTimerTask(String deviceId, String ip, Boolean isEntity, String behaviorId, Integer index,
                       String simulatorId, String variable, BsTimer timer) {
        this.deviceId = deviceId;
        this.deviceIp = ip;
        this.isEntity = isEntity;
        this.behaviorId = behaviorId;
        this.index = index;
        this.simulatorId = simulatorId;
        this.variable = variable;
        this.timer = timer;
    }

    @Override
    public void run() {
        try {
            log.info("timer task [{}] (index:{}) of user simulator [{}] starts with variable:[{}]",
                    behaviorId, index, simulatorId, variable);
            if (!timer.ifActive()) {
                log.info("Timer is inactive, pass this task");
                return;
            }
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
            userSimulatorService.updateProcess(simulatorId, index);
        } catch (Exception e) {
            log.error("User simulator timer task failed: ", e);
        }
    }
}
