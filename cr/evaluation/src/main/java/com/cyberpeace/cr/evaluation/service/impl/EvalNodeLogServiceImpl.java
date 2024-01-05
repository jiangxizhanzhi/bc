package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeOpsReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalNodeLog;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalNodeLogMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalNodeLogService;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import com.cyberpeace.cr.evaluation.service.impl.bo.StageData;
import com.cyberpeace.cr.evaluation.service.impl.bo.TaskBo;
import com.cyberpeace.cr.evaluation.service.impl.bo.ToolBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvalNodeLogServiceImpl extends ServiceImpl<EvalNodeLogMapper, EvalNodeLog> implements EvalNodeLogService {

	@Autowired
	private EvalNodeLogMapper evalNodeLogMapper;

	@Override
	public void logNodeOperate(EvalSceneNodeOpsReq nodeOpsReq, String resourceId, int operate) {
		UserInfo userContext = UserInfoContext.getUserContext();
		EvalNodeLog evalNodeLog = new EvalNodeLog();
		evalNodeLog.setNodeId(nodeOpsReq.getNodeId());
		evalNodeLog.setNodeName(nodeOpsReq.getNodeName());
		evalNodeLog.setTaskId(resourceId);
		evalNodeLog.setCreateUserId(userContext.getUserId());
		evalNodeLog.setOperate(operate);
		evalNodeLogMapper.insert(evalNodeLog);
	}
}
