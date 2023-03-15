package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LcBehavior extends Behavior implements Serializable {

	@ApiModelProperty(value = "行为依赖")
	private List<String> application = Lists.newArrayList();

	@ApiModelProperty(value = "是否实体")
	private Integer isActual;

	@ApiModelProperty(value = "客户端类型，type=45")
	private Integer appType;

}
