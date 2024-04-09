package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeNodesRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;

public interface EvalTestInstanceService extends IService<EvalTestInstance> {

	Boolean deleteTestInstance(String instanceId);

	Boolean stopInstance(String testId, String instanceId, String instanceName);

	EvalTaskTimeNodesRsp getTimeNodes(String instanceId);

}
