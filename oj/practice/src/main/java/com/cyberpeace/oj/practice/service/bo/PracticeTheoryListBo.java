package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实践理论列表博
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
public class PracticeTheoryListBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("方向编号")
	private String courseCategoryId;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

//	@ApiModelProperty("习题集")
//	private String practiceSetId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String content;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人编号")
	private String createUserId;

	/**
	 * 创造用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人姓名")
	private String createUserName;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

//	@ApiModelProperty("习题集名称")
//	private String practiceSetName;

	/**
	 * 解决
	 *
	 * @see Integer
	 */
	@ApiModelProperty("解题状态：")
	private Integer solved;

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目类型 0=单选 1=")
	private Integer TheoryType;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型，常量type=50， 1：视频题，2：图片题，3：文字题")
	private Integer type;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "视频链接")
	private String video;

}
