package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课实操博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class
LessonOperationBo extends OperationLessonBo {

	/**
	 * 解决详情
	 */
	@ApiModelProperty("实操练习解题详情")
	private PracticeSolvedBo resolvedDetail;

}
