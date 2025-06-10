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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ClassroomBo extends BaseClassTimeBo implements Serializable {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程编号
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
	 * BASE(0, "基础"), ATTACK(2, "攻击"), DEFENSE(3, "防御");  未使用到
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课时名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 课时类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer lessonType;

	/**
	 * 组织编号
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
	 * 父级组织编号
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("父级组织编号")
	private List<String> parentOrgIds;

	/**
	 * 创建人
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 训练计划编号
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
	 * 日程类型
	 *
	 * @see Integer
	 * @see com.cyberpeace.oj.ojbase.constant.ClassroomType
	 */
	@ApiModelProperty("日程类型")
	private Integer classify;

	/**
	 * 日程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("日程名称")
	private String name;

	/**
	 * 角色类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("角色类型")
	private Integer roleGroup;

}
