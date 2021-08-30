package com.cyberpeace.rtks.service.bo;

import java.util.List;
import java.io.Serializable;

import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程与知识点关系绑定")
public class CourseKnowledgeBo implements Serializable {

    @ApiModelProperty("课程资源编号")
    private String resourceId;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("课程描述")
    private String description;

    @ApiModelProperty("课程方向")
    private String direction;

    @ApiModelProperty("方向名称")
    private String directionName;

    @ApiModelProperty("课程logo")
    private String logo;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

    @ApiModelProperty("课程时长")
    private Integer courseDuration = 0;

    @ApiModelProperty("课时总数")
    private Integer lessonCount = 0;

    @ApiModelProperty("完成学习数量")
    private Integer completeCount = 0;

    @ApiModelProperty("理论课数量")
    private Integer theoryCount = 0;

    @ApiModelProperty("完成理论课数量")
    private Integer completeTheory = 0;

    @ApiModelProperty("实验课数量")
    private Integer exerciseCount = 0;

    @ApiModelProperty("完成实验课数量")
    private Integer completeExercise = 0;

    @ApiModelProperty("课程学习进度")
    private Integer process = 0;

    @ApiModelProperty("课程关联知识点信息")
    private List<CourseRelatedRes> knowledges;
}
