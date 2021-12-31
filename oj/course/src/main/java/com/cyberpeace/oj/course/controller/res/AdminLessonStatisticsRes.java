package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AdminLessonStatisticsRes implements Serializable {

	@ApiModelProperty("课时资源编号")
	private String resourceId;

	@ApiModelProperty("课时名称")
	private String name;

	@ApiModelProperty("课时类型")
	private String categoryName;

	@ApiModelProperty("课时难度")
	private Integer difficulty;

	@ApiModelProperty("完成练习数")
	private Integer practiceCount;

	@ApiModelProperty("学习人数")
	private Boolean heat;

	@ApiModelProperty("提交报告数")
	private String finishReportCount;
}
