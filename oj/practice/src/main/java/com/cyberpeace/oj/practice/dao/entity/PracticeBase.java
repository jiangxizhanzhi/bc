package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeBase extends BaseTable implements Serializable {

    /** 资源ID*/
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /** 是否内建*/
    @JsonIgnore
    private Boolean builtin = false;

    /** 所属分类*/
    private String categoryId;

    /** 标题*/
    private String title;

    /** 公开*/
    @TableField("public")
    private Boolean isPublic;

    /** 所属习题集*/
    private String practiceSetId;

    /**内容*/
    private String content;

    /**
     * 答案 不同题型记录形式不同. 例: 单选-A, 多选-A|B, 判断-A, 填空-[{\"weight\":40,\"value\":\"A|a\"}, {\"weight\":60,\"value\":\"B\"}], 简答-纯文本")
     */
    private String answer;

    /** 难度, 目前仅操作题使用 0-初级, 1-中级, 2-高级*/
    private Integer difficulty;

    /** 知识点*/
    private String knowledge;

    /** 分值*/
    private Double score = 0.0d;

    /** 创建者*/
    private String createUserId;

}
