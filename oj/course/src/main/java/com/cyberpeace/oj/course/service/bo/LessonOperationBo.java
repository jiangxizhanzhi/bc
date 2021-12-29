package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonOperationBo extends OperationLessonBo {

	@ApiModelProperty("实操练习解题详情")
	private PracticeSolvedBo resolvedDetail;

}
