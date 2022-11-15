package com.cyberpeace.bs.service.bo;

import java.util.TimerTask;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.simulator.BsTimer;
import com.cyberpeace.common.util.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrafficTimerTask extends TimerTask {

	private final String ip;

	private final String behaviorId;

	private final Integer index;

	private final String simulatorId;

	private final BsTimer timer;

	private final String variable;

	private final LcService lcService;

	public TrafficTimerTask(String ip, String behaviorId, Integer index, String simulatorId,
			String variable, BsTimer timer) {
		this.ip = ip;
		this.behaviorId = behaviorId;
		this.index = index;
		this.simulatorId = simulatorId;
		this.variable = variable;
		this.lcService = SpringContextUtil.getBeanByClass(LcService.class);
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
			// todo start a behavior
			lcService.startBehavior(ip, behaviorId, variable);
			// UserSimulatorService userSimulatorService = SpringContextUtil
			// .getBeanByClass(UserSimulatorService.class);
			// userSimulatorService.updateProcess(simulatorId, index);
			// if (index == 0) {
			// Timer timer = userTrafficService.getTimerMap().get(trafficId);
			// if (timer != null) {
			// timer.cancel();
			// userTrafficService.getTimerMap().remove(trafficId);
			// log.info("user traffic {} is finished", trafficId);
			// }
			// }
		} catch (Exception e) {
			log.error("User simulator timer task failed: ", e);
		}
	}
}
