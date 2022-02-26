package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("IP攻击统计")
public class MpAttackIPCount implements Serializable {

	@ApiModelProperty("发起攻击IP||受攻击IP")
	private String ip;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("攻击次数")
	private Integer times;

}
