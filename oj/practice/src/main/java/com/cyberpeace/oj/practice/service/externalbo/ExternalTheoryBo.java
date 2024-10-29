package com.cyberpeace.oj.practice.service.externalbo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExternalTheoryBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("名称")
	private String content;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("创建人编号")
	private String createUserId;

	@ApiModelProperty("创建人姓名")
	private String createUserName;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer TheoryType;

	@ApiModelProperty("答案")
	private String answer;

	@ApiModelProperty(value = "填空题答案")
	private String blank;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	@ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

}
