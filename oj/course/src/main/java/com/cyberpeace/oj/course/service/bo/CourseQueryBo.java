package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Data;

@Data
public class CourseQueryBo extends CourseContext {

	private Boolean myCourse;

	private Boolean systemCourse;

	private Boolean shareCourse;

	private String userId;

	private Integer role;

}
