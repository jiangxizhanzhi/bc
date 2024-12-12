package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 批量认证课实践请求体
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
public class BatchAuthLessonPracticeReq implements Serializable {

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("组织id")
    @NotBlank
    private String organizationId;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否下发")
    private Boolean isPublic;

    /**
     * 课堂id
     *
     * @see String
     */
    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
