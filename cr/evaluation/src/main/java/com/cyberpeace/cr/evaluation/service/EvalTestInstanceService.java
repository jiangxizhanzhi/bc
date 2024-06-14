package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeNodesRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;

import java.util.List;

public interface EvalTestInstanceService extends IService<EvalTestInstance> {

	Boolean deleteTestInstance(String instanceId);

	Boolean stopInstance(String testId, String instanceId, String instanceName);

	EvalTaskTimeNodesRsp getTimeNodes(String instanceId);

	List<EvalRoomPairRsp> getNodes(String instanceId);

	Boolean recreateNode(EvalSceneNodeReq nodeReq);

	Boolean restartNode(EvalSceneNodeReq nodeReq);

}
