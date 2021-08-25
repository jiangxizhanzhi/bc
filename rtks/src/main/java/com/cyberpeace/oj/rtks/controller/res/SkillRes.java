package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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