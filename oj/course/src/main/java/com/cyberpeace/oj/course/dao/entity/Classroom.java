package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 课堂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_classroom")
public class Classroom extends BaseTable {

	/**
	 * 课程表编号
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("课程表编号")
    private String resourceId;

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
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
    private String courseId;

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
    private String lessonId;

	/**
	 * 组织编号
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
    private String organizationId;

	/**
	 * 子组织编号
	 *
	 * @see String
	 */
	@ApiModelProperty("子组织编号")
    private String organizationIds;

	/**
	 * 创建者编号
	 *
	 * @see String
	 */
	@ApiModelProperty("创建者编号")
    private String createUserId;

	/**
	 * 训练计划编号
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
    private String trainPlanId;

	/**
	 * 训练计划
	 *
	 * @see TrainPlan
	 */
	@TableField(exist=false)
    private TrainPlan trainPlan;

}
