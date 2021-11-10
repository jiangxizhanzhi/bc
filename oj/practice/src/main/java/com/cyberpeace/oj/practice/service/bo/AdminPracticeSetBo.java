package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.ReadAndOperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminPracticeSetBo extends ReadAndOperation {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("logo")
	private String logo;

	@ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
	private Integer practiceType;

	@ApiModelProperty("是否公开")
	private boolean isPublic;

	@ApiModelProperty("创建人")
	private String createUserId;

	@ApiModelProperty("创建人名称")
	private String createUserName;

	@ApiModelProperty("习题数量")
	private String practiceCount;

}
