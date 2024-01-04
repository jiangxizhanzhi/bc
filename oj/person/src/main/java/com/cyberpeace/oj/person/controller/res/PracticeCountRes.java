package com.cyberpeace.oj.person.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeCountRes implements Serializable {

	@ApiModelProperty("课程学习统计-累计学习人数")
	private Integer lessonStudyCount;

	@ApiModelProperty("课程学习统计-累计完成人数")
	private Integer lessonFinishCount;

	@ApiModelProperty("习题练习统计-累计练习人数")
	private Integer peopleCount;

	@ApiModelProperty("习题练习统计-累计练习次数")
	private Integer practiceCount;

	@ApiModelProperty("习题练习统计-平均练习次数")
	private Integer averageCount;


	@ApiModelProperty("模拟考试统计-累计模拟人次")
	private Integer paperCount;

	@ApiModelProperty("模拟考试统计-累计通过人数")
	private Integer paperPassPersonCount;

	@ApiModelProperty("模拟考试统计-累计通过次数")
	private Integer paperPassCount;
}
