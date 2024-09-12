package com.cyberpeace.oj.course.service.bo.classroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassroomStatisticsBo {

	private String resourceId;

	@ApiModelProperty(value = "教学课程名称")
	private String planName;

	@ApiModelProperty(value = "课程名称")
	private String courseName;

	@ApiModelProperty(value = "课时名称")
	private String lessonName;

	@ApiModelProperty(value = "任课教师")
	private String teacherName;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "完成人数")
	private Integer finishedCount;

	@ApiModelProperty(value = "最高分")
	private Integer highest;

	@ApiModelProperty(value = "最低分")
	private Integer lowest;

	@ApiModelProperty(value = "我的分数")
	private Integer score;
}
