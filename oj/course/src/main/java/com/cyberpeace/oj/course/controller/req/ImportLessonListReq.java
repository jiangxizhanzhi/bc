package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 导入课列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ImportLessonListReq extends PageReqBase {

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("课程分类")
    private String categoryId;

    /**
     * 除了课程
     *
     * @see String
     */
    @ApiModelProperty("当前课程")
    private String exceptCourse;

}
