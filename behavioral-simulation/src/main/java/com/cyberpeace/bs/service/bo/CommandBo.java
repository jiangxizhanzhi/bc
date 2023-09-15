package com.cyberpeace.bs.service.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 配管下发的命令
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class CommandBo<T extends Serializable> implements Serializable {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 子任务id
     */
    private String subTaskId;

    /**
     * 想定id
     */
    private String xdschemaId;

    /**
     * 代理类型
     */
    private String agentType;

    /**
     * 上报路径
     */
    private String reportUrl;

    /**
     * 配置
     */
    private List<Configuration<T>> configurations;

    /**
     * 配置任务id
     */
    private String configTaskId;

    /**
     * 代理信息
     */
    private AgentInfo agentInfo;

    /**
     * 获取指令
     *
     * @return {@code T}
     */
    public T getCommandBody() {
        return configurations.get(0).getPolicy();
    }
}

/**
 * 配置
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class Configuration<T extends Serializable> implements Serializable {

    /**
     * 目标信息
     */
    private TargetInfo targetInfo;

    /**
     * 指令
     */
    private T policy;

    /**
     * 行动
     */
    private String action;
}

/**
 * 目标信息
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class TargetInfo implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 主机
     */
    private String host;

    /**
     * 名称
     */
    private String name;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 镜像类型
     */
    private String imageType;
}

/**
 * 代理信息
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class AgentInfo implements Serializable {

    /**
     * 代理id
     */
    private String agentId;

    /**
     * 代理类型
     */
    private String agentType;

    /**
     * url
     */
    private String url;
}