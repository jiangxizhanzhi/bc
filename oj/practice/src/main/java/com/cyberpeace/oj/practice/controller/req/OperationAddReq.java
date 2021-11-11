package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class OperationAddReq implements Serializable {

    @ApiModelProperty("标题")
    @NotBlank(groups = {OperationAddReq.class})
    private String title;

    @ApiModelProperty("习题集")
    @NotBlank(groups = {OperationAddReq.class})
    private String practiceSetId;

    @ApiModelProperty("方向")
    @NotBlank(groups = {OperationAddReq.class})
    private String categoryId;

    @ApiModelProperty("知识点")
    private List<String> knowledge;

    @ApiModelProperty("难度")
    @NotBlank(groups = {OperationAddReq.class})
    private Integer difficulty;

    @ApiModelProperty("题目描述")
    @NotBlank(groups = {OperationAddReq.class})
    private String content;

    @ApiModelProperty("附件编号")
    private String attachmentId;

    @ApiModelProperty("是否为动态环境")
    @NotNull(groups = {OperationAddReq.class})
    private Boolean dynamicEnv;

    @ApiModelProperty("场景配置名称")
    private String sceneConfigName;

    @ApiModelProperty("场景配置")
    private String ojSceneConfig;

    @ApiModelProperty("场景共享类型 1=个人 2=共享")
    private Integer sceneShareMode;

    @ApiModelProperty("是否为动态flag")
    private Boolean dynamicFlag;

    @ApiModelProperty("场景存活时间")
    @NotNull(groups = {OperationAddReq.class})
    private Integer destroyDelay;

    @ApiModelProperty("答案")
    private List<String> answer;

    @ApiModelProperty("分数")
    @NotNull(groups = {OperationAddReq.class})
    private Integer score;

    @ApiModelProperty("是否公开")
    @NotNull(groups = {OperationAddReq.class})
    private Boolean isPublic;

    @ApiModelProperty("题解是否公开")
    @NotNull(groups = {OperationAddReq.class})
    private Boolean writeupPublic;

    @ApiModelProperty("题解")
    private String writeUp;

}
