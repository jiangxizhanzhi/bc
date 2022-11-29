package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

@Data
public class CourseContext extends PatternBase {

	private Integer eBook;

	private List<String> directions;

	private Integer difficulty;

}
