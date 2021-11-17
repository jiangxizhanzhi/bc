package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class LessonPracticeListBo implements Serializable {

	private Integer count;

	private Double totalScore;

	private List<? extends TheoryLessonBo> practiceBos;

}
