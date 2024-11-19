package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台流量趋势
 *
 * @author SN
 * @version 1.0.0
 * @see Serializable
 * @since 1.0.0@date 2024/11/14
 */
@Data
public class MpFlowTrend implements Serializable {

	/**
	 * 流量大小
	 *
	 * @see Integer
	 */
	@ApiModelProperty("流量大小")
	private Integer dataSize;

	/**
	 * 时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("时间")
	private Date time;

}
