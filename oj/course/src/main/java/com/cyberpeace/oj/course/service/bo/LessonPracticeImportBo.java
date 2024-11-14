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

/**
 * 课时练习导入Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class LessonPracticeImportBo implements Serializable {

	/**
	 * 试卷信息
	 */
	@ApiModelProperty("试卷信息")
	private Paper paper;

	/**
	 * 理论题信息
	 */
	@ApiModelProperty("理论题信息")
	private List<PracticeTheoryRef> practiceTheory = new ArrayList<>();

	/**
	 * 实操题信息
	 */
	@ApiModelProperty("实操题信息")
	private List<PracticeOperationRef> practiceOperation = new ArrayList<>();

	/**
	 * 试卷习题分数信息
	 */
	@ApiModelProperty("试卷习题分数信息")
	private List<PaperPracticeContext> paperPracticeContext = new ArrayList<>();
}
