package com.cyberpeace.situation.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.integratation.service.bo.situation.ChildLevelData;
import com.cyberpeace.integratation.service.bo.situation.ElementData;
import com.cyberpeace.integratation.service.bo.situation.RangeData;
import com.cyberpeace.situation.service.context.EventTypeContext;
import com.cyberpeace.situation.service.context.site.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.integratation.model.situation.ThirdDictContext;
import com.cyberpeace.integratation.model.situation.res.EventListRsp;
import com.cyberpeace.integratation.service.RemoteCodeService;
import com.cyberpeace.integratation.service.RemoteOperationService;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.gis.SiteType;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.EventType;
import com.cyberpeace.situation.dao.mapper.EventTypeMapper;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:35
 */
@Service
@Slf4j
public class ThirdPartyServiceImpl implements ThirdPartyService {

	@Autowired
	private EventTypeMapper eventTypeMapper;

	@Autowired
	private RemoteOperationService remoteOperationService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private RemoteCodeService remoteCodeService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	private volatile Map<String, CenterContext> centerCache = Maps.newHashMap();

	private volatile Map<String, RangeContext> rangeCache = Maps.newHashMap();

	private volatile Map<String, List<StationContext>> stationCache = Maps.newHashMap();

	private volatile Map<String, EventTypeRes> eventTypeCache = Maps.newHashMap();

	private volatile Map<String, EventListRsp> typicalEventCache = Maps.newHashMap();

	private synchronized <T extends SiteContext> void siteInit(String siteType, Class<T> siteClazz,
			Map<String, T> cache) {

		// 清空原有数据
		cache.clear();

		// 写入站点数据
		List<BcContext> bcList = remoteOperationService.getBcList(siteType);
		List<T> sites = JsonUtil.toJsonObjectArray(JsonUtil.arrayToString(bcList), siteClazz);
		sites.forEach(site -> cache.put(site.getId(), site));
	}

	private <T extends SiteContext> List<T> getSiteList(Map<String, T> cache) {
		List<T> sites = Lists.newArrayList();
		cache.forEach((key, value) -> sites.add(value));
		return sites;
	}

	private void checkCenterData() {
		if (centerCache.isEmpty()) {
			centerInit();
		}
	}

	private synchronized void centerInit() {
		siteInit(SiteType.CENTER.getCode(), CenterContext.class, centerCache);
	}

	@Override
	public List<CenterContext> getCenters() {
		checkCenterData();
		return getSiteList(centerCache);
	}

	private void checkRangeData() {
		if (rangeCache.isEmpty()) {
			rangeInit();
		}
	}

	private synchronized void rangeInit() {
		siteInit(SiteType.RANGE.getCode(), RangeContext.class, rangeCache);
	}

	@Override
	public List<RangeContext> getRanges(String type) {
		checkRangeData();
		List<RangeContext> rangeContexts = getSiteList(rangeCache);
		List<RangeContext> result = Lists.newArrayList();

		if (StringUtils.isNotBlank(type)) {
			List<RangeContext> finalResult = result;
			rangeContexts.forEach(range -> {
				if (range.getSubRangeName().endsWith(type)) {
					finalResult.add(range);
				}
			});
		} else {
			result = rangeContexts;
		}
		return result;
	}

