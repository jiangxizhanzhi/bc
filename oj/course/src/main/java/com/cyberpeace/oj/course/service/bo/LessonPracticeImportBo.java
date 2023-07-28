package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.oj.practice.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LessonPracticeImportBo implements Serializable {

	@ApiModelProperty("试卷信息")
	private Paper paper;

	@ApiModelProperty("理论题信息")
	private List<PracticeTheoryRef> practiceTheory = new ArrayList<>();

	@ApiModelProperty("实操题信息")
	private List<PracticeOperationRef> practiceOperation = new ArrayList<>();

	@ApiModelProperty("试卷习题分数信息")
	private List<PaperPracticeContext> paperPracticeContext = new ArrayList<>();
}
