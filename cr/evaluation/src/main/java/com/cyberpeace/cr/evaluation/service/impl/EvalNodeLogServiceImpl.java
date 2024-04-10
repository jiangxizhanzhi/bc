package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeOpsReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalNodeLog;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalNodeLogMapper;
import com.cyberpeace.cr.evaluation.service.EvalNodeLogService;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EvalNodeLogServiceImpl extends ServiceImpl<EvalNodeLogMapper, EvalNodeLog> implements EvalNodeLogService {

	@Autowired
	private EvalNodeLogMapper evalNodeLogMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Override
	public void logNodeOperate(EvalSceneNodeOpsReq nodeOpsReq, String resourceId, int operate) {
		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(nodeOpsReq.getMpSceneId(), true);
		Map<String, ElementInfo> elements = situationTopo.getElements();
		ElementInfo elementInfo = elements.get(nodeOpsReq.getNodeId());
		UserInfo userContext = UserInfoContext.getUserContext();
		EvalNodeLog evalNodeLog = new EvalNodeLog();
		evalNodeLog.setNodeId(nodeOpsReq.getNodeId());
		evalNodeLog.setNodeName(elementInfo.getName());
		evalNodeLog.setTaskId(resourceId);
		evalNodeLog.setCreateUserId(userContext.getUserId());
		evalNodeLog.setOperate(operate);
		evalNodeLogMapper.insert(evalNodeLog);
	}
}
