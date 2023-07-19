package com.cyberpeace.gateway.service.bo;

import cn.hutool.json.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.situation.model.outer.Command;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public abstract class CommandExecutor {


    /**
     * 默认错误消息
     */
    final String defaultErrorMessage;

    /**
     * url
     */
    final String url;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 消息
     */
    private String message;

    private String configTaskId;

    protected CommandExecutor(String configTaskId,  String url, String message) {
        this.configTaskId = configTaskId;
        this.url = url;
        this.defaultErrorMessage = message;
    }

    /**
     * 具体执行函数
     */
    public Command execute(JSONObject jsonObject) {
        // 默认成功
        state = 1;
        message = "";
        try {
            return doBusiness();
        } catch (Exception e) {
            // 错误代码2
            state = 2;
            // 输出默认错误信息
            message = defaultErrorMessage;
            // 捕获businessException，获取错误信息并覆盖
            if (e instanceof BusinessException) {
                message = ((BusinessException) e).getErrorMessage();
            }
            // 抛出错误
            throw e;
        } finally {
            try {
                // 不论执行是否成功，执行回调
                callback(jsonObject);
            } catch (Exception e1) {
                log.error("failed to callback: ", e1);
            }
        }
    }

    /**
     * 具体业务接口
     */
    protected abstract Command doBusiness();

    /**
     * 回调
     */
    private void callback(JSONObject jsonObject) {
        CallbackBo callbackBo = new CallbackBo();
        callbackBo.setMessage(message);
        callbackBo.setConfigTaskId(configTaskId);
        callbackBo.setState(state);
        callbackBo.setData(jsonObject);
        HttpClientUtil.post(url, callbackBo, String.class);
    }
}

/**
 * 回调数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class CallbackBo implements Serializable {

    private String parentTaskId;

    private String taskId;

    private String subjectId;

    private String planId;

    private String subPlanId;

    private String configTaskId;

    private Integer state;

    private String message;

    private JSONObject data;
}