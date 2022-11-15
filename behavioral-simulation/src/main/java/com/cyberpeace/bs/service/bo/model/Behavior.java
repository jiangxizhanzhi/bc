package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 访问行为
 */
@Data
public class Behavior extends BehaviorInput implements Serializable {

	@ApiModelProperty(value = "行为名称")
	private String name;

	@ApiModelProperty(value = "设备类型")
	private Integer accessOs;

	@ApiModelProperty(value = "网络模型")
	private Integer network;

	@ApiModelProperty(value = "行为耗时（min）")
	private Integer timeCost;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建者id")
	private String createId;

	@ApiModelProperty(value = "创建者名称")
	private String creatorName;

}
