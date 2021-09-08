package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.dao.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PracticeBase extends BaseTable implements Serializable {

    /**
     * 状态
     */
    @JsonIgnore
    private Integer status = Status.NORMAL.getValue();

    /**
     * 资源ID
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 是否内建
     */
    @JsonIgnore
    private boolean builtin = false;

    /**
     * 公开
     */
    @TableField("public")
    private boolean isPublic = true;

    /**
     * 所属习题集
     */
    private String practiceSet;

    /**
     * 所属分类
     */
    private String category;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;

    /**
     * 难度, 仅实操题使用, 暂时保留到Base
     */
    private Integer difficulty;

    /**
     * 知识点
     */
    private String knowledge;

    // TODO 是否可丢弃
    /**
     * 分值
     */
    private Float score = 0.0f;

    /**
     * 练习类型
     */
    private Integer practiceType;

    // TODO 规避 db exception
    @TableField(exist = false)
    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Date lastEditTime = new Date();

}
