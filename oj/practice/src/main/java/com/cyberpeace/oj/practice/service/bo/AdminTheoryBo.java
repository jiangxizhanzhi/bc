package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理理论博
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
public class AdminTheoryBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 空白
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "填空题答案")
	private String blank;

//	@ApiModelProperty(value = "习题集")
//	private String practiceSetId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "分类")
	private String categoryId;

	/**
	 * 课程分类id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位方向", required = true)
	private String courseCategoryId;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "答案")
	private String answer;

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer theoryType;

	/**
	 * 知识
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("攻击知识点")
	private JSONArray attackTag;

	@ApiModelProperty("防御知识点")
	private JSONArray defenseTag;

	/**
	 * 选择
	 *
	 * @see String
	 */
	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	/**
	 * 关键词
	 *
	 * @see String
	 */
	@ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

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
