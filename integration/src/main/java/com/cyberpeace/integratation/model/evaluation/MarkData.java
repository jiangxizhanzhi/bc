package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 标记数据
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class MarkData implements Serializable {

	/**
	 * 基准时间
	 */
	private Long baseTime;

	/**
	 * 数据
	 */
	List<MarkItemData> data;
}
