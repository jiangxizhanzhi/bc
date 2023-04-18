package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class NoteBo implements Serializable {

    @ApiModelProperty("笔记资源编号")
    private String resourceId;

    @ApiModelProperty("笔记类型 1=笔记 2=实验报告")
    private Integer noteType;

    @ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习")
    private Integer businessType;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("创建人")
    private String userId;

    @ApiModelProperty("对应的资源编号")
    private String businessId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

}
