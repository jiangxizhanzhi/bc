package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课理论博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LessonTheoryBo extends TheoryLessonBo {

	/**
	 * 解决详情
	 */
	@ApiModelProperty("解题详情")
	private PracticeSolvedBo resolvedDetail;

}
