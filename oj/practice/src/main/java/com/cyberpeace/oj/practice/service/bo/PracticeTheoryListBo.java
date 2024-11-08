package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实践理论列表博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class PracticeTheoryListBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

//	@ApiModelProperty("习题集")
//	private String practiceSetId;

	/**
	 * 内容
	 */
	@ApiModelProperty("名称")
	private String content;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("创建人编号")
	private String createUserId;

	/**
	 * 创造用户名称
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

//	@ApiModelProperty("习题集名称")
//	private String practiceSetName;

	/**
	 * 解决
	 */
	@ApiModelProperty("解题状态：")
	private Integer solved;

	/**
	 * 理论类型
	 */
	@ApiModelProperty("题目类型 0=单选 1=")
	private Integer TheoryType;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型，常量type=50， 1：视频题，2：图片题，3：文字题")
	private Integer type;

	/**
	 * 视频 URL
	 */
	@ApiModelProperty(value = "视频链接")
	private String video;

}
