package com.cyberpeace.cr.evaluation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 靶标命令执行模板(CrRbTargetCommandTemplates)实体类
 *
 * @author makejava
 * @since 2025-02-10 11:35:19
 */
@Data
public class CrRbTargetCommandTemplates implements Serializable {
    private static final long serialVersionUID = -37247728171232541L;
    /**
     * 主键
     */
    private Integer pk;
    /**
     * ID
     */
    private String id;
    /**
     * 角色
     */
    private Integer role;
    /**
     * 一级分类
     */
    private Integer mainCategory;
    /**
     * 二级分类
     */
    private Integer subCategory;
    /**
     * 三级分类
     */
    private Integer thirdCategory;
    /**
     * 镜像类型：vm，container,simple, entity
     */
    private String imageType;
    /**
     * 业务标签,1:终端防护靶标,2:路由交换靶标,3:边界安全防护设备靶标,4:流量分析靶标,5:应用服务靶标
     */
    private Integer businessTag;
    /**
     * 设备品牌
     */
    private String brand;
    /**
     * 命令模板（带占位符）
     */
    private String commandTemplate;
    /**
     * 命令名称
     */
    private String commandName;
    /**
     * 命令描述
     */
    private String commandDescription;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}

