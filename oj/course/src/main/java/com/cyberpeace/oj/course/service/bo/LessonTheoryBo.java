package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonTheoryBo extends TheoryLessonBo {

	@ApiModelProperty("解题详情")
	private PracticeSolvedBo resolvedDetail;

}
