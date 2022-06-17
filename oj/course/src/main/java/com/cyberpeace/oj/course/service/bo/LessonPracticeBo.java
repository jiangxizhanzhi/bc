package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonPracticeBo implements Serializable {

	@ApiModelProperty("课时简略信息")
	private LessonBriefBo lessonInfo;

	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;

	@ApiModelProperty("评估结果")
	private EvaluateResult evaluateResult;

	@ApiModelProperty("课时简略信息")
	private PracticeBriefBo practiceInfo;

}
