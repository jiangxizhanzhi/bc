package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程WebBo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 课程方向
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 课程方向名称
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课程海报
	 */
	@ApiModelProperty("课程海报")
	private String logo;

	/**
	 * 课程难度
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程创建人
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 创建人名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 讲师
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 学分
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer ebook;

	/**
	 * 课程课时数量
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程课时时长
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

}
