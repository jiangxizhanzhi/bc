package com.cyberpeace.config.custom;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Log4J日志级别配置类
 * 通过读取系统属性来动态设置日志级别
 */
@Slf4j
@Component
public class Log4JLevelConfig {

    /**
     * 从系统属性中获取日志级别配置
     */
    String LOG_LEVEL = SystemPropUtil.getProp("LOG_LEVEL");

    /**
     * 在Spring容器初始化后执行，用于设置日志级别
     * 如果系统属性中的日志级别为空，则不执行任何操作
     * 否则，将设置根日志级别以及特定日志记录器的日志级别
     */
    @PostConstruct
    private void init() {
        // 检查日志级别是否为空，如果为空则不进行后续操作
        if (StringUtils.isBlank(LOG_LEVEL)) {
            return;
        }

        // 输出初始化日志级别的信息
        log.info("Init log level! level=[{}].....", LOG_LEVEL);
        // 将字符串形式的日志级别转换为Log4J的Level对象
        Level level = Level.toLevel(LOG_LEVEL);

        // 设置根日志记录器的日志级别
        Logger.getRootLogger().setLevel(level);
        // 设置特定日志记录器的日志级别
        Logger.getLogger("mq").setLevel(level);
        Logger.getLogger("mp").setLevel(level);
        Logger.getLogger("resourceImport").setLevel(level);
        Logger.getLogger("eval").setLevel(level);
        Logger.getLogger("audit").setLevel(level);

        // 输出初始化日志级别结束的信息
        log.info("Init log level end!.....");
    }

}

