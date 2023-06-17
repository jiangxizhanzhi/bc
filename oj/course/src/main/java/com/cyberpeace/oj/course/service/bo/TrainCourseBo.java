package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainCourseBo implements Serializable {

    @ApiModelProperty("课程resource_id")
    private String resourceId;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("方向名称")
    private String directionName;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("知识点")
    private List<String> knowledgeList;

}
