package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量列表
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowRsp implements Serializable {

	/**
	 * 数据
	 *
	 * @see List<Flow>
	 */
	@ApiModelProperty(value = "流量数据")
	private List<Flow> data;

	public FlowRsp(List<Flow> data) {
		this.data = data;
	}

	public FlowRsp() {}
}
