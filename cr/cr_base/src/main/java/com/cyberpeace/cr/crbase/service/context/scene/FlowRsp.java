package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流返回值
 * 包括流量列表
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class FlowRsp implements Serializable {

	@ApiModelProperty(value = "流量数据")
	private List<Flow> data;

	public FlowRsp(List<Flow> data) {
		this.data = data;
	}

	public FlowRsp() {}
}
