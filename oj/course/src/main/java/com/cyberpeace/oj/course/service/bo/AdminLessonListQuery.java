package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLessonListQuery implements Serializable {

    private Integer type;

    private String categoryId;

    private String courseId;

    private String exceptCourse;

    private String pattern;

}
