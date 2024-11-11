package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ClassroomBo implements Serializable {

	/**
	 * 资源id
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
	 * 课程id
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
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课名称
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课时类型")
	private Integer lessonType;

	/**
	 * 组织id
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
	 * 父母组织id列表
	 */
	@ApiModelProperty("父级组织编号")
	private List<String> parentOrgIds;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 训练计划id
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

	/**
	 * 训练计划名称
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 分类
	 */
	@ApiModelProperty("日程类型")
	private Integer classify;

	/**
	 * 名称
	 */
	@ApiModelProperty("日程名称")
	private String name;

	/**
	 * 岗位群
	 */
	@ApiModelProperty("角色类型")
	private Integer roleGroup;

}
