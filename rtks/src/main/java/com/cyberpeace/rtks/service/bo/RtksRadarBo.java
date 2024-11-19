package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RTKS雷达Bo
 *
 * @author yinqingrong@see Serializable
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@ApiModel("六棱图数据")
public class RtksRadarBo implements Serializable {

	/**
	 * 节点编号
	 *
	 * @see String
	 */
	@ApiModelProperty("节点编号")
	private String firstTreeId;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("节点名称")
	private String treeName;

	/**
	 * 节点值
	 *
	 * @see Integer
	 */
	@ApiModelProperty("节点值")
	private Integer matchPercent;
}
