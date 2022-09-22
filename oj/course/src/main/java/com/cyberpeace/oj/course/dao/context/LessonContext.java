package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LessonContext implements Serializable {

    private Integer type;

    private Integer lessonType;

    private String pattern;

    private List<String> courseIds;

}
