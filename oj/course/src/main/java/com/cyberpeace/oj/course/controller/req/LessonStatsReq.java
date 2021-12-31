package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class LessonStatsReq extends PageReqBase {
    @NotNull
    @ApiModelProperty("课程id")
    private String course_id;

    @ApiModelProperty("课时类型")
    private Integer type;

}
