package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 标记课实践请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class MarkLessonPracticeReq implements Serializable {

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty("学员id")
    @NotBlank
    private String userId;

    /**
     * 水平
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
