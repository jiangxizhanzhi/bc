package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalGuideScheme;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalGuideMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestMapper;
import com.cyberpeace.cr.evaluation.service.EvalGuideService;
import com.cyberpeace.situation.constant.SituationGuideScheme;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeEffect;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalGuideServiceImpl extends ServiceImpl<EvalGuideMapper, EvalGuideScheme> implements EvalGuideService {

	@Autowired
	private EvalGuideMapper evalGuideMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private EvalTestMapper evalTestMapper;

	@Autowired
	private EvalStepMapper evalStepMapper;

	@Override
	public String addSituationGuide(EvalGuideAddReq req) {
		EvalGuideScheme evalGuideScheme = BeanUtils.copy(req, EvalGuideScheme.class);
		evalGuideMapper.insert(evalGuideScheme);
		return evalGuideScheme.getResourceId();
	}

	@Override
	public void modifySituationGuide(EvalGuideModifyReq req) {
		EvalGuideScheme evalGuideScheme = BeanUtils.copy(req, EvalGuideScheme.class);
		evalGuideScheme.setResourceId(req.getGuideId());
		evalGuideMapper.updateById(evalGuideScheme);
	}

	@Override
	public List<EvalGuideListRsp> getGuideList(String testId) {
		return evalGuideMapper.selectListByTestId(testId);
	}

	@Override
	public void	runGuideScheme(String guideId) {
		EvalGuideScheme evalGuideScheme = evalGuideMapper.selectById(guideId);
		String sceneId = evalGuideMapper.selectSceneIdByGuideId(guideId);
		if (sceneId == null) {
			String topoId = evalTestMapper.selectTopoIdByTestId(evalGuideScheme.getTestId());
			if (topoId == null) {
				throw new BusinessException(CrBusinessErrorCode.CANNOT_FIND_TOPO);
			}
			pushDataToRoomByScheme(false, topoId, evalGuideScheme);
		}else {
			pushDataToRoomByScheme(true, sceneId, evalGuideScheme);
		}
	}

	@Override
	public void deleteGuideScheme(String guideId) {
		evalGuideMapper.deleteById(guideId);
	}

	@Override
	public List<EvalStepGuideListRsp> getStepGuideList(String testId) {
		EvalTest evalTest = evalTestMapper.selectById(testId);
		if (evalTest == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		List<String> stepIds = evalTest.getStepIdList();
		if (CollectionUtils.isEmpty(stepIds)) {
			return new ArrayList<>();
		}
		return evalStepMapper.selectStepGuideListByStepIds(stepIds);
	}

	@Override
	public EvalStepGuideDetailRsp getGuideDetail(String stepId) {
		return evalStepMapper.selectGuideDetailByStepId(stepId);
	}

	@Override
	public void deleteByStepId(String stepId) {
		evalGuideMapper.deleteByStepId(stepId);
	}

	public void pushDataToRoomByScheme(Boolean isInstance, String sceneId, EvalGuideScheme evalGuideScheme) {
		WsMultiNodeEffect wsEffect = new WsMultiNodeEffect();
		List<String> elementIds = getElementIds(sceneId, isInstance);
		wsEffect.setSceneId(sceneId);
		wsEffect.setCategory(evalGuideScheme.getAttackType());
		wsEffect.setEventType(SituationGuideScheme.EventType.ATTACK.getCode());
		wsEffect.setName(evalGuideScheme.getName());
		wsEffect.setSourceId(evalGuideScheme.getSource());
		wsEffect.setTargetId(evalGuideScheme.getTarget());
		wsEffect.setResult(evalGuideScheme.getResult());

		wsEffect.setDuration(3);
		LineStyle lineStyle = new LineStyle();
		lineStyle.setLineType("straight");
		lineStyle.setColor("#FF0000");
		lineStyle.setWidth(3);
		wsEffect.setLineStyle(lineStyle);

		WsMessage<WsMultiNodeEffect> wsMessage;
		if (elementIds.contains(evalGuideScheme.getTarget())) {
			wsMessage = new WsMessage<>(MessageType.DirectorSchedulingEE.getDesc(), wsEffect);
		} else {
			wsMessage = new WsMessage<>(MessageType.DirectorSchedulingEA.getDesc(), wsEffect);
		}
		situationHandlerService.pushDataToRoom(wsMessage, wsEffect.getSceneId());
	}

	public List<String> getElementIds(String sceneId, Boolean isInstance) {

		SceneData sceneData;
		if (isInstance != null && !isInstance) {
			sceneData = topoHandlerService.getSceneData(null, sceneId);
		} else {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
		return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
	}

}
