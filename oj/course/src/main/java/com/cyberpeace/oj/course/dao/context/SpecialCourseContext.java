package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpecialCourseContext implements Serializable {

	private Boolean myCourse;

	private Boolean systemCourse;

	private Boolean shareCourse;

	private String userId;

	public Boolean getMyCourse() {
		return null == myCourse || myCourse;
	}

	public Boolean getSystemCourse() {
		return null == systemCourse || systemCourse;
	}

	public Boolean getShareCourse() {
		return null == shareCourse || shareCourse;
	}
}
