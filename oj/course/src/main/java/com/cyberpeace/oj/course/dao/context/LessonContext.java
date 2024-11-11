package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

/**
 * 课程背景
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LessonContext extends PatternBase {

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 课程id列表
	 */
	private List<String> courseIds;

}
