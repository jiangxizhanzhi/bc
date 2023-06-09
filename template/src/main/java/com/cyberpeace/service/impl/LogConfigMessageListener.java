package com.cyberpeace.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class LogConfigMessageListener implements ChannelAwareMessageListener {

    private static final Logger log = LoggerFactory.getLogger("mq");

    private static Map<Integer, LogConfigMessageListener> register = Maps.newHashMap();

    private final String LOG_FILE_KEEP_DAYS = "sys_config_update";

    public static Integer logFileKeepDays = 15;

    @PostConstruct
    public void init() {
        if (register.containsKey(declareMessageType())) {
            throw new Error("declareAuthMessageType=[" + declareMessageType() + "] repeat");
        }

        register.put(declareMessageType(), this);
    }

    public static Integer getLogFileKeepDays() {
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
                        String logFileMaxHistory = object.getJSONObject("data").getString("log_file_max_history");
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
