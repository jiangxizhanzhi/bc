package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 流量列表
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 数据
	 */
	@ApiModelProperty(value = "流量数据")
	private List<Flow> data;

	public FlowRsp(List<Flow> data) {
		this.data = data;
	}

	public FlowRsp() {}
}
