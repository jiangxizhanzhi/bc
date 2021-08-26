package com.cyberpeace.oj.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;

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

}