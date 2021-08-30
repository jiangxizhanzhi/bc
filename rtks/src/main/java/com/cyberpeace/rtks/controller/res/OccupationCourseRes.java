package com.cyberpeace.rtks.controller.res;

import com.cyberpeace.rtks.service.bo.CourseKnowledgeBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("职业课程学习进度")
public class OccupationCourseRes implements Serializable {

    @ApiModelProperty("课程进度")
    private Integer coursePercent;

    @ApiModelProperty("理论课时进度")
    private Integer theoryPercent;

    @ApiModelProperty("实验课时进度")
    private Integer exercisePercent;

    @ApiModelProperty("课程知识点信息")
    private List<CourseKnowledgeBo> knowledgeBos;

}
