package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 下发任务请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class ReleaseTaskReq implements Serializable {

    /**
     * 通知设置
     */
    private List<String> notifySettings = Lists.newArrayList();

    /**
     * 任务
     */
    private Task task;

    /**
     * 流程图id
     */
    private List<Long> flowChartIds = Lists.newArrayList();

    /**
     * 前置步骤
     */
    private List<String> prevSteps = Lists.newArrayList();

    /**
     * 任务策略
     */
    private String taskStrategy = "";

    /**
     * ip地址
     */
    private String ip;

    /**
     * 是否使用ip识别设备
     */
    private Boolean isUsingIp;

    /**
     * 变量
     */
    private List<ReqVariable> variable = Lists.newArrayList();

    public ReleaseTaskReq() {
        this.setTask(new Task());
    }

    /**
     * 设置设备id
     *
     * @param id id
     */
    public void setDeviceId(String id) {
        this.getTask().setDeviceId(id);
    }

    /**
     * 设置流程图
     *
     * @param id id
     */
    public void setFlowChart(String id) {
        this.getFlowChartIds().add(new Long(id));
    }

    /**
     * 添加变量
     *
     * @param name  变量名称
     * @param value 变量值
     */
    public void addVariable(String name, String value) {
        ReqVariable variable = new ReqVariable(name, value);
        this.variable.add(variable);
    }
}

/**
 * 任务
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
class Task implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备来源
     */
    private Integer deviceSource = 2;

    /**
     * 执行类型
     */
    private Integer execType = 1;

    /**
     * 名称
     */
    private String name = "test";

    /**
     * 是否录制视频
     */
    private String isRecordVideo = "0";

}

/**
 * 请求体变量
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
class ReqVariable implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 变量值
     */
    private String varValue;
    /**
     * 变量类型
     */
    private String variableType = "Text";

    public ReqVariable(String name, String varValue) {
        this.name = name;
        this.varValue = varValue;
    }
}