package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * 课程背景
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CourseContext extends PatternBase {

	/**
	 * 电子书
	 */
	private Integer eBook;

	/**
	 * 方向
	 */
	private List<String> directions;

	/**
	 * 难度
	 */
	private Integer difficulty;

}
