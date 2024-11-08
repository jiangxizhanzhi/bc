package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台流趋势
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MpFlowTrend implements Serializable {

	/**
	 * 流量大小
	 */
	@ApiModelProperty("流量大小")
	private Integer dataSize;

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private Date time;

}
