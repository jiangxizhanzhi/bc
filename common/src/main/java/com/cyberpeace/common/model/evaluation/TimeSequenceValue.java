package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 时序数据模型
 */
@Data
public class TimeSequenceValue implements Serializable {

	private Integer value;

	/**
	 * yyyy-mm-dd hh:mm:ss
	 */
	private Date time;
}
