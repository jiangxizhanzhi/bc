package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 标记课时练习请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class MarkLessonPracticeReq implements Serializable {

	/**
	 * 课时编号
	 */
	@ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

	/**
	 * 学员id
	 */
	@ApiModelProperty("学员id")
    @NotBlank
    private String userId;

	/**
	 * 等级 1=优 2=良 3=中 4=差
	 */
	@ApiModelProperty("等级 1=优 2=良 3=中 4=差")
    private Integer level;

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
