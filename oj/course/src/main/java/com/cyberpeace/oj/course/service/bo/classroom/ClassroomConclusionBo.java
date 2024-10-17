package com.cyberpeace.oj.course.service.bo.classroom;

import com.cyberpeace.oj.ojbase.constant.ClassReportStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassroomConclusionBo {

	@ApiModelProperty("学号")
	private String userName;

	@ApiModelProperty("用户姓名")
	private String name;

	@ApiModelProperty("组织名称")
	private String organizationName;

	@ApiModelProperty("批阅情况")
	private String status;

	@ApiModelProperty("实验报告得分")
	private Double score;

	@ApiModelProperty("评分人姓名")
	private String evaluatorName;

	@ApiModelProperty(value = "最高分")
	private Double highest;

	@ApiModelProperty(value = "最低分")
	private Double lowest;

	public void setStatus(Integer status) {
		if (ClassReportStatus.NOT_SUBMITTED.equals(status)) {
			this.status = "未提交";
		} else if (ClassReportStatus.NOT_EVALUATED.equals(status)) {
			this.status = "未评分";
		} else if (ClassReportStatus.NOT_PASSED.equals(status)) {
			this.status = "未通过";
		} else if (ClassReportStatus.PASSED.equals(status)) {
			this.status = "通过";
		}
	}
}
