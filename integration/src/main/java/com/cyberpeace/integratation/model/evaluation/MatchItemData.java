package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 匹配项数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MatchItemData implements Serializable {

	/**
	 * t
	 */
	private Long t;

	/**
	 * v
	 */
	private String v;
}
