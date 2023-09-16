package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.controller.req.guide.Screen;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
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
	public List<GuideHistoryRsp> getLocalRecord(ScreenGuideHistoryReq screenGuideHistoryReq) {
		if (CollectionUtils.isEmpty(screenGuideHistoryReq.getEventSubTypeList())) {
			return new ArrayList<>();
		}
		List<SituationGuideHistory> situationGuideHistoryList = new ArrayList<>();

		Screen attackScreen = screenGuideHistoryReq.getAttackScreen();
		Screen defenseScreen = screenGuideHistoryReq.getDefenseScreen();
		Screen blockingScreen = screenGuideHistoryReq.getBlockingScreen();
		Screen belongingScreen = screenGuideHistoryReq.getBelongingScreen();
		//攻击
		if (attackScreen != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.ATTACK.getCode(), TimeUtil.toDate1(attackScreen.getStartTime()),
					TimeUtil.toDate1(attackScreen.getEndTime()), attackScreen.getEventName(), attackScreen.getCategory(),
					attackScreen.getSource(), attackScreen.getTarget(), attackScreen.getResult(), null));
		}
		//防御
		if (defenseScreen != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.DEFENSE.getCode(), TimeUtil.toDate1(defenseScreen.getStartTime()),
					TimeUtil.toDate1(defenseScreen.getEndTime()), defenseScreen.getEventName(), defenseScreen.getCategory(),
					defenseScreen.getSource(), defenseScreen.getTarget(), defenseScreen.getResult(), null));
		}
		//阻断
		if (blockingScreen != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.BLOCKING.getCode(), TimeUtil.toDate1(blockingScreen.getStartTime()),
					TimeUtil.toDate1(blockingScreen.getEndTime()), blockingScreen.getEventName(), null,
					blockingScreen.getSource(), blockingScreen.getTarget(), null, null));
		}
		//归属
		if (belongingScreen != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getEventSubTypeList(),
					SituationGuideScheme.EventType.BELONGING.getCode(), TimeUtil.toDate1(belongingScreen.getStartTime()),
					TimeUtil.toDate1(belongingScreen.getEndTime()), belongingScreen.getEventName(), null,
					belongingScreen.getSource(), null, null, belongingScreen.getRole()));
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
			GuideHistoryRsp guideHistoryRsp;
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

		return guideHistoryRspList;
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






}
