package com.cyberpeace.oj.practice.service.externalBo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ExternalTheoryBo implements Serializable {

    /** 资源ID*/
    private String resourceId;

    /** 是否内建*/
    @JsonIgnore
    private boolean builtin;

    /** 所属分类*/
    private String categoryId;

    /** 标题*/
    private String title;

    /** 公开*/
    @TableField("public")
    private boolean isPublic = true;

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

    /** 题型 */
    private Integer theoryType;

    @TableField("`option`")
    private String option;

    private String keyword;

}
