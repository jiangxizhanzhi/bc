package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 类别新增博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CategoryAddBo implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 父母id
	 */
	private String parentId;

	/**
	 * 课程类型
	 */
	private Integer courseType;

}
