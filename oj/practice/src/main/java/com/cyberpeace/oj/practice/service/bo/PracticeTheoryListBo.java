package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 练习理论列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeTheoryListBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 分类编号
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 习题集
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String content;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 创建人编号
	 */
	@ApiModelProperty("创建人编号")
	private String createUserId;

	/**
	 * 创建人姓名
	 */
	@ApiModelProperty("创建人姓名")
	private String createUserName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 习题集名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

	/**
	 * 解题状态：
	 */
	@ApiModelProperty("解题状态：")
	private Integer solved;

	/**
	 * 题目类型 0=单选 1=
	 */
	@ApiModelProperty("题目类型 0=单选 1=")
	private Integer TheoryType;

}
