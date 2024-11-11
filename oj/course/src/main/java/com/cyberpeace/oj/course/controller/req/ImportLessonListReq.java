package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 进口课列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ImportLessonListReq extends PageReqBase {

    /**
     * 类别id
     */
    @ApiModelProperty("课程分类")
    private String categoryId;

    /**
     * 除了课程
     */
    @ApiModelProperty("当前课程")
    private String exceptCourse;

}
