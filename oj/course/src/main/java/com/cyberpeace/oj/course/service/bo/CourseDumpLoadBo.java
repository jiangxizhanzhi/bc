package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CourseDumpLoadBo implements Serializable {

	private String name;

	private String description;

	private String categoryName;

	private String logo;

	private String outline;

	private Integer difficulty;

	private String createUserId;

	private String modifyUserId;

	private Boolean isPublic;

	private Integer courseType;

	private String lecturer;

	private Integer score;

	private String courseNum;



}
