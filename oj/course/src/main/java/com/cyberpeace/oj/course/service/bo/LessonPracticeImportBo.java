package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程练习导入 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class LessonPracticeImportBo implements Serializable {

	/**
	 * 纸
	 */
	@ApiModelProperty("试卷信息")
	private Paper paper;

	/**
	 * 实践理论
	 */
	@ApiModelProperty("理论题信息")
	private List<PracticeTheoryRef> practiceTheory = new ArrayList<>();

	/**
	 * 实践实操
	 */
	@ApiModelProperty("实操题信息")
	private List<PracticeOperationRef> practiceOperation = new ArrayList<>();

	/**
	 * 纸质实践背景
	 */
	@ApiModelProperty("试卷习题分数信息")
	private List<PaperPracticeContext> paperPracticeContext = new ArrayList<>();
}
