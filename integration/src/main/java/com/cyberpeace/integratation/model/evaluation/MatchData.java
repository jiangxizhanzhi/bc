package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 匹配数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MatchData implements Serializable {

	/**
	 * 基准时间
	 *
	 * @see Long
	 */
	private Long baseTime;

	/**
	 * 数据
	 *
	 * @see List<MatchItemData>
	 */
	private List<MatchItemData> data;
}
