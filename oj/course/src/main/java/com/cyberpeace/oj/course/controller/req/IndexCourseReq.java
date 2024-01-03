package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexCourseReq {

    String resourceId;

    @ApiModelProperty("课程名称")
    private String courseName;
    @ApiModelProperty("覆盖度")
    private Integer coverage;
    @ApiModelProperty("权重")
    private String weight;

    @ApiModelProperty("课程数")
    private Integer courseNum;

    @ApiModelProperty("完成百分比")
    private Integer completePercent;

    @ApiModelProperty("描述")
    private String desc;
}
