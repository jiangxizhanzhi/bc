package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryTimeLineReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.AttackCodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.context.guide.MpEvent;
import com.cyberpeace.cr.crbase.service.context.guide.ScreenBo;
import com.cyberpeace.integratation.model.scene.SceneEventContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.SituationGuideScheme;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.PortInfo;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 导调历史Service实现
 *
 * @author SN
 * @date 2024/11/13
 */
@Slf4j
@Service
public class GuideHistoryServiceImpl extends ServiceImpl<SituationGuideHisMapper, SituationGuideHistory> implements GuideHistoryService {

	/**
	 * 态势导调历史Mapper
	 */
	@Autowired
	private SituationGuideHisMapper situationGuideHisMapper;

	/**
	 * 态势处理器Service
	 */
	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 态势导调Service
	 */
	@Autowired
	private SituationGuideService situationGuideService;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 场景元素缓存
	 */
	Cache<String, Map<String, ElementInfo>> sceneElementCache = CacheBuilder.newBuilder()
																  .expireAfterAccess(1, TimeUnit.HOURS).build();

	/**
	 * 获取全部选项
	 *
	 * @param roomId 房间id
	 * @return {@code PlaybackOptionRsp }
	 */
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

	/**
	 * 获取记录
	 *
	 * @param screenGuideHistoryReq 屏幕导调历史请求体
	 * @return {@code List<GuideHistoryRsp> }
	 */
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