	@Override
	public List<RangeDetailContext> getRangeDetails(String type) {
		checkRangeData();
		List<RangeContext> rangeContexts = getSiteList(rangeCache);

		Map locationTypeMap =Maps.newHashMap();
		Set<String> locationTypeSet = rangeContexts.stream().map(RangeContext::getLocation).collect(Collectors.toSet());
		int index = 1;
		for (String locationType : locationTypeSet) {
			locationTypeMap.put(locationType,index++);
		}

		RangeData rangeData = remoteSituationService.getRangeData("");
		Map<String,List<ElementData>> childLevelDatumMap = Maps.newHashMap();

		for (ChildLevelData childLevelDatum : rangeData.getChildLevelData()) {
			childLevelDatumMap.put(childLevelDatum.getKeyWord(),childLevelDatum.getValueWord());
		}
		List<RangeDetailContext> finalResult = Lists.newArrayList();
		rangeContexts.forEach(range -> {
			if (range.getSubRangeName().endsWith(type)) {

				RangeDetailContext rangeDetailContext= BeanUtils.copy(range, RangeDetailContext.class);
				rangeDetailContext.setLocationType((Integer) locationTypeMap.getOrDefault(rangeDetailContext.getLocation() ,0));

				List<ElementData> elementDatas = childLevelDatumMap.get(rangeDetailContext.getSubRangeName());
				for (ElementData elementData : elementDatas) {
					if("资源".equals(elementData.getKeyWord())){
						rangeDetailContext.setResourceCount(Integer.valueOf(elementData.getValueWord()));
					}else if("场站".equals(elementData.getKeyWord())){
						rangeDetailContext.setStationCount(Integer.valueOf(elementData.getValueWord()));
					}else if("任务".equals(elementData.getKeyWord())){
						rangeDetailContext.setCurrentTaskCount(Integer.valueOf(elementData.getValueWord()));
					}
				}
				finalResult.add(rangeDetailContext);
			}
		});

		return finalResult;
	}

	@Override
	public RangeContext getRangeById(String rangeId) {
		checkRangeData();
		return rangeCache.get(rangeId);
	}

	@Override
	public RangeContext getRangeByName(String rangeName) {
		checkRangeData();
		RangeContext rangeContext = null;
		for (RangeContext range : rangeCache.values()) {
			if (range.getSubRangeName().equals(rangeName)) {
				rangeContext = range;
			}
		}
		return rangeContext;
	}

	private void checkStationData(String rangeId) {
		if (stationCache.get(rangeId) == null) {
			stationInit();
		}
	}

	private synchronized void stationInit() {
		// 清空原有数据
		stationCache.clear();

		// 获取所有场站数据
		List<RangeContext> rangeContexts = getRanges("中心");
		List<BcContext> sites = remoteOperationService.getBcList(SiteType.STATION.getCode());
		List<StationContext> stations = JsonUtil.toJsonObjectArray(JsonUtil.arrayToString(sites),
				StationContext.class);
		stations.forEach(station -> {
			String rangeId = station.getSubRangeId();
			if (stationCache.get(rangeId) == null) {
				stationCache.put(rangeId, Lists.newArrayList(station));
			} else {
				stationCache.get(rangeId).add(station);
			}
		});
		//6月8日徐老师说已修改逻辑，联管中心降级，只展示自己的场站，不再全部展示
//		rangeContexts.forEach(rangeContext -> {
//			stationCache.put(rangeContext.getSubRangeId(), stations);
//		});
	}

	@Override
	public List<StationContext> getStations(String rangeId) {
		checkStationData(rangeId);
		List<StationContext> stationContexts = stationCache.get(rangeId) == null ? Lists.newArrayList() : stationCache.get(rangeId);

		RangeData rangeData = remoteSituationService.getRangeData(rangeId);
		Map<String,List<ElementData>> childLevelDatumMap = Maps.newHashMap();

		for (ChildLevelData childLevelDatum : rangeData.getChildLevelData()) {
			childLevelDatumMap.put(childLevelDatum.getKeyWord(),childLevelDatum.getValueWord());
		}
		stationContexts.forEach(stationContext -> {

			List<ElementData> elementDatas = childLevelDatumMap.get(stationContext.getStationName());
			for (ElementData elementData : elementDatas) {
				if("资源".equals(elementData.getKeyWord())){
					stationContext.setResourceCount(Integer.valueOf(elementData.getValueWord()));
				}
			}
		});

		return stationContexts;
	}

