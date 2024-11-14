package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课时配置导出Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class LessonConfigDumpBo implements Serializable {

	/**
	 * 编号
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
