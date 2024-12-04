package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * 课程背景
 *
 * @author SN
 * @version 1.0.0
 * @see PatternBase
 * @since 1.0.0@date 2024/11/08
 */
@Data
public class CourseContext extends PatternBase {

	/**
	 * 电子书
	 *
	 * @see Integer
	 */
	private Integer eBook;

	/**
	 * 方向
	 *
	 * @see List<String>
	 */
	private List<String> directions;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

}
