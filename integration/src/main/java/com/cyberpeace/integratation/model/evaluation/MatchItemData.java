package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 匹配项数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
