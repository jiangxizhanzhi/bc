package com.cyberpeace.bs.service.bo.behavior;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为客户端类型分布
 * @author SN
 * @date 2023/12/21
 */
@Data
public class ClientDistribution {

	@ApiModelProperty(value = "行为总数")
	private Integer total;

	@ApiModelProperty(value = "客户端类型")
	private List<String> clientTypes = Lists.newArrayList();

	@ApiModelProperty(value = "计数")
	private List<Integer> countList = Lists.newArrayList();

}
