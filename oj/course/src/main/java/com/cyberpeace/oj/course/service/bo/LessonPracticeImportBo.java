package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程练习导入 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class LessonPracticeImportBo implements Serializable {

	/**
	 * 纸
	 *
	 * @see Paper
	 */
	@ApiModelProperty("试卷信息")
	private Paper paper;

	/**
	 * 实践理论
	 *
	 * @see List<PracticeTheoryRef>
	 */
	@ApiModelProperty("理论题信息")
	private List<PracticeTheoryRef> practiceTheory = new ArrayList<>();

	/**
	 * 实践实操
	 *
	 * @see List<PracticeOperationRef>
	 */
	@ApiModelProperty("实操题信息")
	private List<PracticeOperationRef> practiceOperation = new ArrayList<>();

	/**
	 * 纸质实践背景
	 *
	 * @see List<PaperPracticeContext>
	 */
	@ApiModelProperty("试卷习题分数信息")
	private List<PaperPracticeContext> paperPracticeContext = new ArrayList<>();
}
