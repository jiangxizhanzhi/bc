package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.res.EventTypeContextRes;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.Center;
import com.cyberpeace.situation.dao.entity.Station;
import com.cyberpeace.situation.dao.mapper.CenterMapper;
import com.cyberpeace.situation.dao.mapper.EventTypeContextMapper;
import com.cyberpeace.situation.dao.mapper.StationMapper;
import com.cyberpeace.situation.dao.mapper.SubRangeContextMapper;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.EventTypeContext;
import com.cyberpeace.situation.service.context.SubRangeContext;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:35
 */
@Service
@Slf4j
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Autowired
    private SubRangeContextMapper subRangeContextMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private EventTypeContextMapper eventTypeContextMapper;

    private volatile Map<String, Center> centerCache = Maps.newHashMap();

    private volatile Map<String, SubRangeContext> subRangeCache = Maps.newHashMap();

    private volatile Map<String, Station> stationCache = Maps.newHashMap();

    private volatile Map<String, EventTypeRes> eventTypeCache = Maps.newHashMap();

    @Override
    public List<SubRangeContext> getSubRanges() {
        if (subRangeCache.isEmpty()) {
            subRangeInit();
        }
        List<SubRangeContext> subRangeContextList = new ArrayList<>();
        subRangeCache.forEach((key, value) -> subRangeContextList.add(value));
        return subRangeContextList;
    }

    @Override
    public List<Center> getCenters() {
        if (centerCache.isEmpty()) {
            centerInit();
        }
        List<Center> centerList = new ArrayList<>();
        centerCache.forEach((key, value) -> centerList.add(value));
        return centerList;
    }

    @Override
    public List<Station> getStations(String subRangeId){
        if (stationCache.isEmpty()) {
            stationInit(subRangeId);
        }
        List<Station> stationList = new ArrayList<>();
        stationCache.forEach((key, value) -> stationList.add(value));
        return stationList;
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

    private synchronized void subRangeInit() {
        if (subRangeCache.isEmpty()) {
            List<SubRangeContext> subRanges = subRangeContextMapper.getSubRanges();
            for (SubRangeContext subRange : subRanges) {
                String subRangeId = subRange.getSubRangeId();
                if (!subRangeCache.containsKey(subRangeId)) {
                    subRangeCache.put(subRangeId, subRange);
                } else {
                    subRangeCache.replace(subRangeId, subRange);
                }
            }
        }
    }

    private synchronized void centerInit() {
        if (centerCache.isEmpty()) {
            List<Center> centers = centerMapper.getCenters();
            for (Center center : centers) {
                String centerId = center.getCenterId();
                if (!centerCache.containsKey(centerId)) {
                    centerCache.put(centerId, center);
                } else {
                    centerCache.replace(centerId, center);
                }
            }
        }
    }

    private synchronized void stationInit(String subRangeId) {
        if (stationCache.isEmpty()) {
            List<Station> stations = stationMapper.getStations(subRangeId);
            for (Station station : stations) {
                String stationId = station.getStationId();
                if (!stationCache.containsKey(stationId)) {
                    stationCache.put(stationId, station);
                } else {
                    stationCache.replace(stationId, station);
                }
            }
        }
    }

    private synchronized void eventTypesInit() {
        if (eventTypeCache.isEmpty()) {
            List<EventTypeContext> rootEventTypeContextList = eventTypeContextMapper.getEventTypeContextList(Boolean.TRUE);
            List<EventTypeContext> eventTypeContextList = eventTypeContextMapper.getEventTypeContextList(Boolean.FALSE);
            for (EventTypeContext eventTypeContext : rootEventTypeContextList) {
                EventTypeRes parentType = BeanUtils.copy(eventTypeContext, EventTypeRes.class);
                String code = parentType.getCode();
                traversalNode(code, parentType, eventTypeContextList);
                if (!eventTypeCache.containsKey(code)) {
                    eventTypeCache.put(code, parentType);
                } else {
                    eventTypeCache.get(code).getSubTypes().add(parentType);
                }
            }
        }
    }

    private void traversalNode(String parentNode, EventTypeRes parentType, List<EventTypeContext> eventTypeContextList) {
        if (CollectionUtils.isNotEmpty(eventTypeContextList)) {
            List<EventTypeContextRes> list = new ArrayList<>();
            eventTypeContextList.forEach(eventTypeContext -> {
                if (eventTypeContext.getParentNode().equals(parentNode)) {
                    list.add(BeanUtils.copy(eventTypeContext, EventTypeContextRes.class));
                }
            });
            parentType.setSubTypes(list);
        }
    }
}
