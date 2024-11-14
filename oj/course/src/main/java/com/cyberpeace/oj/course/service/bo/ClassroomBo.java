package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

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
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课时编号
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 课时类型
	 */
	@ApiModelProperty("课时类型")
	private Integer lessonType;

	/**
	 * 组织编号
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 组织路径
	 */
	@ApiModelProperty("组织路径")
	private String orgPath;

	/**
	 * 父级组织编号
	 */
	@ApiModelProperty("父级组织编号")
	private List<String> parentOrgIds;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 训练计划编号
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

	/**
	 * 训练计划名称
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 日程类型
	 */
	@ApiModelProperty("日程类型")
	private Integer classify;

	/**
	 * 日程名称
	 */
	@ApiModelProperty("日程名称")
	private String name;

	/**
	 * 角色类型
	 */
	@ApiModelProperty("角色类型")
	private Integer roleGroup;

}
