package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程配置 dump bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class LessonConfigDumpBo implements Serializable {

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	private Integer index;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 节点类型
	 *
	 * @see Integer
	 */
	private Integer nodeType;

	/**
	 * 子级
	 *
	 * @see List<LessonConfigDumpBo>
	 */
	private List<LessonConfigDumpBo> children;

}
