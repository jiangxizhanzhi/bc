package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseInfoBo implements Serializable {

    private String resourceId;

    private String name;

    private String description;

    private String direction;

    private String logo;

    private String courseWriteup;

    private Integer difficulty;

    private String createUser;

    private String lastEditUser;

    private Integer eBook;

    private Integer heat;

    private String lecturer;

    private Integer score;

    private String courseNum;

    private String directionName;

    private Integer lessonCount;

    private Integer courseDuration;

}
