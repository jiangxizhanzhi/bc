package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalGuideScheme;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalGuideMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
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
import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private SituationGuideHisMapper situationGuideHisMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

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
	public List<EvalGuideListRsp> getGuideList(String instanceId) {
		return evalGuideMapper.selectListByInstanceId(instanceId);
	}

	@Override
	public void	runGuideScheme(String guideId) {
		EvalGuideScheme evalGuideScheme = evalGuideMapper.selectById(guideId);
		String sceneId = evalGuideMapper.selectSceneIdByGuideId(guideId);
		pushDataToRoomByScheme(true, sceneId, evalGuideScheme);
		addHistory(evalGuideScheme, sceneId);
	}

	@Override
	public void deleteGuideScheme(String guideId) {
		evalGuideMapper.deleteById(guideId);
	}

	@Override
	public List<EvalStepGuideListRsp> getStepGuideList(String instanceId) {
		return evalInstanceStepMapper.selectStepGuideListByInstanceId(instanceId);
	}

	@Override
	public EvalStepGuideDetailRsp getGuideDetail(String stepId) {
		return evalInstanceStepMapper.selectGuideDetailByStepId(stepId);
	}

	@Override
	public void deleteByStepId(String stepId) {
		evalGuideMapper.deleteByStepId(stepId);
	}

	@Override
	public List<EvalGuideAttackEnumRsp> getAttackEnum() {
		List<EvalGuideAttackEnumRsp> attackEnumRsps = new ArrayList<>();
		for (SituationGuideScheme.AttackUrlEnum modelEnum : SituationGuideScheme.AttackUrlEnum.values()) {
			EvalGuideAttackEnumRsp enumRsp = new EvalGuideAttackEnumRsp(modelEnum.getCode(), modelEnum.getMsg(), modelEnum.getDesc(), modelEnum.getSuccessUrl(), modelEnum.getType());
			attackEnumRsps.add(enumRsp);
		}
		return attackEnumRsps;
	}

	@Override
	public EvalTestElementAreaRsp getElementArea(String testId) {
		EvalTestElementAreaRsp evalTestElementAreaRsp = new EvalTestElementAreaRsp();
		SceneData sceneData;
		String sceneId = evalTestMapper.selectSceneIdByTestId(testId);
		if (StringUtils.isNotEmpty(sceneId)) {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		} else  {
			String topoId = evalTestMapper.selectTopoIdByTestId(testId);
			sceneData = topoHandlerService.getSceneData(null, topoId);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
		List<ElementType> elements = elementInfoList.stream().map(info -> new ElementType(info.getElementId(), info.getName())).collect(Collectors.toList());
		evalTestElementAreaRsp.setElementList(elements);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas().values());
		List<ElementType> areas = areaInfoList.stream().map(info -> new ElementType(info.getAreaId(), info.getName())).collect(Collectors.toList());
		evalTestElementAreaRsp.setAreaList(areas);
		return evalTestElementAreaRsp;
	}

	@Override
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

	private void addHistory(EvalGuideScheme scheme, String sceneId) {
		SituationGuideHistory history = new SituationGuideHistory();
		history.setRole(null);
		history.setTarget(scheme.getTarget());
		history.setSource(scheme.getSource());
		history.setResult(scheme.getResult());
		history.setEventName(scheme.getName());
		history.setEventType(SituationGuideScheme.EventType.ATTACK.getCode());
		history.setCategory(scheme.getAttackType());
		history.setSceneId(sceneId);
		history.setGuideId(scheme.getResourceId());
		history.setType(SituationGuideScheme.Type.MANUALLY.getCode());
		history.setResult(scheme.getResult());
		history.setBusinessType(5);
		history.setMissionId(null);
		history.setTaskId(null);
		history.setTriggerCondition(null);
		history.setRoomId(scheme.getTestId());
		situationGuideHisMapper.insert(history);
	}

	private List<String> getElementIds(String sceneId, Boolean isInstance) {

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
