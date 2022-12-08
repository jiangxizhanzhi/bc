package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.Center;
import com.cyberpeace.situation.dao.entity.Station;
import com.cyberpeace.situation.dao.entity.SubRange;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:33
 */
public interface ThirdPartyService {

    List<SubRange> getSubRanges();

    List<Center> getCenters();

    List<Station> getStations(String subRangeId);

    List<EventTypeRes> getEventTypes();
}
