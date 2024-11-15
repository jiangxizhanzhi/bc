package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 理论新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class TheoryAddReq implements Serializable {

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = true)
	@NotBlank(groups = { Create.class })
	private String content;

	/**
	 * 空白
	 */
	@ApiModelProperty(value = "填空题答案", required = true)
	private String blank;

//	@ApiModelProperty(value = "习题集", required = true)
//	private String practiceSetId = "default";

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "分类", required = true)
	@NotBlank(groups = { Create.class })
	private String categoryId;

	/**
	 * 课程分类id
	 */
	@ApiModelProperty(value = "岗位方向", required = true)
	@NotBlank(groups = { Create.class })
	private String courseCategoryId;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "答案", required = true)
	@NotBlank(groups = { Create.class })
	private String answer;

	/**
	 * 理论类型
	 */
	@ApiModelProperty(value = "理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简答", required = true)
	@NotNull(groups = { Create.class })
	private Integer theoryType;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 选择
	 */
	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	/**
	 * 关键词
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
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

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