		String sceneId = situationGuideHisMapper.getReplaySceneId(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getBusinessType());
		if (StringUtils.isBlank(sceneId)) {
			log.info("This training [{}] hasn't start a scene, no scene to replay.", screenGuideHistoryReq.getRoomId());
			throw new BusinessException(CrBusinessErrorCode.REPLAY_SCENE_NOT_EXIST);
		}
		List<Integer> eventSubTypeList = screenGuideHistoryReq.getEventSubTypeList();
		//攻击
		if (attackScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.ATTACK.getCode(), TimeUtil.toDate1(attackScreenBo.getStartTime()),
					TimeUtil.toDate1(attackScreenBo.getEndTime()), attackScreenBo.getEventName(), attackScreenBo.getCategory(),
					attackScreenBo.getSource(), attackScreenBo.getTarget(), attackScreenBo.getResult(), null));
		}
		//防御
		if (defenseScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.DEFENSE.getCode(), TimeUtil.toDate1(defenseScreenBo.getStartTime()),
					TimeUtil.toDate1(defenseScreenBo.getEndTime()), defenseScreenBo.getEventName(), defenseScreenBo.getCategory(),
					defenseScreenBo.getSource(), defenseScreenBo.getTarget(), defenseScreenBo.getResult(), null));
		}
		//阻断
		if (blockingScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.BLOCKING.getCode(), TimeUtil.toDate1(blockingScreenBo.getStartTime()),
					TimeUtil.toDate1(blockingScreenBo.getEndTime()), blockingScreenBo.getEventName(), null,
					blockingScreenBo.getSource(), blockingScreenBo.getTarget(), null, null));
		}
		//归属
		if (belongingScreenBo != null) {
			situationGuideHistoryList.addAll(situationGuideHisMapper.getEventByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.BELONGING.getCode(), TimeUtil.toDate1(belongingScreenBo.getStartTime()),
					TimeUtil.toDate1(belongingScreenBo.getEndTime()), belongingScreenBo.getEventName(), null,
					belongingScreenBo.getSource(), null, null, belongingScreenBo.getRole()));
		}

		//处理成前端展示类型
		List<GuideHistoryRsp> guideHistoryRspList = new ArrayList<>();
		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			//获取场景内的节点信息
			Map<String, ElementInfo> elementInfoMap = sceneElementCache.getIfPresent(situationGuideHistory.getSceneId());
			if (elementInfoMap == null) {
				SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(situationGuideHistory.getSceneId(), true);
				elementInfoMap = situationTopo.getElements();
				sceneElementCache.put(situationGuideHistory.getSceneId(), elementInfoMap);
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
				guideHistoryRsp.setTargetName(elementInfoMap.get(situationGuideHistory.getTarget()).getName());
				guideHistoryRsp.setTargetIp(getElementIp(elementInfoMap.get(situationGuideHistory.getTarget())));
				guideHistoryRsp.setTargetId(situationGuideHistory.getTarget());
			}
			guideHistoryRspList.add(guideHistoryRsp);
		}

		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			situationGuideService.pushDataToRoomByGuide(true, situationGuideHistory);
		}

		//获取中台数据,处理数据
		ScreenBo screenBo = new ScreenBo();
		screenBo.setEventName("123");
		if (screenGuideHistoryReq.getAttackScreenBo() == null) {
			screenGuideHistoryReq.setAttackScreenBo(screenBo);
		}
		if (screenGuideHistoryReq.getDefenseScreenBo() == null) {
			screenGuideHistoryReq.setDefenseScreenBo(screenBo);
		}
		List<MpEvent> mpEventList = situationGuideService.getMpEventList(screenGuideHistoryReq);

		//推送动效
		Map<String, Integer> attCk = Arrays.stream(SituationGuideScheme.AttackUrlEnum.values())
										   .collect(Collectors.toMap(SituationGuideScheme.AttackUrlEnum::getMpCode, SituationGuideScheme.AttackUrlEnum::getCode));
		attCk.putAll(Arrays.stream(SituationGuideScheme.DefenseUrlEnum.values())
						   .collect(Collectors.toMap(SituationGuideScheme.DefenseUrlEnum::getMpCode, SituationGuideScheme.DefenseUrlEnum::getCode)));

		for (MpEvent mpEvent : mpEventList) {
			for (String category : mpEvent.getAttCks()) {
				SituationGuideHistory situationGuideHistory = new SituationGuideHistory();
				situationGuideHistory.setSceneId(sceneId);
				situationGuideHistory.setCategory(attCk.get(category));
				situationGuideHistory.setEventName(mpEvent.getEventName());
				situationGuideHistory.setEventType(SituationGuideScheme.EventType.getEventTypeByMpCode(mpEvent.getActionType()).getCode());
				if (SituationGuideScheme.Type.HOST.getMpCode().equals(mpEvent.getTriggerType())) {
					//主机采集没有源节点，将源节点和目的节点设为同一个
					situationGuideHistory.setSource(mpEvent.getTargetNodeId());
				} else {
					situationGuideHistory.setSource(mpEvent.getSourceNodeId());
				}
				situationGuideHistory.setTarget(mpEvent.getTargetNodeId());
				//中台没有攻击结果和归属，赋一个默认值
				situationGuideHistory.setResult(SituationGuideScheme.ResultType.SUCCESS.getCode());
				situationGuideHistory.setRole(SituationGuideScheme.RoleType.RED.getCode());
				situationGuideService.pushDataToRoomByGuide(true, situationGuideHistory);
			}
		}

		//处理成前端展示
		guideHistoryRspList.addAll(situationGuideService.mpEventToGuideHistoryRsp(mpEventList));

		return guideHistoryRspList;
	}

	/**
	 * 获取事件时间列表
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getEventTimeList(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq) {
		List<String> eventTimes = new ArrayList<>();

		ScreenBo attackScreenBo = screenGuideHistoryTimeLineReq.getAttackScreenBo();
		ScreenBo defenseScreenBo = screenGuideHistoryTimeLineReq.getDefenseScreenBo();
		ScreenBo blockingScreenBo = screenGuideHistoryTimeLineReq.getBlockingScreenBo();
		ScreenBo belongingScreenBo = screenGuideHistoryTimeLineReq.getBelongingScreenBo();
		//获取复盘id
		String sceneId = situationGuideHisMapper.getReplaySceneId(screenGuideHistoryTimeLineReq.getRoomId(), screenGuideHistoryTimeLineReq.getBusinessType());
		if (StringUtils.isBlank(sceneId)) {
			log.info("This training [{}] hasn't start a scene, no scene to replay.", screenGuideHistoryTimeLineReq.getRoomId());
			throw new BusinessException(CrBusinessErrorCode.REPLAY_SCENE_NOT_EXIST);
		}
		List<Integer> eventSubTypeList = screenGuideHistoryTimeLineReq.getEventSubTypeList();
		//攻击
		if (attackScreenBo != null) {
			eventTimes.addAll(situationGuideHisMapper.getEventTimeByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.ATTACK.getCode(), TimeUtil.toDate1(attackScreenBo.getStartTime()),
					TimeUtil.toDate1(attackScreenBo.getEndTime()), attackScreenBo.getEventName(), attackScreenBo.getCategory(),
					attackScreenBo.getSource(), attackScreenBo.getTarget(), attackScreenBo.getResult(), null));
		}
		//防御
		if (defenseScreenBo != null) {
			eventTimes.addAll(situationGuideHisMapper.getEventTimeByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.DEFENSE.getCode(), TimeUtil.toDate1(defenseScreenBo.getStartTime()),
					TimeUtil.toDate1(defenseScreenBo.getEndTime()), defenseScreenBo.getEventName(), defenseScreenBo.getCategory(),
					defenseScreenBo.getSource(), defenseScreenBo.getTarget(), defenseScreenBo.getResult(), null));
		}
		//阻断
		if (blockingScreenBo != null) {
			eventTimes.addAll(situationGuideHisMapper.getEventTimeByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.BLOCKING.getCode(), TimeUtil.toDate1(blockingScreenBo.getStartTime()),
					TimeUtil.toDate1(blockingScreenBo.getEndTime()), blockingScreenBo.getEventName(), null,
					blockingScreenBo.getSource(), blockingScreenBo.getTarget(), null, null));
		}
		//归属
		if (belongingScreenBo != null) {
			eventTimes.addAll(situationGuideHisMapper.getEventTimeByScreen(sceneId, eventSubTypeList,
					SituationGuideScheme.EventType.BELONGING.getCode(), TimeUtil.toDate1(belongingScreenBo.getStartTime()),
					TimeUtil.toDate1(belongingScreenBo.getEndTime()), belongingScreenBo.getEventName(), null,
					belongingScreenBo.getSource(), null, null, belongingScreenBo.getRole()));
		}

		ScreenBo screenBo = new ScreenBo();
		screenBo.setEventName("123");
		if (screenGuideHistoryTimeLineReq.getAttackScreenBo() == null) {
			screenGuideHistoryTimeLineReq.setAttackScreenBo(screenBo);
		}
		if (screenGuideHistoryTimeLineReq.getDefenseScreenBo() == null) {
			screenGuideHistoryTimeLineReq.setDefenseScreenBo(screenBo);
		}
		SceneEventContext sceneEventContext = situationGuideService.screenGuideHistoryReqToSceneEventContext(screenGuideHistoryTimeLineReq);
		if (sceneEventContext == null) {
			return eventTimes;
		}

		JSONArray eventTimeArray = remoteSceneService.getEventTimeList(sceneEventContext);
		if (eventTimeArray != null) {
			eventTimes.addAll(eventTimeArray.toJavaList(JSONObject.class).stream().map(eventTime -> eventTime.getString("time")).collect(Collectors.toList()));
		}
		return eventTimes;
	}

	/**
	 * 执行
	 *
	 * @param execGuideHisReq 执行导调历史请求体
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean execute(ExecGuideHisReq execGuideHisReq) {
		List<SituationGuideHistory> situationGuideHistoryList = situationGuideService.guideHistoryRspToSituationGuideHistory(Collections.singletonList(execGuideHisReq));
		for (SituationGuideHistory situationGuideHistory : situationGuideHistoryList) {
			situationGuideService.pushDataToRoomByGuide(true, situationGuideHistory);
		}
		return true;
	}

	/**
	 * 获取开始时间
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code String }
	 */
	@Override
	public String getStartTime(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq) {
		String startTime = null;
		ScreenBo attackScreenBo = screenGuideHistoryTimeLineReq.getAttackScreenBo();
		ScreenBo defenseScreenBo = screenGuideHistoryTimeLineReq.getDefenseScreenBo();
		ScreenBo blockingScreenBo = screenGuideHistoryTimeLineReq.getBlockingScreenBo();
		ScreenBo belongingScreenBo = screenGuideHistoryTimeLineReq.getBelongingScreenBo();

		startTime = getStartTime(startTime, attackScreenBo);
		startTime = getStartTime(startTime, defenseScreenBo);
		startTime = getStartTime(startTime, blockingScreenBo);
		startTime = getStartTime(startTime, belongingScreenBo);
		return startTime;
	}

	/**
	 * 获取结束时间
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code String }
	 */
	@Override
	public String getEndTime(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq) {
		String endTime = null;
		ScreenBo attackScreenBo = screenGuideHistoryTimeLineReq.getAttackScreenBo();
		ScreenBo defenseScreenBo = screenGuideHistoryTimeLineReq.getDefenseScreenBo();
		ScreenBo blockingScreenBo = screenGuideHistoryTimeLineReq.getBlockingScreenBo();
		ScreenBo belongingScreenBo = screenGuideHistoryTimeLineReq.getBelongingScreenBo();

		endTime = getEndTime(endTime, attackScreenBo);
		endTime = getEndTime(endTime, defenseScreenBo);
		endTime = getEndTime(endTime, blockingScreenBo);
		endTime = getEndTime(endTime, belongingScreenBo);
		return endTime;
	}

	/**
	 * 获取开始时间
	 *
	 * @param startTime 开始时间
	 * @param screenBo  屏幕Bo
	 * @return {@code String }
	 */
	private String getStartTime(String startTime, ScreenBo screenBo) {
		if (screenBo != null) {
			if (startTime == null) {
				startTime = screenBo.getStartTime();
			} else {
				if (TimeUtil.toDate1(startTime).after(TimeUtil.toDate1(screenBo.getStartTime()))) {
					startTime = screenBo.getStartTime();
				}
			}
		}
		return startTime;
	}

	/**
	 * 获取结束时间
	 *
	 * @param endTime  结束时间
	 * @param screenBo 屏幕Bo
	 * @return {@code String }
	 */
	private String getEndTime(String endTime, ScreenBo screenBo) {
		if (screenBo != null) {
			if (endTime == null) {
				endTime = screenBo.getEndTime();
			} else {
				if (TimeUtil.toDate1(endTime).before(TimeUtil.toDate1(screenBo.getEndTime()))) {
					endTime = screenBo.getEndTime();
				}
			}
		}
		return endTime;
	}

	/**
	 * 获取元素ip地址
	 *
	 * @param elementInfo 元素信息
	 * @return {@code String }
	 *///获取节点ip
	private String getElementIp(ElementInfo elementInfo) {
		List<PortInfo> portInfoList = new ArrayList<>(elementInfo.getPorts().values());
		for (PortInfo portInfo : portInfoList) {
			if (StringUtils.isNotEmpty(portInfo.getIp())) {
				return portInfo.getIp();
			}
		}
		return "";
	}

	/**
	 * 设置屏幕Bo时间
	 *
	 * @param screenBo  屏幕Bo
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 */
	private void setScreenBoTime(ScreenBo screenBo, String startTime, String endTime) {
		if (screenBo != null) {
			screenBo.setStartTime(TimeUtil.toDate1(screenBo.getStartTime()).before(TimeUtil.toDate1(startTime)) ? startTime : screenBo.getStartTime());
			screenBo.setEndTime(TimeUtil.toDate1(screenBo.getEndTime()).before(TimeUtil.toDate1(endTime)) ? screenBo.getEndTime() : endTime);
		}
	}

}
