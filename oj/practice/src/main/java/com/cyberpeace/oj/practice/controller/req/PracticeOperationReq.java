package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("实践操作练习")
public class PracticeOperationReq {

    @ApiModelProperty(value = "ID", required = true)
    private String resourceId;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "习题集", required = true)
    private String practiceSet;

    @ApiModelProperty(value = "分类", required = true)
    private String category;

    @ApiModelProperty(value = "答案", required = true)
    private String answer;

    @ApiModelProperty("知识点")
    private String knowledge;

    @ApiModelProperty(value = "难度", required = true)
    private Integer difficulty;

    @ApiModelProperty("附件")
    private String file;

    // TODO 获取方式待定
    @ApiModelProperty("附件名称")
    private String fileName;

    @ApiModelProperty("是否动态答题环境")
    private boolean isDynamicEnv;

    @ApiModelProperty("关联场景")
    private String ojSceneConfig;

    @ApiModelProperty("共享模式")
    private Integer shareMode;

    @ApiModelProperty("是否使用动态FLAG")
    private boolean isDynamicFlag;

    @ApiModelProperty("场景存活时间")
    private Integer destroyDelay;

}
