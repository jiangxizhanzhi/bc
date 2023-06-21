package com.cyberpeace.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class LogConfigMessageListener implements ChannelAwareMessageListener {

    @Autowired
    private SystemConfigService systemConfigService;

    private static final Logger log = LoggerFactory.getLogger("mq");

    private static Map<Integer, LogConfigMessageListener> register = Maps.newHashMap();

    private static final String LOG_FILE_KEEP_DAYS = "sys_config_update";

    private static final String LOG_FILE_KEEP_DAYS_KEY = "log_file_max_history";

    public static Integer logFileKeepDays = 15;

    @PostConstruct
    public void init() {
        if (register.containsKey(declareMessageType())) {
            throw new Error("declareAuthMessageType=[" + declareMessageType() + "] repeat");
        }

        register.put(declareMessageType(), this);

        checkLogFileKeepDays();
    }

    public void checkLogFileKeepDays() {
        try {
            // 更新日志文件保留天数
            logFileKeepDays = Integer.valueOf(systemConfigService.getSystemConfig(LOG_FILE_KEEP_DAYS_KEY));
            log.info("Update log file keep days successfully, current value is {}", logFileKeepDays);

        } catch (Exception e) {
            // 更新配置失败，保持原有配置
            log.error("Update log file keep days failed, keep current value {}", logFileKeepDays);
        }
    }

    public Integer getLogFileKeepDays() {
        checkLogFileKeepDays();
        return logFileKeepDays;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String realMessage = new String(message.getBody(), WebConstant.ENCODING);

        // mq日志正常情况不输出，调试的时候再输出
        if (log.isDebugEnabled()) {
            log.debug("LogConfigMessageListener: {}", realMessage);
        }

        try {
            dispatch(realMessage);
        } catch (Exception e) {
            log.error("LogConfigMessageListener: dispatch error ", e);
        } finally {
            // 不管数据处理是否成功，都直接ack消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 每个子类需要重写该方法，保证返回值唯一
     *
     * @return
     */
    public Integer declareMessageType() {
        return -1;
    }

    public Boolean onLogConfigUpdate(Integer days) {

        Integer currentDays = getLogFileKeepDays();

        // 本系统只使用接口获取的配置，但当消息与接口值不一致时，打印日志进行记录
        if (!currentDays.equals(days)) {
            log.error("Log config error, current log_file_max_history is {}, but mq message is {}", currentDays, days);
            return true;
        }

        // 修改日志保留天数
        logFileKeepDays = days;
        return true;
    }

    private boolean dispatch(String message) {
        JSONObject object;

        try {
            object = JSON.parseObject(message);
        } catch (JSONException e) {
            log.error("can not JSON.parseObject message: {}", message);
            return true;
        }

        String type = object.getString("type");

        if (StringUtils.isBlank(type)) {
            log.error("message=[{}] cannot find type ", message);
            return true;
        }

        boolean result = true;
        for (Integer key : register.keySet()) {
            LogConfigMessageListener realListener = register.get(key);
            log.info("delete_class=[{}] , delete type =[{}] ", realListener.toString(), type);

            try {
                switch (type) {
                    case LOG_FILE_KEEP_DAYS:
                        String logFileMaxHistory = null;
                        if (LOG_FILE_KEEP_DAYS_KEY.equals(object.getJSONObject("data").getString("keyName"))) {
                            logFileMaxHistory = object.getJSONObject("data").getString("value");
                        }
                        if (StringUtils.isNotEmpty(logFileMaxHistory)) {
                            Integer days = Integer.valueOf(logFileMaxHistory);
                            realListener.onLogConfigUpdate(days);
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.error("message=[{}] handler error", message, e);
                result = false;
            }
        }
        return result;
    }

}
