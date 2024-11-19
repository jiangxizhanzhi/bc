package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位课程Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("职业课程基本信息")
public class RoleCourseBo implements Serializable {

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
    private String resourceId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
    private String name;

	/**
	 * 课程海报
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
    private String logo;

	/**
	 * 课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时数量")
    private Integer lessonCount;

	/**
	 * 课程时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程时长")
    private Integer courseDuration;

	/**
	 * 课程难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
    private Integer difficulty;

	/**
	 * 课程访问权限
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("课程访问权限")
    private Boolean interview;

}
