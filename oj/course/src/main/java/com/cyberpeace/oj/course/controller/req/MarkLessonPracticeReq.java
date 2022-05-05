package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@ApiModel
public class MarkLessonPracticeReq implements Serializable {

    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    @ApiModelProperty("学员id")
    @NotBlank
    private String userId;

    @ApiModelProperty("等级 1=优 2=良 3=中 4=差")
    private Integer level;

    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
