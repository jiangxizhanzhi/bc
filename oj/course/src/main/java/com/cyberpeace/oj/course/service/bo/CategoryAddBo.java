package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 类别新增博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class CategoryAddBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	private Integer courseType;

}
