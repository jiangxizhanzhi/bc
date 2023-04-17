package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
