package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yinqingrong
 */
@Data
@ApiModel("六棱图数据")
public class RtksRadarBo implements Serializable {

	/**
	 * 节点编号
	 */
	@ApiModelProperty("节点编号")
	private String firstTreeId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty("节点名称")
	private String treeName;

	/**
	 * 节点值
	 */
	@ApiModelProperty("节点值")
	private Integer matchPercent;
}
