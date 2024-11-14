package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时理论Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LessonTheoryBo extends TheoryLessonBo {

	/**
	 * 解题详情
	 */
	@ApiModelProperty("解题详情")
	private PracticeSolvedBo resolvedDetail;

}
