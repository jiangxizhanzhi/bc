package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 标记项目数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MarkItemData implements Serializable {

	/**
	 * t
	 */
	private Long t;

	/**
	 * v
	 */
	private String v;

	/**
	 * m
	 */
	private Integer m;
}
