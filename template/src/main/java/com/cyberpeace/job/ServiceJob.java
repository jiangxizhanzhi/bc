package com.cyberpeace.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.integratation.service.RemoteManageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务工作
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
@Component
public class ServiceJob {

	@Autowired
	private RemoteManageService remoteManageService;

	@Scheduled(initialDelay = 500, fixedRate = Long.MAX_VALUE)
	public void reportStatus() {
		log.info("report status start");
		remoteManageService.reportStatus();
		log.info("report status end");
	}
}
