package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导入课时列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class ImportLessonListReq extends PageReqBase {

    /**
     * 课程分类
     *
     * @see String
     */
    @ApiModelProperty("课程分类")
    private String categoryId;

    /**
     * 当前课程
     *
     * @see String
     */
    @ApiModelProperty("当前课程")
    private String exceptCourse;

}
