package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 流信息上下文
 * 流量简要信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class FlowInfoContext implements Serializable {

	private String flowId;

	private String sourceIp;
}
