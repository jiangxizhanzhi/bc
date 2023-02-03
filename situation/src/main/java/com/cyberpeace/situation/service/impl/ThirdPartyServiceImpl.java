package com.cyberpeace.situation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.integratation.service.RemoteOperationService;
import com.cyberpeace.situation.constant.SiteType;
import com.cyberpeace.situation.controller.res.EventTypeContextRes;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.EventType;
import com.cyberpeace.situation.dao.mapper.EventTypeMapper;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.site.CenterContext;
import com.cyberpeace.situation.service.context.site.RangeContext;
import com.cyberpeace.situation.service.context.site.SiteContext;
import com.cyberpeace.situation.service.context.site.StationContext;
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

	private volatile Map<String, CenterContext> centerCache = Maps.newHashMap();

	private volatile Map<String, RangeContext> rangeCache = Maps.newHashMap();

	private volatile Map<String, List<StationContext>> stationCache = Maps.newHashMap();

	private volatile Map<String, EventTypeRes> eventTypeCache = Maps.newHashMap();

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
	public List<RangeContext> getRanges() {
		checkRangeData();
		return getSiteList(rangeCache);
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
		List<BcContext> sites = remoteOperationService.getBcList(SiteType.STATION.getCode());
		List<StationContext> stations = JsonUtil.toJsonObjectArray(JsonUtil.arrayToString(sites),
				StationContext.class);
		List<StationContext> bcStations = Lists.newArrayList();
		stations.forEach(station -> {
			String rangeId = station.getSubRangeId();
			if (stationCache.get(rangeId) == null) {
				stationCache.put(rangeId, Lists.newArrayList(station));
			} else {
				stationCache.get(rangeId).add(station);
			}
		});
	}

	@Override
	public List<StationContext> getStations(String rangeId) {
		checkStationData(rangeId);
		return stationCache.get(rangeId) == null ? Lists.newArrayList() : stationCache.get(rangeId);
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

	private void traversalNode(String parentNode, EventTypeRes parentType,
			List<EventType> eventTypeList) {
		if (CollectionUtils.isNotEmpty(eventTypeList)) {
			List<EventTypeContextRes> list = new ArrayList<>();
			eventTypeList.forEach(eventType -> {
				if (eventType.getParentNode().equals(parentNode)) {
					list.add(BeanUtils.copy(eventType, EventTypeContextRes.class));
				}
			});
			parentType.setSubTypes(list);
		}
	}
}
