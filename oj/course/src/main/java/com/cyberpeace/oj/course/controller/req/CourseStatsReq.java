package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CourseStatsReq extends PageReqBase {
    @ApiModelProperty("方向")
    private String direction;

    @ApiModelProperty("难度")
    private Integer difficult;

}
