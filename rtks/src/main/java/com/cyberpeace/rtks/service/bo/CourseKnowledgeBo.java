package com.cyberpeace.rtks.service.bo;

import java.util.List;
import java.io.Serializable;

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

    @ApiModelProperty("课程logo")
    private String logo;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

    @ApiModelProperty("课程时长")
    private Integer durationSum;

    @ApiModelProperty("课时总数")
    private Integer lessonCount;

    @ApiModelProperty("课程学习进度")
    private Integer process;

    @ApiModelProperty("课程关联知识点信息")
    private List<KnowledgeInfoBo> knowledges;
}
