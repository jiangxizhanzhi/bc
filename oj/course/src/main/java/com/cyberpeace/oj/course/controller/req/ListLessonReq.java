package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("课时列表查询")
public class ListLessonReq extends PageReqBase {

    @ApiModelProperty("课时类型")
    private Integer type;

    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseId;

}
