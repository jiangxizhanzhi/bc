package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
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
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer lessonType;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 组织路径
	 *
	 * @see String
	 */
	@ApiModelProperty("组织路径")
	private String orgPath;

	/**
	 * 父母组织id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("父级组织编号")
	private List<String> parentOrgIds;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 分类
	 *
	 * @see Integer
	 */
	@ApiModelProperty("日程类型")
	private Integer classify;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("日程名称")
	private String name;

	/**
	 * 岗位群
	 *
	 * @see Integer
	 */
	@ApiModelProperty("角色类型")
	private Integer roleGroup;

}
