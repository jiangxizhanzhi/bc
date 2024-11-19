package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习试卷评估Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticePaperEvaluateBo implements Serializable {

	/**
	 * 姓名
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 提交状态
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("提交状态")
	private Boolean submitState=false;

	/**
	 * 提交时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("提交时间")
	private Date submitTime;

	/**
	 * 理论题正确率
	 *
	 * @see String
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 评分等级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评分等级")
	private Integer level;

	/**
	 * 批改人
	 *
	 * @see String
	 */
	@ApiModelProperty("批改人")
	private String corrector;

	/**
	 * 批改时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("批改时间")
	private Date correctTime;

}
