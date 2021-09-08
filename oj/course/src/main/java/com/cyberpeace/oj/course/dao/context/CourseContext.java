package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class CourseContext implements Serializable {

    private Integer eBook;

    private String direction;

    private Integer difficulty;

    private String pattern;

    private String sort;

    private Boolean isPublic;

    private Boolean myCourse;

    private Boolean systemCourse;

    private Boolean shareCourse;

    private String userId;

    private String likeUser;

    public void isWeb() {
        this.isPublic = true;
    }
}
