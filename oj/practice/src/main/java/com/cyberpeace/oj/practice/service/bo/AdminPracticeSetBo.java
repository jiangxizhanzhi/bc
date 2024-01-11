package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminPracticeSetBo extends AuthTable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("简介")
	private String desc;

	@ApiModelProperty("logo")
	private String logo;

	@ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
	private Integer practiceType;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("创建人")
	private String createUserId;

	@ApiModelProperty("创建人名称")
	private String createUserName;

	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	@ApiModelProperty("授权操作人数量")
	private Integer operationCount;

	@ApiModelProperty("授权访问人数量")
	private Integer readCount;

	@ApiModelProperty("真题标签")
	private String tag;
}
