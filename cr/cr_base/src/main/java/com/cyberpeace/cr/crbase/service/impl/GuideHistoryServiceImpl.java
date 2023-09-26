package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.AttackCodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.context.guide.MpEvent;
import com.cyberpeace.cr.crbase.service.context.guide.ScreenBo;
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
	private SituationGuideService situationGuideService;

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
											  .map(attackUrlEnum -> new AttackCodeNameRsp(attackUrlEnum.getCode(), attackUrlEnum.getMsg(), attackUrlEnum.getType()))
											  .collect(Collectors.toList()));
		playbackOptionRsp.setDefenseType(Arrays.stream(SituationGuideScheme.DefenseUrlEnum.values())
											  .map(defenseUrlEnum -> new CodeNameRsp(defenseUrlEnum.getCode(), defenseUrlEnum.getMsg()))
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

		//设置过滤条件中的开始时间、结束时间
		String startTime = screenGuideHistoryReq.getStartTime();
		String endTime = screenGuideHistoryReq.getEndTime();
		setScreenBoTime(attackScreenBo, startTime, endTime);
		setScreenBoTime(defenseScreenBo, startTime, endTime);
		setScreenBoTime(blockingScreenBo, startTime, endTime);
		setScreenBoTime(belongingScreenBo, startTime, endTime);
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
				category = SituationGuideScheme.AttackUrlEnum.getEnumByCode(situationGuideHistory.getCategory()).getMsg();
			} else if (SituationGuideScheme.EventType.DEFENSE.getCode().equals(situationGuideHistory.getEventType())) {
				category = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(situationGuideHistory.getCategory()).getMsg();
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
			guideHistoryRsp.setCategoryCode(Collections.singletonList(situationGuideHistory.getCategory()));
			guideHistoryRsp.setActionCode(situationGuideHistory.getEventType());
			guideHistoryRsp.setTriggerCode(situationGuideHistory.getType());
			guideHistoryRsp.setResult(situationGuideHistory.getResult());
			guideHistoryRsp.setRole(situationGuideHistory.getRole());
			//获取源节点名称、ip
			if (StringUtils.isNotEmpty(situationGuideHistory.getSource())) {
				guideHistoryRsp.setSourceName(elementInfoMap.get(situationGuideHistory.getSource()).getName());
				guideHistoryRsp.setSourceIp(getElementIp(elementInfoMap.get(situationGuideHistory.getSource())));
				guideHistoryRsp.setSourceId(situationGuideHistory.getSource());
			}
			//获取目标节点名称、ip
			if (StringUtils.isNotEmpty(situationGuideHistory.getTarget())) {
				guideHistoryRsp.setTargetName(elementInfoMap.get(situationGuideHistory.getSource()).getName());
				guideHistoryRsp.setTargetIp(getElementIp(elementInfoMap.get(situationGuideHistory.getSource())));
				guideHistoryRsp.setTargetId(situationGuideHistory.getTarget());
			}
			guideHistoryRspList.add(guideHistoryRsp);
		}

		String topoId = null;
		topoId = situationGuideHisMapper.getTopoIdByRoom(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getBusinessType());
		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			situationGuideHistory.setSceneId(topoId);
			situationGuideService.pushDataToRoomByGuide(false, situationGuideHistory);
		}

		//获取中台数据,处理数据
		List<MpEvent> mpEventList = situationGuideService.getMpEventList(screenGuideHistoryReq);

		//推送动效
		Map<String, Integer> attCk = Arrays.stream(SituationGuideScheme.AttackUrlEnum.values())
										   .collect(Collectors.toMap(SituationGuideScheme.AttackUrlEnum::getMpCode, SituationGuideScheme.AttackUrlEnum::getCode));
		attCk.putAll(Arrays.stream(SituationGuideScheme.DefenseUrlEnum.values())
						   .collect(Collectors.toMap(SituationGuideScheme.DefenseUrlEnum::getMpCode, SituationGuideScheme.DefenseUrlEnum::getCode)));

		for (MpEvent mpEvent : mpEventList) {
			for (String category : mpEvent.getAttCks()) {
				SituationGuideHistory situationGuideHistory = new SituationGuideHistory();
				situationGuideHistory.setSceneId(topoId);
				situationGuideHistory.setCategory(attCk.get(category));
				situationGuideHistory.setEventName(mpEvent.getEventName());
				situationGuideHistory.setEventType(SituationGuideScheme.EventType.getEventTypeByMpCode(mpEvent.getActionType()).getCode());
				situationGuideHistory.setSource(mpEvent.getSourceNodeId());
				situationGuideHistory.setTarget(mpEvent.getTargetNodeId());
				//中台没有攻击结果和归属，赋一个默认值
				situationGuideHistory.setResult(SituationGuideScheme.ResultType.SUCCESS.getCode());
				situationGuideHistory.setRole(SituationGuideScheme.RoleType.RED.getCode());
				situationGuideService.pushDataToRoomByGuide(false, situationGuideHistory);
			}
		}

		//处理成前端展示
		guideHistoryRspList.addAll(situationGuideService.mpEventToGuideHistoryRsp(mpEventList));

		return guideHistoryRspList;
	}

	@Override
	public List<String> getEventTimeList(String roomId, Integer businessType) {
		List<String> eventTimes = situationGuideHisMapper.getEventTimes(roomId);
		//获取该训练过程中的所有场景id
		String sceneIdsRecord = situationGuideHisMapper.getSceneIdsByRoom(roomId, businessType);
		List<String> sceneIds = JsonUtil.toJsonObjectArray(sceneIdsRecord, String.class);
		if (CollectionUtils.isNotEmpty(sceneIds)) {
			JSONArray eventTimeArray = remoteSceneService.getEventTimeList(StringUtils.join(sceneIds, ","));
			eventTimes.addAll(eventTimeArray.toJavaList(JSONObject.class).stream().map(eventTime -> eventTime.getString("time")).collect(Collectors.toList()));
		}
		return eventTimes;
	}

	@Override
	public Boolean execute(ExecGuideHisReq execGuideHisReq) {
		List<SituationGuideHistory> situationGuideHistoryList = situationGuideService.guideHistoryRspToSituationGuideHistory(Collections.singletonList(execGuideHisReq));
		String topoId = situationGuideHisMapper.getTopoIdByRoom(execGuideHisReq.getRoomId(), execGuideHisReq.getBusinessType());
		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			situationGuideHistory.setSceneId(topoId);
			situationGuideService.pushDataToRoomByGuide(false, situationGuideHistory);
		}
		return true;
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

	private void setScreenBoTime(ScreenBo screenBo, String startTime, String endTime) {
		if (screenBo != null) {
			screenBo.setStartTime(TimeUtil.toDate1(screenBo.getStartTime()).before(TimeUtil.toDate1(startTime)) ? screenBo.getStartTime() : startTime);
			screenBo.setEndTime(TimeUtil.toDate1(screenBo.getEndTime()).before(TimeUtil.toDate1(endTime)) ? endTime : screenBo.getEndTime());
		}
	}

}
