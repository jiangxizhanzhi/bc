package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;

import lombok.Data;

@Data
public class SkillRes implements Serializable {

	private String system;

	private String category;

	private String resourceId;

	private String systemId;

	private String categoryId;

	private String content;

	private String courses;

	private List<RelatedCourse> courseList;

	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}