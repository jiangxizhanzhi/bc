package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.service.context.SubRangeContext;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:33
 */
public interface ThirdPartyService {

    List<SubRangeContext> getSubRanges();

    List<EventTypeRes> getEventTypes();
}
