package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Log4JLevelConfig {

	String LOG_LEVEL = SystemPropUtil.getProp("LOG_LEVEL");

	@PostConstruct
	private void init() {
		if (StringUtils.isBlank(LOG_LEVEL)) {
			return;
		}

		log.info("Init log level! level=[{}].....", LOG_LEVEL);
		Level level = Level.toLevel(LOG_LEVEL);
		Logger.getRootLogger().setLevel(level);
		Logger.getLogger("mq").setLevel(level);
		Logger.getLogger("mp").setLevel(level);
		Logger.getLogger("resourceImport").setLevel(level);
		Logger.getLogger("eval").setLevel(level);
		Logger.getLogger("audit").setLevel(level);
		log.info("Init log level end!.....");
	}

}
