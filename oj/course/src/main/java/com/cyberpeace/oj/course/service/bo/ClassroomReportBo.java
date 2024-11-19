package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课堂报告Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ClassroomReportBo implements Serializable {

	/**
	 * 用户编号
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String userName;

	/**
	 * 用户姓名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

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
	 * 笔记编号
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	private String noteId;

	/**
	 * 实验报告内容
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 实验报告得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("实验报告得分")
	private Double score;

	/**
	 * 评分人编号
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人编号")
	private String evaluatorId;

	/**
	 * 评分人名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人名称")
	private String evaluatorUserName;

	/**
	 * 评分人姓名
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人姓名")
	private String evaluatorName;

	/**
	 * 评分时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("评分时间")
	private Date evaluateTime;

	/**
	 * 报告提交时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("报告提交时间")
	private Date submitTime;

	/**
	 * 评语
	 *
	 * @see String
	 */
	@ApiModelProperty("评语")
	private String evaluateComment;

	/**
	 * 是否通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

}
