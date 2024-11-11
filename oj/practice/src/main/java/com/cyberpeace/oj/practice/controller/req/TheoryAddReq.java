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
 * @date 2024/11/11
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

	/**
	 * 练习设置id
	 */
	@ApiModelProperty(value = "习题集", required = true)
	@NotBlank(groups = { Create.class })
	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "分类", required = true)
	@NotBlank(groups = { Create.class })
	private String categoryId;

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

}
