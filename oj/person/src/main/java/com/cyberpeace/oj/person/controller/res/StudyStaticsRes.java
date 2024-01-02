package com.cyberpeace.oj.person.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudyStaticsRes implements Serializable {

	@ApiModelProperty("课时数")
	private Integer lessonCount;

	@ApiModelProperty("课时总数")
	private Integer lessonAllCount;

	@ApiModelProperty("理论课时数")
	private Integer theoryLessonCount;

	private Integer theoryLessonAllCount;

	@ApiModelProperty("实验课时数")
	private Integer expLessonCount;

	private Integer expLessonAllCount;

	@ApiModelProperty("练习数")
	private Integer practiceCount;

	private Integer practiceAllCount;

	@ApiModelProperty("理论练习数")
	private Integer theoryPracticeCount;

	private Integer theoryPracticeAllCount;

	@ApiModelProperty("实操练习数")
	private Integer operationPracticeCount;

	private Integer operationPracticeAllCount;

	@ApiModelProperty("考试数")
	private Integer examCount;

	private Integer examAllCount;

	@ApiModelProperty("理论考试数")
	private Integer theoryExamCount;

	private Integer theoryExamAllCount;

	@ApiModelProperty("实操考试数")
	private Integer operationExamCount;

	private Integer operationExamAllCount;
}
