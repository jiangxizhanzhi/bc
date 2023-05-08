package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程行为列表请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehaviorListReqBody implements Serializable {

    /**
     * 流程图名称
     */
    private String flowChartName;

    /**
     * 分页
     */
    private Integer page;

    /**
     * 页的大小
     */
    private Integer size;

    /**
     * 网络模型
     */
    private Integer netModel;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 客户端类型
     */
    private Integer appType;

    /**
     * 是否实体设备
     */
    private Integer isActual;
}
