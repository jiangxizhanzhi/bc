package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 训练计划响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanRsp implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("训练计划编号")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("训练计划名称")
    private String name;

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 计数
     *
     * @see Integer
     */
    @ApiModelProperty("数量")
    private Integer count;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人id")
    private String createUserId;

    /**
     * 创建用户
     *
     * @see String
     */
    @ApiModelProperty("创建人名称")
    private String createUser;
}
