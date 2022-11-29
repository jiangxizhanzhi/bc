package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

@Data
public class AdminLessonListQuery extends PatternBase {

    private Integer type;

    private String categoryId;

    private String courseId;

    private String exceptCourse;

}
