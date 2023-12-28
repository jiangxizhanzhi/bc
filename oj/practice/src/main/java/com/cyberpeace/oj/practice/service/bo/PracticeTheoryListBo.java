package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class PracticeTheoryListBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("分类编号")
	private String categoryId;


	@ApiModelProperty("习题集")
	private String practiceSetId;

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

	@ApiModelProperty("习题集名称")
	private String practiceSetName;

	@ApiModelProperty("解题状态：")
	private Integer solved;

	@ApiModelProperty("题目类型 0=单选 1=")
	private Integer TheoryType;

}
