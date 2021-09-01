package com.cyberpeace.oj.practice.dao.entityUnused;

import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PracticeBase extends BaseTable implements Serializable {

    // 状态
    private Integer status;

    // 资源ID
    private String resourceId;

    // 是否内建
    private boolean builtin;

    @ApiModelProperty("公开")
    private boolean isPublic;

    @ApiModelProperty("所属习题集")
    private String practiceSet;

    @ApiModelProperty("所属分类")
    private String category;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("答案")
    private String answer;

    // 仅实操题使用, 保留到Base
    @ApiModelProperty("难度")
    private Integer difficulty;

    @ApiModelProperty("知识点")
    private String knowledge;

    @ApiModelProperty("分值")
    private Float score;

}
