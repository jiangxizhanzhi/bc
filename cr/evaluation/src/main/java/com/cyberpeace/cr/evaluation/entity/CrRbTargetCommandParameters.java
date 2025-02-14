package com.cyberpeace.cr.evaluation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 靶标命令执行参数(CrRbTargetCommandParameters)实体类
 *
 * @author makejava
 * @since 2025-02-10 11:35:01
 */
@Data
public class CrRbTargetCommandParameters implements Serializable {
    private static final long serialVersionUID = 410390143530554590L;
    /**
     * 主键
     */
    private Integer pk;
    /**
     * ID
     */
    private String id;
    /**
     * 关联的命令模板id
     */
    private String commandId;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数描述
     */
    private String parameterDesc;
    /**
     * 参数类型
     */
    private Integer parameterType;
    /**
     * 参数备注
     */
    private String parameterRemarks;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否必填
     */
    private Integer isRequired;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}

