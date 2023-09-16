package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackTimeCountGroup implements Serializable {

	@ApiModelProperty("攻击时间")
	private String createTime;

	@ApiModelProperty("攻击次数")
	private Integer count;
}
