package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程配置 dump bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LessonConfigDumpBo implements Serializable {

	/**
	 * 指数
	 */
	private Integer index;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 子级
	 */
	private List<LessonConfigDumpBo> children;

}
