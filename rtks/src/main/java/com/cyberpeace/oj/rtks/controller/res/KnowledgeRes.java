package com.cyberpeace.oj.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;

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
}