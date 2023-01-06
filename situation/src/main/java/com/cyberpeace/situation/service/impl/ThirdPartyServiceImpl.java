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
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.controller.res.EventTypeContextRes;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.Center;
import com.cyberpeace.situation.dao.entity.EventTypeContext;
import com.cyberpeace.situation.dao.entity.Station;
import com.cyberpeace.situation.dao.entity.SubRange;
import com.cyberpeace.situation.dao.mapper.CenterMapper;
import com.cyberpeace.situation.dao.mapper.EventTypeContextMapper;
import com.cyberpeace.situation.dao.mapper.StationMapper;
import com.cyberpeace.situation.dao.mapper.SubRangeMapper;
import com.cyberpeace.situation.service.ThirdPartyService;
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
    private SubRangeMapper subRangeMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private EventTypeContextMapper eventTypeContextMapper;

    @Autowired
    private RemoteSituationService remoteSituationService;

    private volatile Map<String, Center> centerCache = Maps.newHashMap();

    private volatile Map<String, SubRange> subRangeCache = Maps.newHashMap();

    private volatile Map<String, List<Station>> stationCache = Maps.newHashMap();

    private volatile Map<String, EventTypeRes> eventTypeCache = Maps.newHashMap();

    @Override
    public List<SubRange> getSubRanges() {
        if (subRangeCache.isEmpty()) {
            subRangeInit();
        }
        List<SubRange> subRangeList = new ArrayList<>();
        subRangeCache.forEach((key, value) -> subRangeList.add(value));
        return subRangeList;
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
        if (stationCache.get(subRangeId) == null) {
            stationInit(subRangeId);
        }
        List<Station> stationList = new ArrayList<>();
        stationCache.get(subRangeId).forEach(station -> stationList.add(station));
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
            List<SubRange> subRanges = subRangeMapper.getSubRanges();
            for (SubRange subRange : subRanges) {
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
            List<BcContext> bcs = remoteSituationService.getBcList("RL");
            List<Center> centers = JsonUtil.toJsonObjectArray(JsonUtil.arrayToString(bcs), Center.class);
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
        if (stationCache.get(subRangeId) == null) {
            List<Station> stations = stationMapper.getStations(subRangeId);
            stationCache.put(subRangeId, stations);
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
