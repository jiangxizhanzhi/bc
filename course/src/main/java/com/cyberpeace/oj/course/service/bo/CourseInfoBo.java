package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import com.cyberpeace.oj.course.dao.entity.Course;

import lombok.Data;

@Data
public class CourseInfoBo extends Course implements Serializable {

    private String directionName;

    private Integer lessonCount;

    private Integer courseDuration;

}
