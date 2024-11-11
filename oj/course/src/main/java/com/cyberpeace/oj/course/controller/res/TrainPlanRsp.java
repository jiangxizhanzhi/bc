package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 训练计划响应
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TrainPlanRsp implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("训练计划编号")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("训练计划名称")
    private String name;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 计数
     */
    @ApiModelProperty("数量")
    private Integer count;

    /**
     * 创造用户id
     */
    @ApiModelProperty("创建人id")
    private String createUserId;

    /**
     * 创建用户
     */
    @ApiModelProperty("创建人名称")
    private String createUser;
}
