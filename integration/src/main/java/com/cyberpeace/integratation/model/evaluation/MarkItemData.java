package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 标记项目数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
