package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.dao.mapper.AttackMessageHistoryMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttackMessageHistoryServiceImpl extends ServiceImpl<AttackMessageHistoryMapper, AttackMessageHistory> implements AttackMessageHistoryService {

	@Autowired
	private AttackMessageHistoryMapper attackMessageHistoryMapper;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Override
	public void add(AttackMessageReq attackMessageReq) {
		AttackMessageHistory attackMessageHistory = attackMessageBoToAttackMessageHistory(attackMessageReq);
		attackMessageHistory.setResourceId(IDGeneratorUtil.getID());
		attackMessageHistoryMapper.insert(attackMessageHistory);
	}

	@Override
	public List<AttackTypeCount> getAttackTop5(String sceneId) {
		List<AttackTypeCount> attackTypeCountList = attackMessageHistoryMapper.getAttackTop(sceneId);
		if (attackTypeCountList.size() > 5) {
			return attackTypeCountList.subList(0, 5);
		}
		return attackTypeCountList;
	}

	@Override
	public List<DefenseTypeCount> getDefenseTop5(String sceneId) {
		List<DefenseTypeCount> defenseTypeCountList = attackMessageHistoryMapper.getDefenseTop(sceneId);
		if (defenseTypeCountList.size() > 5) {
			return defenseTypeCountList.subList(0, 5);
		}
		return defenseTypeCountList;
	}

	@Override
	public List<HostCount> getHostTop5(Predicate<? super AttackType> filterValue, String sceneId) {
		List<HostCount> HostCountTopList = getHostTop(filterValue, sceneId);
		Map<String, String> nodeMap = situationHandlerService.getNodeMap(sceneId);
		for (HostCount hostCount : HostCountTopList) {
			hostCount.setHostName(nodeMap.get(hostCount.getHostId()));
		}
		if (HostCountTopList.size() > 5) {
			return HostCountTopList.subList(0, 5);
		}
		return HostCountTopList;
	}

	@Override
	public List<HostStatusCount> getHostStatusCount(String sceneId) {
		SceneData sceneData = topoHandlerService.getSceneData(null, sceneId);
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<HostStatusCount> hostStatusCountList = new ArrayList<>();
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements()
																			.values());
		List<String> elementIdList = StreamApiUtils.map(elementInfoList, ElementInfo::getElementId);
		List<HostCount> paralysisHostCountList = getHostTop(AttackType::getIsParalysis, sceneId);
		List<String> paralysisHostIdList = StreamApiUtils.map(paralysisHostCountList, HostCount::getHostId);
		List<HostCount> exploitHostCountList = getHostTop(AttackType::getIsExploit, sceneId);
		List<String> exploitHostIdList = StreamApiUtils.map(exploitHostCountList, HostCount::getHostId);
		List<HostCount> virusHostCountList = getHostTop(AttackType::getIsVirus, sceneId);
		List<String> virusHostIdList = StreamApiUtils.map(virusHostCountList, HostCount::getHostId);
		elementIdList.removeAll(paralysisHostIdList);
		elementIdList.removeAll(exploitHostIdList);
		elementIdList.removeAll(virusHostIdList);
		hostStatusCountList.add(new HostStatusCount("正常", elementIdList.size()));
		hostStatusCountList.add(new HostStatusCount("病毒植入数", virusHostIdList.size()));
		hostStatusCountList.add(new HostStatusCount("漏洞利用数", exploitHostIdList.size()));
		hostStatusCountList.add(new HostStatusCount("毁瘫数", paralysisHostIdList.size()));
		return hostStatusCountList;
	}

	@Override
	public void recovery(String sceneId) {
		attackMessageHistoryMapper.recovery(sceneId);
	}

	@Override
	public List<AttackLog> getAttackLog(String sceneId) {
		List<AttackLog> attackLogList = attackMessageHistoryMapper.getAttackLog(sceneId);
		Map<String, String> nodeMap = situationHandlerService.getNodeMap(sceneId);
		for (AttackLog attackLog : attackLogList) {
			String sourceId = attackLog.getSourceId();
			if(sourceId != null && !sourceId.equals("")){
				String sourceName = nodeMap.get(sourceId);
				if (sourceName == null){
					sourceName = "area-" + attackLog.getSourceId().substring(0, 8);
				}
				attackLog.setSourceName(sourceName);
			}
			String targetId = attackLog.getTargetId();
			if(targetId != null && !targetId.equals("")){
				String targetName = nodeMap.get(targetId);
				if (targetName == null && !targetId.equals("")){
					targetName = "area-" + attackLog.getTargetId().substring(0, 8);
				}
				attackLog.setTargetName(targetName);
			}
			attackLog.setAttackType(AttackType.getEnumByKeyWord(attackLog.getAttackType(), AttackType.class)
											  .getMsg());
		}
		return attackLogList;
	}

	@Override
	public Map<String, Integer> getIntervalTime(String sceneId) {
		Date startTime = TimeUtil.toDate1(attackMessageHistoryMapper.getStartTime(sceneId));
		Date nowTime = new Date();
		Map<String, Integer> map = new HashMap<>();
		map.put("minute", (int) (TimeUtil.getIntervalSeconds(startTime, nowTime) / 60 % 60));
		map.put("hour", (int) (TimeUtil.getIntervalSeconds(startTime, nowTime) / (60 * 24)));
		return map;
	}

	@Override
	public List<AttackMessageReq> getAttackHistory(String sceneId){
		List<AttackMessageHistory> attackMessageHistoryList = attackMessageHistoryMapper.getAllAttackHistory(sceneId);
		return attackMessageHistoryList.stream().map(this::attackMessageHistoryToAttackMessageReq).collect(Collectors.toList());
	}

	public <E, T> List<HostCount> getHostTop(Predicate<? super AttackType> filterValue, String sceneId) {
		List<String> attackTypeList = Lists.newArrayList(AttackType.values())
										   .stream()
										   .filter(filterValue)
										   .map(AttackType::getKeyWord)
										   .collect(Collectors.toList());
		return attackMessageHistoryMapper.getHostTop(sceneId, attackTypeList);
	}

	public AttackMessageHistory attackMessageBoToAttackMessageHistory(AttackMessageReq attackMessageReq) {
		AttackMessageHistory attackMessageHistory = BeanUtils.copy(attackMessageReq, AttackMessageHistory.class);
		attackMessageHistory
				.setMediaAnimation(JSON.toJSONString(attackMessageReq.getMediaAnimationList()));
		if (!attackMessageReq.getType()
							 .equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			attackMessageHistory.setMediaAnimation(null);
		}
		attackMessageHistory.setIsHistory(0);
		return attackMessageHistory;
	}

	public AttackMessageReq attackMessageHistoryToAttackMessageReq(AttackMessageHistory attackMessageHistory){
		AttackMessageReq attackMessageReq = BeanUtils.copy(attackMessageHistory, AttackMessageReq.class);
		attackMessageReq.setMediaAnimationList(JsonUtil.toJsonObjectArray(attackMessageHistory.getMediaAnimation(),MediaAnimation.class));
		return attackMessageReq;
	}
}
