package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Data;

@Data
public class CourseQueryBo extends CourseContext {

	private Boolean myCourse = true;

	private Boolean systemCourse = true;

	private Boolean shareCourse = true;

	private String userId;

}
