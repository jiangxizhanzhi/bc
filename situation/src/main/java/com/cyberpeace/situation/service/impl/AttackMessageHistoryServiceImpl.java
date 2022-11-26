package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.dao.mapper.AttackMessageHistoryMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
		if (attackTypeCountList.size() > 4) {
			return attackTypeCountList.subList(0, 4);
		}
		return attackTypeCountList;
	}

	@Override
	public List<DefenseTypeCount> getDefenseTop5(String sceneId) {
		List<DefenseTypeCount> defenseTypeCountList = attackMessageHistoryMapper.getDefenseTop(sceneId);
		if (defenseTypeCountList.size() > 4) {
			return defenseTypeCountList.subList(0, 4);
		}
		return defenseTypeCountList;
	}

	@Override
	public List<HostCount> getHostTop5(Predicate<? super AttackType> filterValue, String sceneId) {
		List<HostCount> HostCountTopList = getHostTop(filterValue, sceneId);
		Map<String, String> nodeMap = getNodeMap(sceneId);
		for (HostCount hostCount : HostCountTopList) {
			hostCount.setHostName(nodeMap.get(hostCount.getHostName()));
		}
		if (HostCountTopList.size() > 4) {
			return HostCountTopList.subList(0, 4);
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
		List<String> paralysisHostIdList = StreamApiUtils.map(paralysisHostCountList, HostCount::getHostName);
		List<HostCount> exploitHostCountList = getHostTop(AttackType::getIsExploit, sceneId);
		List<String> exploitHostIdList = StreamApiUtils.map(exploitHostCountList, HostCount::getHostName);
		List<HostCount> virusHostCountList = getHostTop(AttackType::getIsVirus, sceneId);
		List<String> virusHostIdList = StreamApiUtils.map(virusHostCountList, HostCount::getHostName);
		hostStatusCountList.add(new HostStatusCount("毁瘫", paralysisHostIdList.size()));
		hostStatusCountList.add(new HostStatusCount("病毒植入", virusHostIdList.size()));
		hostStatusCountList.add(new HostStatusCount("漏洞利用", exploitHostIdList.size()));
		elementIdList.removeAll(paralysisHostIdList);
		elementIdList.removeAll(exploitHostIdList);
		elementIdList.removeAll(virusHostIdList);
		hostStatusCountList.add(new HostStatusCount("正常", elementIdList.size()));
		return hostStatusCountList;
	}

	@Override
	public void recovery(String sceneId) {
		attackMessageHistoryMapper.recovery(sceneId);
	}

	@Override
	public List<AttackLog> getAttackLogTop5(String sceneId) {
		List<AttackLog> attackLogList = attackMessageHistoryMapper.getAttackLogTop5(sceneId);
		Map<String, String> nodeMap = getNodeMap(sceneId);
		for (AttackLog attackLog : attackLogList) {
			attackLog.setSourceName(nodeMap.get(attackLog.getSourceId()));
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

	public Map<String, String> getNodeMap(String sceneId) {
		SceneData sceneData = topoHandlerService.getSceneData(null, sceneId);
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements()
																			.values());
		Map<String, String> nodeMap = new HashMap<>();
		for (ElementInfo elementInfo : elementInfoList) {
			nodeMap.put(elementInfo.getElementId(), elementInfo.getName());
		}
		return nodeMap;
	}

	public AttackMessageReq attackMessageHistoryToAttackMessageReq(AttackMessageHistory attackMessageHistory){
		return BeanUtils.copy(attackMessageHistory, AttackMessageReq.class);
	}
}
