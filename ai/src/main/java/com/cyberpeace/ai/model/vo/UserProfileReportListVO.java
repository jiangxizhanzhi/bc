package com.cyberpeace.ai.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像报告列表元素")
public class UserProfileReportListVO {

	@ApiModelProperty("报告ID")
	private String reportId;

	@ApiModelProperty("报告名称")
	private String reportName;

	@ApiModelProperty("报告生成时间")
	private String createTime;
}