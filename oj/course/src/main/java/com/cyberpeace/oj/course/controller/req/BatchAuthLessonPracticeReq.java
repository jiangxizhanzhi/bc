package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class BatchAuthLessonPracticeReq implements Serializable {

    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    @ApiModelProperty("组织id")
    @NotBlank
    private String organizationId;

    @ApiModelProperty("是否下发")
    private Boolean isPublic;

    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
