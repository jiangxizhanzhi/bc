package com.cyberpeace.bs.service.bo.demo;

import java.util.TimerTask;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.simulator.BsTimer;
import com.cyberpeace.common.util.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskDemo extends TimerTask {

    private final String ip;

    private final String behaviorId;

    private final Integer index;

    private final String simulatorId;

    private final BsTimer timer;

    private final String variable;

    private final LcService lcService;

	private TaskParam taskParam;

    public TaskDemo(String ip, String behaviorId, Integer index, String simulatorId,
			String variable, BsTimer timer, TaskParam taskParam) {
        this.ip = ip;
        this.behaviorId = behaviorId;
        this.index = index;
        this.simulatorId = simulatorId;
        this.variable = variable;
        this.lcService = SpringContextUtil.getBeanByClass(LcService.class);
        this.timer = timer;
		this.taskParam = taskParam;
    }

    @Override
    public void run() {
        try {
            log.info("timer task [{}] (index:{}) of user simulator [{}] starts with variable:[{}]",
                    behaviorId, index, simulatorId, variable);
			if (Boolean.FALSE.equals(timer.ifActive())) {
                log.info("Timer is inactive, pass this task");
                return;
            }
			ReleaseTaskReqDemo req = new ReleaseTaskReqDemo();
			req.setDeviceId(taskParam.getDeviceId());
			req.setEnvironmentId(taskParam.getEnvironmentId());
			req.setProjectId(taskParam.getProjectId());
            req.setFlowChart(behaviorId);
            // todo start a behavior
            lcService.startDemoBehavior(req);
        } catch (Exception e) {
            log.error("User simulator timer task failed: ", e);
        }
    }
}

