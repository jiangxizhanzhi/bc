package com.cyberpeace.cr.report_score.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSubjectGroupBo {

	@ApiModelProperty("用户ID")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("科目")
	private String subject;

	@ApiModelProperty("科目总分")
	private String subjectScore;

}
