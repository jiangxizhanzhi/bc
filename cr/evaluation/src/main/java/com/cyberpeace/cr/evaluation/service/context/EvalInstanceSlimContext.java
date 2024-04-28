package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

@Data
public class EvalInstanceSlimContext {

	private String instanceId;

	private String instanceName = "当前测试实例";

	private String mpSceneId;

}
