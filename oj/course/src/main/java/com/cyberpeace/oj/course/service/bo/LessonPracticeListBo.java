package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class LessonPracticeListBo implements Serializable {

	@ApiModelProperty("习题总数")
	private Integer count;

	@ApiModelProperty("总分")
	private Double totalScore;

	private List<? extends TheoryLessonBo> practiceBos;

}
