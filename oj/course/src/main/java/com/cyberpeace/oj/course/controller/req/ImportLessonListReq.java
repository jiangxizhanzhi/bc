package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ImportLessonListReq extends PageReqBase {

    @ApiModelProperty("课程分类")
    private String categoryId;

    @ApiModelProperty("当前课程")
    private String exceptCourse;

}