	private synchronized void eventTypesInit() {
		if (eventTypeCache.isEmpty()) {
			List<EventType> rootEventTypeList = eventTypeMapper
					.getEventTypeContextList(Boolean.TRUE);
			List<EventType> eventTypeList = eventTypeMapper.getEventTypeContextList(Boolean.FALSE);
			for (EventType eventType : rootEventTypeList) {
				EventTypeRes parentType = BeanUtils.copy(eventType, EventTypeRes.class);
				String code = parentType.getCode();
				traversalNode(code, parentType, eventTypeList);
				if (!eventTypeCache.containsKey(code)) {
					eventTypeCache.put(code, parentType);
				} else {
					eventTypeCache.get(code).getSubTypes().add(parentType);
				}
			}
		}
	}

	@Override
	public List<EventTypeRes> getEventTypes() {
		if (eventTypeCache.isEmpty()) {
			eventTypesInit();
		}
		List<EventTypeRes> eventTypeResList = new ArrayList<>();
		eventTypeCache.forEach((key, value) -> eventTypeResList.add(value));
		return eventTypeResList;
	}

	@Override
	public Map<String, String> getEventTypesMap() {

		Map<String, String> eventTypeMap = new HashMap<>();

		// 提取事件类型
		for (EventTypeRes eventTypeRes : getEventTypes()) {
			eventTypeMap.put(eventTypeRes.getCode(), eventTypeRes.getName());
			if (CollectionUtils.isNotEmpty(eventTypeRes.getSubTypes())) {
				for (ThirdDictContext context : eventTypeRes.getSubTypes()) {
					eventTypeMap.put(context.getCode(), context.getName());
				}
			}
		}

		return eventTypeMap;
	}

	/**
	 * 获取事件子类型列表
	 *
	 * @return {@code List<EventTypeContext>}
	 */
	@Override
	public List<EventTypeContext> getEventSubTypeList() {
		List<EventTypeContext> eventTypeContextList = new ArrayList<>();
		for (EventTypeRes eventTypeRes : getEventTypes()) {
			if (CollectionUtils.isNotEmpty(eventTypeRes.getSubTypes())) {
				for (ThirdDictContext context : eventTypeRes.getSubTypes()) {
					EventTypeContext eventSubType = BeanUtils.deepCopy(context,
							EventTypeContext.class);
					eventSubType.setParentType(eventTypeRes.getName());
					eventTypeContextList.add(eventSubType);
				}
			}
		}
		return eventTypeContextList;
	}

	private void traversalNode(String parentNode, EventTypeRes parentType,
			List<EventType> eventTypeList) {
		if (CollectionUtils.isNotEmpty(eventTypeList)) {
			List<ThirdDictContext> list = new ArrayList<>();
			eventTypeList.forEach(eventType -> {
				if (eventType.getParentNode().equals(parentNode)) {
					list.add(BeanUtils.copy(eventType, ThirdDictContext.class));
				}
			});
			parentType.setSubTypes(list);
		}
	}

	private synchronized void typicalEventsInit() {
		// 清空原有数据
		typicalEventCache.clear();

		// 请求态势系统数据
		List<EventListRsp> typicalEvents = remoteCodeService.getEvents();

		// 写入缓存
		typicalEvents.forEach(event -> {
			typicalEventCache.put(event.getCode(), event);
		});
	}

	@Override
	public List<EventListRsp> getTypicalEvents() {

		if (typicalEventCache.isEmpty()) {
			typicalEventsInit();
		}
		List<EventListRsp> typicalEvents = new ArrayList<>();
		typicalEventCache.forEach((key, value) -> typicalEvents.add(value));
		return typicalEvents;
	}

	@Override
	public Map<String, String> getTypicalEventsMap() {

		if (typicalEventCache.isEmpty()) {
			typicalEventsInit();
		}

		Map<String, String> typicalEventMap = Maps.newHashMap();
		typicalEventCache.forEach((key, value) -> {
			for (ThirdDictContext context : value.getEvents()) {
				typicalEventMap.put(context.getCode(), context.getName());
			}
		});

		return typicalEventMap;
	}
}
