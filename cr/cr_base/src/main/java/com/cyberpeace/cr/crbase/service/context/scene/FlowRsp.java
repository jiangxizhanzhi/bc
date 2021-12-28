package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 流量列表
 */
@Data
public class FlowRsp implements Serializable {

	private List<Flow> data;
}
