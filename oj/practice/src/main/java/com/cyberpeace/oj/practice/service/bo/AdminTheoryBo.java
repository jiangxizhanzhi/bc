package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理理论Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminTheoryBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 填空题答案
	 */
	@ApiModelProperty(value = "填空题答案")
	private String blank;

	/**
	 * 习题集
	 */
	@ApiModelProperty(value = "习题集")
	private String practiceSetId;

	/**
	 * 分类
	 */
	@ApiModelProperty(value = "分类")
	private String categoryId;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "答案")
	private String answer;

	/**
	 * 理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单
	 */
	@ApiModelProperty(value = "理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer theoryType;

	/**
	 * 知识点
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 选项 - (判断、单选、多选题型使用)
	 */
	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	/**
	 * 关键字 - (简答题题型使用)
	 */
	@ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

}
