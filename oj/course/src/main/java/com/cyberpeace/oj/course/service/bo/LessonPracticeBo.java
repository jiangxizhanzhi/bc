package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时练习Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonPracticeBo implements Serializable {

	/**
	 * 课时简略信息
	 *
	 * @see LessonBriefBo
	 */
	@ApiModelProperty("课时简略信息")
	private LessonBriefBo lessonInfo;

	/**
	 * 课后练习题信息
	 *
	 * @see Map<String, LessonPracticeListBo>
	 */
	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;

	/**
	 * 评估结果
	 *
	 * @see EvaluateResult
	 */
	@ApiModelProperty("评估结果")
	private EvaluateResult evaluateResult;

	/**
	 * 课时简略信息
	 *
	 * @see PracticeBriefBo
	 */
	@ApiModelProperty("课时简略信息")
	private PracticeBriefBo practiceInfo;

}
