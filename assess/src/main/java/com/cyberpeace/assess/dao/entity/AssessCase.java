package com.cyberpeace.assess.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 
 * @TableName assess_case
 */
@Data
@TableName("assess_case")
public class AssessCase extends BaseTable {

    /**
     * 
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 评估材料名称
     */
    private String name;

    /**
     * 评估案例概述
     */
    private String overview;

    /**
     * 封面
     */
    private String cover;

    /**
     * 标签
     */
    private String tag;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String createUserId;

}