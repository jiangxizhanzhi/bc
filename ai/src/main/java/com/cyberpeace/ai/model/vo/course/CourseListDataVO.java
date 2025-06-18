package com.cyberpeace.ai.model.vo.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程完成数据")
public class CourseListDataVO {

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("所属模块")
	private String moduleName;

	@ApiModelProperty("状态")
	private String status;

	@ApiModelProperty("完成状态")
	private Boolean finished;

	@ApiModelProperty("课程时长（小时）")
	private Integer duration;

	@ApiModelProperty("完成日期")
	private String finishDate;

}