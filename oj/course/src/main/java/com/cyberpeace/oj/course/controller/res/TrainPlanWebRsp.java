package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class TrainPlanWebRsp implements Serializable {

	/**
	 * 训练计划编号
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String resourceId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂id")
	private String roomIds;

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


	/**
	 * 课后练习数
	 *
	 * @see String
	 */
	@ApiModelProperty("课后练习数")
	private Integer coursePracticeCount;

	/**
	 * 课后练习总数
	 *
	 * @see String
	 */
	@ApiModelProperty("课后练习总数")
	private Integer coursePracticeCountTotal;

	/**
	 * 实验报告数
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告数")
	private Integer experimentReportCount;

	/**
	 * 实验报告总数
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告总数")
	private Integer experimentReportCountTotal;

	/**
	 * 任务总数
	 *
	 * @see String
	 */
	@ApiModelProperty("任务总数")
	private Integer missionCountTotal;

	/**
	 * 任务完成数
	 *
	 * @see String
	 */
	@ApiModelProperty("任务完成数")
	private Integer missionCount;

	/**
	 * 任务列表
	 *
	 * @see String
	 */
	@ApiModelProperty("任务列表")
	private List<TrainPlanLessonRsp> lessons;


}
