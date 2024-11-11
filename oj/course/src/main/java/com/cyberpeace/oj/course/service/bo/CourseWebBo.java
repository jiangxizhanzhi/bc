package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程 Web bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程数量
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 类别id
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 图标
	 */
	@ApiModelProperty("课程海报")
	private String logo;

	/**
	 * 困难
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 创造用户名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 讲师
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 得分
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 电子书
	 */
	@ApiModelProperty("课程类型")
	private Integer ebook;

	/**
	 * 课计数
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程时间
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

}
