package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练计划响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainPlanRsp implements Serializable {

	/**
	 * 训练计划编号
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
    private String resourceId;

	/**
	 * 训练计划名称
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
	 * 数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("数量")
    private Integer count;

	/**
	 * 创建人id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人id")
    private String createUserId;

	/**
	 * 创建人名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人名称")
    private String createUser;

	@ApiModelProperty("状态, 1=未开始，2=进行中，3=已结束")
	private Integer status;
}
