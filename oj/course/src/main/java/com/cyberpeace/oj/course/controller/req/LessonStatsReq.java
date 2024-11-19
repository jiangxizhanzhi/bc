package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 课时统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonStatsReq extends PageReqBase {
	/**
	 * 课程id
	 *
	 * @see String
	 */
	@NotNull
    @ApiModelProperty("课程id")
    private String course_id;

	/**
	 * 课时类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
    private Integer type;

}
