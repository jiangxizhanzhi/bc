package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PracticePaperEvaluateBo implements Serializable {

	@ApiModelProperty("昵称")
	private String name;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("组织名称")
	private String organizationName;

	@ApiModelProperty("提交状态")
	private Boolean submitState=false;

	@ApiModelProperty("提交时间")
	private Date submitTime;

	@ApiModelProperty("理论题正确率")
	private String accuracy;

	@ApiModelProperty("评分等级")
	private Integer level;

	@ApiModelProperty("批改人")
	private String corrector;

	@ApiModelProperty("批改时间")
	private Date correctTime;

}
