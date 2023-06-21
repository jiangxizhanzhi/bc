package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

@Data
public class CourseKnowledgeInfoBo {

    private String resourceId;

    private String name;

    private String directionName;

    private Integer difficulty;

    private Integer lessonCount;

    private String description;

    private Integer process;

    private String relatedId;

    private String logo;

    private String content;


    public TrainCourseBo getTrainCourseBoFromKnowledge() {
        TrainCourseBo trainCourseBo = new TrainCourseBo();
        return trainCourseBo;
    }
}
