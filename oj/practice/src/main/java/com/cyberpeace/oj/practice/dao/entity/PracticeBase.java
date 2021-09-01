package com.cyberpeace.oj.practice.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PracticeBase extends BaseTable implements Serializable {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 是否内建
     */
    private boolean builtin;

    /**
     * 公开
     */
    private boolean isPublic;

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

    /**
     * 分值
     */
    private Float score;

}
