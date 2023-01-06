package com.cyberpeace.situation.service;

import java.util.List;

import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.Center;
import com.cyberpeace.situation.dao.entity.Station;
import com.cyberpeace.situation.dao.entity.SubRange;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:33
 */
public interface ThirdPartyService {

	List<Center> getCenters();

	List<SubRange> getSubRanges();

	List<Station> getStations(String subRangeId);

	List<EventTypeRes> getEventTypes();

}
