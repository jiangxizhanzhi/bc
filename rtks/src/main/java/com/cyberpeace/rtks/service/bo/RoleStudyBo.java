package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户学习记录
 */
@Data
@ApiModel("学习记录统计数据")
public class RoleStudyBo implements Serializable {

	@ApiModelProperty("总课程数")
	private Integer totalCourses;

	@ApiModelProperty("完成课程数")
	private Integer finishedCourses;

	@ApiModelProperty("总理论题数")
	private Integer totalTheories;

	@ApiModelProperty("完成理论题数")
	private Integer finishedTheories;

	@ApiModelProperty("总实操题数")
	private Integer totalOperates;

	@ApiModelProperty("完成实操题数")
	private Integer finishedOperates;

	@ApiModelProperty("完成理论课时数量")
	private Integer finishedTheoryLessons;

	@ApiModelProperty("未完成理论课时数量")
	private Integer unFinishedTheoryLessons;

	@ApiModelProperty("完成实验课时数量")
	private Integer finishedPracticeLessons;

	@ApiModelProperty("未完成实验课时数量")
	private Integer unFinishedPracticeLessons;

}
