package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id重量
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class IdWeight implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("id, 编号")
	private String resourceId;

	/**
	 * 重量
	 */
	@ApiModelProperty("权重")
	private Integer weight;
}
