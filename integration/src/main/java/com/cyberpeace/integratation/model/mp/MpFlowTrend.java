package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MpFlowTrend implements Serializable {

	/**
	 * 流量大小
	 */
	private Integer dataSize;

	/**
	 * 时间
	 */
	private Date time;

}
