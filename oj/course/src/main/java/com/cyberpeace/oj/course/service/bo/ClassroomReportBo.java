package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课堂举报 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ClassroomReportBo implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

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
	 * 注意id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	private String noteId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("实验报告得分")
	private Double score;

	/**
	 * 计算器id
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人编号")
	private String evaluatorId;

	/**
	 * 计算器用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人名称")
	private String evaluatorUserName;

	/**
	 * 计算器名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人姓名")
	private String evaluatorName;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("评分时间")
	private Date evaluateTime;

	/**
	 * 提交时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("报告提交时间")
	private Date submitTime;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	@ApiModelProperty("评语")
	private String evaluateComment;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

}
