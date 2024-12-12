package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课实操博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see OperationLessonBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class
LessonOperationBo extends OperationLessonBo {

	/**
	 * 解决详情
	 *
	 * @see PracticeSolvedBo
	 */
	@ApiModelProperty("实操练习解题详情")
	private PracticeSolvedBo resolvedDetail;

}
