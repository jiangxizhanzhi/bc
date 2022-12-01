package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.res.EventTypeContextRes;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.mapper.EventTypeContextMapper;
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
    private EventTypeContextMapper eventTypeContextMapper;

    private volatile Map<String, SubRangeContext> subRangeCache = Maps.newHashMap();

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
