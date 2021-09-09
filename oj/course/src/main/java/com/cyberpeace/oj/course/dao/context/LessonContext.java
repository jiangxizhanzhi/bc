package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class LessonContext implements Serializable {

    private Integer type;

    private String courseId;

    private String pattern;
}
