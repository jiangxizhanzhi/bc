package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TrainPlanRsp implements Serializable {

    @ApiModelProperty("训练计划编号")
    private String resourceId;

    @ApiModelProperty("训练计划名称")
    private String name;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("创建人id")
    private String createUserId;

    @ApiModelProperty("创建人名称")
    private String createUser;

    @ApiModelProperty("类型, 1=普通，2=连续")
    private Integer type;

    @ApiModelProperty("状态, 1=未开始，2=进行中，3=已结束")
    private Integer status;

    @ApiModelProperty("组织编号")
    private String organizationId;

    @ApiModelProperty("组织名称")
    private String organizationName;

    @ApiModelProperty("组织路径")
    private String orgPath;

    @ApiModelProperty("父级组织编号")
    private List<String> parentOrgIds;

}
