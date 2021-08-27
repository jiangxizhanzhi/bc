package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;

import lombok.Data;

@Data
public class KnowledgeRes implements Serializable {

	private String resourceId;

	private String categoryId;

	private String category;

	private String content;

	private String courses;

	private Integer weight;

	private List<RelatedCourse> courseList;

	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}