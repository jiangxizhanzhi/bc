package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课理论博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see TheoryLessonBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LessonTheoryBo extends TheoryLessonBo {

	/**
	 * 解决详情
	 *
	 * @see PracticeSolvedBo
	 */
	@ApiModelProperty("解题详情")
	private PracticeSolvedBo resolvedDetail;

}
