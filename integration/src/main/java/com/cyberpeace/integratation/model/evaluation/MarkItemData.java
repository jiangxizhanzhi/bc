package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 标记项目数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MarkItemData implements Serializable {

	/**
	 * t
	 *
	 * @see Long
	 */
	private Long t;

	/**
	 * v
	 *
	 * @see String
	 */
	private String v;

	/**
	 * m
	 *
	 * @see Integer
	 */
	private Integer m;
}
