package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

/**
 * 课时上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PatternBase
 * @since 1.0.0
 */
@Data
public class LessonContext extends PatternBase {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 课程id列表
	 *
	 * @see List<String>
	 */
	private List<String> courseIds;

}
