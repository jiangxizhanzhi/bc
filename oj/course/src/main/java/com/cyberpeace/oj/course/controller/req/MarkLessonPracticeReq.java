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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class MarkLessonPracticeReq implements Serializable {

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

	/**
	 * 学员id
	 *
	 * @see String
	 */
	@ApiModelProperty("学员id")
    @NotBlank
    private String userId;

	/**
	 * 等级 1=优 2=良 3=中 4=差
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级 1=优 2=良 3=中 4=差")
    private Integer level;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
