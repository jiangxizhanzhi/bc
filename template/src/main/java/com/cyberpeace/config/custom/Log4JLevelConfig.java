package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * log4j level  配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class Log4JLevelConfig {

	/**
	 * 日志层级
	 *
	 * @see String
	 */
	String LOG_LEVEL = SystemPropUtil.getProp("LOG_LEVEL");
	org.slf4j.Logger logResourceImport = LoggerFactory.getLogger("resourceImport");

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	private void init() {
		logResourceImport.info("start resourceImport log");
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
