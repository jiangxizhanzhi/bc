package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 匹配数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
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
