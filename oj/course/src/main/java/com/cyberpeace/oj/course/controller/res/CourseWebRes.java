package com.cyberpeace.oj.course.controller.res;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("前端课程基本信息")
public class CourseWebRes extends CourseWebBo {

    @ApiModelProperty("学习进度")
    CourseStudyProcessBo courseStudyProcess;
}
