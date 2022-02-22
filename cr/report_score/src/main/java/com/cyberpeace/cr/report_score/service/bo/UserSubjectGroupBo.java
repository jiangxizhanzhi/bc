package com.cyberpeace.cr.report_score.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("科目分组信息")
@Data
public class UserSubjectGroupBo implements Serializable {

	@ApiModelProperty("用户ID")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("科目")
	private Integer subject;

	@ApiModelProperty("科目总分")
	private String subjectScore;

}
