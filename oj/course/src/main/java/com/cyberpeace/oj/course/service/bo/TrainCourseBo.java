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

    @ApiModelProperty("课程学习进度")
    private Integer process;

    @ApiModelProperty("课程关联知识点信息")
    private List<CourseKnowledgeBo> knowledgeList;

    @ApiModelProperty("课程logo")
    private String logo;

    @ApiModelProperty("课程关联的知识点或技能id")
    private String relatedId;

    @ApiModelProperty("是否具有课程访问权限")
    private Boolean interview;

    public static TrainCourseBo getTrainCourseBoFromKnowledge(CourseKnowledgeInfoBo courseKnowledge) {
        TrainCourseBo trainCourseBo = new TrainCourseBo();
        trainCourseBo.setResourceId(courseKnowledge.getResourceId());
        trainCourseBo.setName(courseKnowledge.getName());
        trainCourseBo.setDirectionName(courseKnowledge.getDirectionName());
        trainCourseBo.setDifficulty(courseKnowledge.getDifficulty());
        trainCourseBo.setLessonCount(courseKnowledge.getLessonCount());
        trainCourseBo.setDescription(courseKnowledge.getDescription());
        trainCourseBo.setProcess(courseKnowledge.getProcess());
        trainCourseBo.setLogo(courseKnowledge.getLogo());
        trainCourseBo.setRelatedId(courseKnowledge.getRelatedId());
        trainCourseBo.setInterview(false);
        return trainCourseBo;
    }
}
