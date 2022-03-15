package com.cyberpeace.job;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 通用定时任务
 */
@Slf4j
@Component
public class CommonJob {
	/**
	 * 每天一点执行 清除过期和过大日志信息
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void clearLog() {
		if (!SystemUtils.IS_OS_LINUX) {
			// only support linux
			return;
		}
		log.info("clearLog start");
		tomcatLog();
		bigLog();
		log.info("clearLog end");
	}

	public void bigLog() {
		log.info("clearLog bigLog");
		String command = "find %s -name 'catalina.out' -type f -size +300M -exec truncate -s 0 {} \\;";
		shellLog(command);
	}

	private void tomcatLog() {
		log.info("clearLog tomcatLog");
		String command = "find %s \\( -name 'gc*' -o -name 'catalina*' -o -name 'host-manager*' -o -name 'manager*' -o -name 'localhost*' \\) -type f -mtime +15 -exec rm -rf {} \\;";
		shellLog(command);
	}

	private void shellLog(String baseCommand) {
		String catalinaBasePath = SystemPropUtil.getProp("catalina.base");
		String logPath = catalinaBasePath + File.separator + "logs";
		String command = String.format(baseCommand, logPath);
		try {
			Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
		} catch (IOException e) {
			log.error("clearLog shell error {}", e.getMessage());
			log.error("clearLog shell error by command {}", command);
		}

	}
}
