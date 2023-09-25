package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.res.AttackMessageHistoryRes;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.dao.mapper.AttackMessageHistoryMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.RealistSceneWsMessageIdentifier;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsRbElementStatus;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttackMessageHistoryServiceImpl extends ServiceImpl<AttackMessageHistoryMapper, AttackMessageHistory> implements AttackMessageHistoryService {

	@Autowired
	private AttackMessageHistoryMapper attackMessageHistoryMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Override
	public void add(AttackMessageReq attackMessageReq, Boolean attackSuccess) {
		if (attackMessageReq.getAttackOrDefense() == null || attackMessageReq.getAttackOrDefense().equals("attack")) {
			if (Boolean.FALSE.equals(attackSuccess)) {
				AttackMessageHistory attackMessageHistory = attackMessageBoToAttackMessageHistory(attackMessageReq, false);
				attackMessageHistoryMapper.insert(attackMessageHistory);
				return;
			}
		}
		AttackMessageHistory attackMessageHistory = attackMessageBoToAttackMessageHistory(attackMessageReq, true);
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
		for (HostCount hostCount : HostCountTopList) {
			hostCount.setHostName(getNameById(sceneId, hostCount.getHostId()));
		}
		if (HostCountTopList.size() > 5) {
			return HostCountTopList.subList(0, 5);
		}
		return HostCountTopList;
	}

	@Override
	public List<HostStatusCount> getHostStatusCount(String sceneId) {
		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(sceneId);
		List<HostStatusCount> hostStatusCountList = new ArrayList<>();
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements()
																			.values());
		List<String> elementIdList = StreamApiUtils.map(elementInfoList, ElementInfo::getElementId);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas()
																	  .values());
		List<String> areaIdList = StreamApiUtils.map(areaInfoList, AreaInfo::getAreaId);
		//获取异常状态id
		List<HostCount> paralysisHostCountList = getHostTop(AttackType::getIsParalysis, sceneId);
		List<String> paralysisHostIdList = StreamApiUtils.map(paralysisHostCountList, HostCount::getHostId);
		List<HostCount> exploitHostCountList = getHostTop(AttackType::getIsExploit, sceneId);
		List<String> exploitHostIdList = StreamApiUtils.map(exploitHostCountList, HostCount::getHostId);
		List<HostCount> virusHostCountList = getHostTop(AttackType::getIsVirus, sceneId);
		List<String> virusHostIdList = StreamApiUtils.map(virusHostCountList, HostCount::getHostId);
		//去除区域id
		paralysisHostIdList.removeAll(areaIdList);
		exploitHostIdList.removeAll(areaIdList);
		virusHostIdList.removeAll(areaIdList);
		//去除异常状态id
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
		//恢复所有瘫痪节点状态
		Map<String, String> nodeMap = situationHandlerService.getNodeMap(sceneId);
		Set<String> elementIdList = nodeMap.keySet();
		List<WsRbElementStatus> wsRbElementStatusList = elementIdList.stream()
																	 .map(elementId -> new WsRbElementStatus(elementId, 0))
																	 .collect(Collectors.toList());
		WsMessage<List<WsRbElementStatus>> wsMessage = new WsMessage<>(MessageType.RbNodeStatus.getDesc(),
				wsRbElementStatusList);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
	}

	@Override
	public List<AttackLog> getAttackLog(String sceneId) {
		List<AttackLog> attackLogList = attackMessageHistoryMapper.getAttackLog(sceneId);
		for (AttackLog attackLog : attackLogList) {
			String sourceId = attackLog.getSourceId();
			if (sourceId != null && !sourceId.equals("")) {
				attackLog.setSourceName(getNameById(sceneId, sourceId));
			}
			String targetId = attackLog.getTargetId();
			if (targetId != null && !targetId.equals("")) {
				attackLog.setTargetName(getNameById(sceneId, targetId));
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
		map.put("hour", (int) (TimeUtil.getIntervalSeconds(startTime, nowTime) / (60 * 60)));
		return map;
	}

	@Override
	public List<DefenseLog> getDefenseLog(String sceneId) {
		List<DefenseLog> defenseLogList = attackMessageHistoryMapper.getDefenseLog(sceneId);
		//设置名称
		defenseLogList.forEach(defenseLog -> defenseLog.setHostName(getNameById(sceneId, defenseLog.getHostId())));
		//设置防御类型
		defenseLogList.forEach(defenseLog -> defenseLog.setDefenseType(DefenseType.getEnumByKeyWord(defenseLog.getDefenseType(), DefenseType.class)
																				  .getMsg()));
		return defenseLogList;
	}

	@Override
	public void pushParalysisNodeStatus(String sceneId) {
		List<HostCount> paralysisHostCountList = getHostTop(AttackType::getIsParalysis, sceneId);
		List<String> paralysisHostIdList = StreamApiUtils.map(paralysisHostCountList, HostCount::getHostId);
		List<WsRbElementStatus> wsRbElementStatusList = paralysisHostIdList.stream()
																		   .map(elementId -> new WsRbElementStatus(elementId, 1))
																		   .collect(Collectors.toList());
		WsMessage<List<WsRbElementStatus>> wsMessage = new WsMessage<>(MessageType.RbNodeStatus.getDesc(),
				wsRbElementStatusList);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
	}

	@Override
	public List<SceneTrend> getDefenseTrend(String sceneId) {
		List<DefenseLog> defenseLogList = attackMessageHistoryMapper.getDefenseLog(sceneId);
		defenseLogList.forEach(defenseLog -> defenseLog.setTime(TimeUtil.getHalfTime(defenseLog.getTime())));
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 30);
		if (calendar1.before(calendar)) {
			calendar.set(Calendar.SECOND, 0);
		}
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < 30; i++){
			map.put(simp.format(calendar.getTime()) , 0);
			calendar.add(Calendar.SECOND, -30);
		}
		for(DefenseLog defenseLog:defenseLogList){
			Integer count = map.get(defenseLog.getTime());
			if(count != null){
				map.put(defenseLog.getTime(), count+1);
			}
		}
		List<SceneTrend> sceneTrendList = new ArrayList<>();
		for (String time: map.keySet()){
			sceneTrendList.add(new SceneTrend(map.get(time),time));
		}
		sceneTrendList.sort(Comparator.comparing(SceneTrend::getTime));
		Collections.reverse(sceneTrendList);
		return sceneTrendList;
	}

	@Override
	public void clearIllegalData(String sceneId) {
		List<AttackLog> attackLogList = attackMessageHistoryMapper.getAttackLog(sceneId);
		//获取源节点或目标节点已被删除的动效历史的resourceId列表
		List<String> resourceIdList = attackLogList.stream()
												   .filter(attackLog -> getNameById(sceneId, attackLog.getSourceId()) == null || getNameById(sceneId, attackLog.getTargetId()) == null)
												   .map(AttackLog::getResourceId)
												   .collect(Collectors.toList());
		if (resourceIdList.size()>0){
			attackMessageHistoryMapper.deleteBatchIds(resourceIdList);
		}
	}

	@Override
	public <T> void pushDataTo28S(String topoId, T data) {
		RealistSceneWsMessageIdentifier identifier = new RealistSceneWsMessageIdentifier();
		identifier.setPath(WsPath.OUTER_STATISTICS);
		identifier.setTopoId(topoId);
		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
																		.data(JsonUtil.toJsonString(data,
																				PropertyNamingStrategy.SnakeCase))
																		.messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, true);
	}

	@Override
	public List<AttackMessageHistoryRes> getAttackMessageHistoryRes(String sceneId, Date startTime, Date endTime){
		List<AttackMessageHistory> attackMessageHistoryList = attackMessageHistoryMapper.getAttackHistoryByTime(sceneId, startTime, endTime);
		return StreamApiUtils.map(attackMessageHistoryList, attackMessageHistory ->
				new AttackMessageHistoryRes(TimeUtil.format1(attackMessageHistory.getCreateTime()),
						attackMessageHistory.getDescription(), attackMessageHistory.getAttackOrDefense() == null ? "attack" : attackMessageHistory.getAttackOrDefense())
		);
	}

	public <E, T> List<HostCount> getHostTop(Predicate<? super AttackType> filterValue, String sceneId) {
		List<String> attackTypeList = Lists.newArrayList(AttackType.values())
										   .stream()
										   .filter(filterValue)
										   .map(AttackType::getKeyWord)
										   .collect(Collectors.toList());
		return attackMessageHistoryMapper.getHostTop(sceneId, attackTypeList);
	}

	public AttackMessageHistory attackMessageBoToAttackMessageHistory(AttackMessageReq attackMessageReq, Boolean attackSuccess) {
		AttackMessageHistory attackMessageHistory = BeanUtils.copy(attackMessageReq, AttackMessageHistory.class);
		attackMessageHistory
				.setMediaAnimation(JSON.toJSONString(attackMessageReq.getMediaAnimationList()));
//		if (!attackMessageReq.getType()
//							 .equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
		attackMessageHistory.setMediaAnimation(null);
//		}
		if (attackSuccess) {
			attackMessageHistory.setIsHistory(0);
		} else {
			attackMessageHistory.setIsHistory(1);
		}
		return attackMessageHistory;
	}

	public AttackMessageReq attackMessageHistoryToAttackMessageReq(AttackMessageHistory attackMessageHistory) {
		AttackMessageReq attackMessageReq = BeanUtils.copy(attackMessageHistory, AttackMessageReq.class);
		attackMessageReq.setMediaAnimationList(JsonUtil.toJsonObjectArray(attackMessageHistory.getMediaAnimation(),
				MediaAnimation.class));
		return attackMessageReq;
	}

	private String getNameById(String sceneId, String nodeId){
		Map<String, String> nodeMap = situationHandlerService.getNodeMap(sceneId);
		Map<String, String> areaMap = situationHandlerService.getAreaMap(sceneId);
		String name = nodeMap.get(nodeId);
		if(name == null){
			name = areaMap.get(nodeId);
		}
		return name;
	}
}
