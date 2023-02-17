package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价打分
 * @TableName marking
 */
@Data
@TableName(value ="marking")
public class Marking extends BaseTable {

    /**
     *
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 评分所属业务（资源）id（课程、工具库...）
     */
    private String businessId;

    /**
     * 评分用户id
     */
    private String userId;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 评分类型（课程1、工具库2）
     */
    private Integer markType;



    public enum ConstantsType {

        /* 常量项类别**/
        TOOL(1, "tool"),
        COURSE(2,  "course");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        ConstantsType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}