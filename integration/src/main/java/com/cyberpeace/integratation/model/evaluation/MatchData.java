package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 匹配数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MatchData implements Serializable {

	/**
	 * 基准时间
	 */
	private Long baseTime;

	/**
	 * 数据
	 */
	private List<MatchItemData> data;
}
