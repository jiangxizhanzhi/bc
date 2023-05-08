package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ip数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class IpBo implements Serializable {

	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "IP")
	private String ip;

}
