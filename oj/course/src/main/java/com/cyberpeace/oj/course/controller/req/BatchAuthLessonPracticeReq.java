package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 批量认证课实践请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class BatchAuthLessonPracticeReq implements Serializable {

    /**
     * 课id
     */
    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    @NotBlank
    private String organizationId;

    /**
     * 是公共
     */
    @ApiModelProperty("是否下发")
    private Boolean isPublic;

    /**
     * 课堂id
     */
    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
