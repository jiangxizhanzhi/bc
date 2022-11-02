package com.cyberpeace.bs.service.bo;

import java.util.Timer;
import java.util.TimerTask;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserTrafficService;
import com.cyberpeace.common.util.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrafficTimerTask extends TimerTask {

	private String ip;

	private String behaviorId;

	private Integer index;

	private String trafficId;

	private LcService lcService;

	public TrafficTimerTask(String ip, String behaviorId, Integer index, String trafficId) {
		this.ip = ip;
		this.behaviorId = behaviorId;
		this.index = index;
		this.trafficId = trafficId;
		this.lcService = SpringContextUtil.getBeanByClass(LcService.class);
	}

	@Override
	public void run() {
		try {
			log.info("timer task [{}] (index:{}) of user traffic [{}] starts", behaviorId, index,
					trafficId);
			// todo start a behavior
			lcService.startTraffic(ip, behaviorId);
			UserTrafficService userTrafficService = SpringContextUtil
					.getBeanByClass(UserTrafficService.class);
			userTrafficService.updateProcess(trafficId, index);
			if (index == 0) {
				Timer timer = userTrafficService.getTimerMap().get(trafficId);
				if (timer != null) {
					timer.cancel();
					userTrafficService.getTimerMap().remove(trafficId);
					log.info("user traffic {} is finished", trafficId);
				}
			}
		} catch (Exception e) {
			log.error("Traffic timer task failed: ", e);
		}
	}
}
