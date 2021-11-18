package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CourseContext implements Serializable {

	private Integer eBook;

	private List<String> directions;

	private Integer difficulty;

	private String pattern;

}
