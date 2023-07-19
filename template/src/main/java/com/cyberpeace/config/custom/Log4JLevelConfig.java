package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

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
		Configurator.setLevel(LogManager.getRootLogger().getName(), level);
		Configurator.setLevel(LogManager.getLogger("mq").getName(), level);
		Configurator.setLevel(LogManager.getLogger("mp").getName(), level);
		Configurator.setLevel(LogManager.getLogger("resourceImport").getName(), level);
		Configurator.setLevel(LogManager.getLogger("eval").getName(), level);
		log.info("Init log level end!.....");

		;


	}

}
