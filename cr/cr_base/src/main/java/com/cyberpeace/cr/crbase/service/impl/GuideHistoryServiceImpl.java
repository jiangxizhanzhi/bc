package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.service.context.guide.MpEvent;
import com.cyberpeace.cr.crbase.service.context.guide.ScreenBo;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import com.cyberpeace.integratation.model.scene.AttackScreenContext;
import com.cyberpeace.integratation.model.scene.SceneEventContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.PortInfo;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class GuideHistoryServiceImpl extends ServiceImpl<SituationGuideHisMapper, SituationGuideHistory> implements GuideHistoryService {

	@Autowired
	private SituationGuideHisMapper situationGuideHisMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Override
	public PlaybackOptionRsp getAllOption(String roomId) {
		PlaybackOptionRsp playbackOptionRsp = new PlaybackOptionRsp();
		playbackOptionRsp.setAttackEventNames(situationGuideHisMapper.getEventNameList(roomId, SituationGuideScheme.EventType.ATTACK.getCode()));
		playbackOptionRsp.setDefenseEventNames(situationGuideHisMapper.getEventNameList(roomId, SituationGuideScheme.EventType.DEFENSE.getCode()));
		playbackOptionRsp.setBlockingEventNames(situationGuideHisMapper.getEventNameList(roomId, SituationGuideScheme.EventType.BLOCKING.getCode()));
		playbackOptionRsp.setBelongingEventNames(situationGuideHisMapper.getEventNameList(roomId, SituationGuideScheme.EventType.BELONGING.getCode()));
		playbackOptionRsp.setAttackType(Arrays.stream(SituationGuideScheme.AttackUrlEnum.values())
											  .map(attackUrlEnum -> new CodeNameRsp(attackUrlEnum.getCode(), attackUrlEnum.getName()))
											  .collect(Collectors.toList()));
		playbackOptionRsp.setDefenseType(Arrays.stream(SituationGuideScheme.DefenseUrlEnum.values())
											  .map(defenseUrlEnum -> new CodeNameRsp(defenseUrlEnum.getCode(), defenseUrlEnum.getName()))
											  .collect(Collectors.toList()));
		playbackOptionRsp.setResult(Arrays.stream(SituationGuideScheme.ResultType.values())
											  .map(resultType -> new CodeNameRsp(resultType.getCode(), resultType.getDesc()))
											  .collect(Collectors.toList()));
		playbackOptionRsp.setRoleType(Arrays.stream(SituationGuideScheme.RoleType.values())
										  .map(roleType -> new CodeNameRsp(roleType.getCode(), roleType.getDesc()))
										  .collect(Collectors.toList()));
		return playbackOptionRsp;
	}

	@Override
	public List<GuideHistoryRsp> getRecord(ScreenGuideHistoryReq screenGuideHistoryReq) {
		if (CollectionUtils.isEmpty(screenGuideHistoryReq.getEventSubTypeList())) {
			return new ArrayList<>();
		}
		List<SituationGuideHistory> situationGuideHistoryList = new ArrayList<>();

		ScreenBo attackScreenBo = screenGuideHistoryReq.getAttackScreenBo();
		ScreenBo defenseScreenBo = screenGuideHistoryReq.getDefenseScreenBo();
		ScreenBo blockingScreenBo = screenGuideHistoryReq.getBlockingScreenBo();
		ScreenBo belongingScreenBo = screenGuideHistoryReq.getBelongingScreenBo();
		//攻击
		if (attackScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.ATTACK.getCode(), TimeUtil.toDate1(attackScreenBo.getStartTime()),
					TimeUtil.toDate1(attackScreenBo.getEndTime()), attackScreenBo.getEventName(), attackScreenBo.getCategory(),
					attackScreenBo.getSource(), attackScreenBo.getTarget(), attackScreenBo.getResult(), null));
		}
		//防御
		if (defenseScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.DEFENSE.getCode(), TimeUtil.toDate1(defenseScreenBo.getStartTime()),
					TimeUtil.toDate1(defenseScreenBo.getEndTime()), defenseScreenBo.getEventName(), defenseScreenBo.getCategory(),
					defenseScreenBo.getSource(), defenseScreenBo.getTarget(), defenseScreenBo.getResult(), null));
		}
		//阻断
		if (blockingScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.BLOCKING.getCode(), TimeUtil.toDate1(blockingScreenBo.getStartTime()),
					TimeUtil.toDate1(blockingScreenBo.getEndTime()), blockingScreenBo.getEventName(), null,
					blockingScreenBo.getSource(), blockingScreenBo.getTarget(), null, null));
		}
		//归属
		if (belongingScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.BELONGING.getCode(), TimeUtil.toDate1(belongingScreenBo.getStartTime()),
					TimeUtil.toDate1(belongingScreenBo.getEndTime()), belongingScreenBo.getEventName(), null,
					belongingScreenBo.getSource(), null, null, belongingScreenBo.getRole()));
		}

		//处理成前端展示类型
		List<GuideHistoryRsp> guideHistoryRspList = new ArrayList<>();
		Map<String, Map<String, ElementInfo>> sceneElementMap = new HashMap<>();
		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			//获取场景内的节点信息
			Map<String, ElementInfo> elementInfoMap = sceneElementMap.get(situationGuideHistory.getSceneId());
			if (elementInfoMap == null) {
				SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(situationGuideHistory.getSceneId(), true);
				elementInfoMap = situationTopo.getElements();
				sceneElementMap.put(situationGuideHistory.getSceneId(), elementInfoMap);
			}
			//获取记录攻防类型
			String category = null;
			if (SituationGuideScheme.EventType.ATTACK.getCode().equals(situationGuideHistory.getEventType())) {
				category = SituationGuideScheme.AttackUrlEnum.getEnumByCode(situationGuideHistory.getCategory()).getName();
			} else if (SituationGuideScheme.EventType.DEFENSE.getCode().equals(situationGuideHistory.getEventType())) {
				category = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(situationGuideHistory.getCategory()).getName();
			}
			//获取事件名称、触发类型、动作类型
			GuideHistoryRsp guideHistoryRsp = null;
			if (category != null) {
				guideHistoryRsp = new GuideHistoryRsp(situationGuideHistory.getEventName(),
						EnumUtils.getEnumDescByCode(situationGuideHistory.getType(), SituationGuideScheme.Type.class),
						EnumUtils.getEnumDescByCode(situationGuideHistory.getEventType(), SituationGuideScheme.EventType.class),
						Arrays.asList(category));
			} else {
				guideHistoryRsp = new GuideHistoryRsp(situationGuideHistory.getEventName(),
						EnumUtils.getEnumDescByCode(situationGuideHistory.getType(), SituationGuideScheme.Type.class),
						EnumUtils.getEnumDescByCode(situationGuideHistory.getEventType(), SituationGuideScheme.EventType.class),
						null);
			}
			//获取源节点名称、ip
			if (StringUtils.isNotEmpty(situationGuideHistory.getSource())) {
				guideHistoryRsp.setSourceName(elementInfoMap.get(situationGuideHistory.getSource()).getName());
				guideHistoryRsp.setSourceIp(getElementIp(elementInfoMap.get(situationGuideHistory.getSource())));
			}
			//获取目标节点名称、ip
			if (StringUtils.isNotEmpty(situationGuideHistory.getTarget())) {
				guideHistoryRsp.setSourceName(elementInfoMap.get(situationGuideHistory.getSource()).getName());
				guideHistoryRsp.setSourceIp(getElementIp(elementInfoMap.get(situationGuideHistory.getSource())));
			}
			guideHistoryRspList.add(guideHistoryRsp);
		}

		//获取中台数据
		SceneEventContext sceneEventContext = new SceneEventContext();
		//默认返回前1000条数据
		sceneEventContext.setPage(1);
		sceneEventContext.setPageSize(1000);
		//获取该训练过程中的所有场景id
		String sceneIdsRecord = situationGuideHisMapper.getSceneIdsByRoom(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getBusinessType());
		List<String> sceneIds = JsonUtil.toJsonObjectArray(sceneIdsRecord, String.class);
		sceneEventContext.setSceneId(StringUtils.join(sceneIds, ","));
		//是否查询流量事件
		sceneEventContext.setFlow(screenGuideHistoryReq.getEventSubTypeList()
													   .contains(SituationGuideScheme.Type.FLOW.getCode()));
		//是否查询主机事件
		sceneEventContext.setHost(screenGuideHistoryReq.getEventSubTypeList()
													   .contains(SituationGuideScheme.Type.HOST.getCode()));
		//是否筛选攻击
		if (attackScreenBo == null) {
			sceneEventContext.setAttack(null);
		} else {
			sceneEventContext.setAttack(screenToAttackScreenContext(attackScreenBo));
		}
		//是否筛选防御
		if (defenseScreenBo == null) {
			sceneEventContext.setDefense(null);
		} else {
			sceneEventContext.setDefense(screenToAttackScreenContext(defenseScreenBo));
		}
		//中台无阻断和归属事件，不做处理

		//处理成前端展示类型
		JSONArray mpEventArray = remoteSceneService.getEventList(sceneEventContext);
		List<MpEvent> mpEventList = mpEventArray.toJavaList(MpEvent.class);
		for (MpEvent mpEvent : mpEventList) {
			GuideHistoryRsp guideHistoryRsp = new GuideHistoryRsp();
			guideHistoryRsp.setEventName(mpEvent.getEventName());
			guideHistoryRsp.setTriggerType(MpEvent.TriggerType.getMsgByCode(mpEvent.getTriggerType()));
			guideHistoryRsp.setActionType(MpEvent.AttackType.getMsgByCode(mpEvent.getActionType()));
			guideHistoryRsp.setCategory(mpEvent.getAttCkNames());
			guideHistoryRsp.setSourceName(mpEvent.getSourceNodeName());
			guideHistoryRsp.setSourceIp(mpEvent.getSourceNodeIp());
			guideHistoryRsp.setSourcePort(mpEvent.getSourceNodePort());
			guideHistoryRsp.setTargetName(mpEvent.getTargetNodeName());
			guideHistoryRsp.setTargetIp(mpEvent.getTargetNodeIp());
			guideHistoryRsp.setTargetPort(mpEvent.getTargetNodePort());
			guideHistoryRspList.add(guideHistoryRsp);
		}
		return guideHistoryRspList;
	}

	@Override
	public List<String> getEventTimeList(String roomId, Integer businessType) {
		//获取该训练过程中的所有场景id
		String sceneIdsRecord = situationGuideHisMapper.getSceneIdsByRoom(roomId, businessType);
		List<String> sceneIds = JsonUtil.toJsonObjectArray(sceneIdsRecord, String.class);
		JSONArray eventTimeArray = remoteSceneService.getEventTimeList(StringUtils.join(sceneIds, ","));
		List<String> eventTimes = eventTimeArray.toJavaList(JSONObject.class).stream().map(eventTime -> eventTime.getString("time")).collect(Collectors.toList());
		eventTimes.addAll(situationGuideHisMapper.getEventTimes(roomId));
		return eventTimes;
	}

	//获取节点ip
	private String getElementIp(ElementInfo elementInfo) {
		List<PortInfo> portInfoList = new ArrayList<>(elementInfo.getPorts().values());
		for (PortInfo portInfo : portInfoList) {
			if (StringUtils.isNotEmpty(portInfo.getIp())) {
				return portInfo.getIp();
			}
		}
		return "";
	}

	//转化攻击、防御的筛选条件
	private AttackScreenContext screenToAttackScreenContext(ScreenBo screenBo) {
		AttackScreenContext screenContext = new AttackScreenContext();
		screenContext.setStartTime(screenBo.getStartTime());
		screenContext.setEndTime(screenBo.getEndTime());
		// todo 与中台类型对接
		screenContext.setDefenseTypes(null);
		if (screenBo.getEventName() != null) {
			screenContext.setEventName(Collections.singletonList(screenBo.getEventName()));
		} else {
			screenContext.setEventName(null);
		}
		screenContext.setSourceNodeIds(Collections.singletonList(screenBo.getSource()));
		screenContext.setTargetNodeIds(Collections.singletonList(screenBo.getTarget()));
		return screenContext;
	}

}
