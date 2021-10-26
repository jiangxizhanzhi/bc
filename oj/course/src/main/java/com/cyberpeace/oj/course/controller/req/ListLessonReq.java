package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("课时列表查询")
public class ListLessonReq extends PageReqBase {

    @ApiModelProperty("课时类型 0=理论课 1=实验课 用于课程下课时列表筛选")
    private Integer type;

    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseId;

    @ApiModelProperty("要排除的课程编号 用于课时导入筛选")
    private String exceptCourse;

    @ApiModelProperty("课程方向筛选条件 用于课时导入筛选")
    private String categoryId;

}
