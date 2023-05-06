package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 远程行为返回体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehaviorRspBo implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 流程图名称
     */
    private String flowChartName;

    /**
     * 应用程序名称
     */
    private String appName;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 网络模型
     */
    private Integer netModel;

    /**
     * 步骤数
     */
    private Integer stepNum;

    /**
     * 消耗时间
     */
    private Integer consumeTime;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 类型
     */
    private String type;

    /**
     * 行为依赖
     */
    private String application;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 标签
     */
    private String tag;

    /**
     * 客户端类型
     */
    private Integer appType;

    /**
     * 是否实体设备
     */
    private Integer isActual;

    /**
     * 变量
     */
    private List<LcVariableRspBo> variables;
}
