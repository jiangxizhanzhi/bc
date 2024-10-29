package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class TheoryAddReq implements Serializable {

	@ApiModelProperty(value = "内容", required = true)
	@NotBlank(groups = { Create.class })
	private String content;

	@ApiModelProperty(value = "填空题答案", required = true)
	private String blank;

//	@ApiModelProperty(value = "习题集", required = true)
//	private String practiceSetId = "default";

	@ApiModelProperty(value = "分类", required = true)
	@NotBlank(groups = { Create.class })
	private String categoryId;

	@ApiModelProperty(value = "答案", required = true)
	@NotBlank(groups = { Create.class })
	private String answer;

	@ApiModelProperty(value = "理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简答", required = true)
	@NotNull(groups = { Create.class })
	private Integer theoryType;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	@ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

}
