package com.cyberpeace.situation.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.res.EventListRsp;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.service.context.site.CenterContext;
import com.cyberpeace.situation.service.context.site.RangeContext;
import com.cyberpeace.situation.service.context.site.StationContext;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:33
 */
public interface ThirdPartyService {

	/**
	 * 获取LG中心列表
	 * @return List<CenterContext>
	 */
	List<CenterContext> getCenters();

	/**
	 * 获取分靶场列表
	 * @return List<RangeContext>
	 */
	List<RangeContext> getRanges(String type);

	/**
	 * 获取分靶场
	 * @param rangeId 分靶场ID
	 * @return RangeContext
	 */
	RangeContext getRangeById(String rangeId);

	/**
	 * 获取分靶场
	 * @param rangeName 分靶场名称
	 * @return RangeContext
	 */
	RangeContext getRangeByName(String rangeName);

	/**
	 * 获取场站列表
	 * @param rangeId 分靶场ID
	 * @return List<StationContext>
	 */
	List<StationContext> getStations(String rangeId);

	/**
	 * 获取攻防事件类型
	 * @return List<EventTypeRes>
	 */
	List<EventTypeRes> getEventTypes();

	/**
	 * 获取攻防事件类型映射
	 *
	 * @return {@code Map<String, String>}
	 */
	Map<String, String> getEventTypesMap();

	/**
	 * 获取特定事件类型列表
	 * @return List<EventListRsp>
	 */
	List<EventListRsp> getTypicalEvents();

	/**
	 * 获取特定事件类型（子类型）映射
	 * @return Map<String, String>
	 */
	Map<String, String> getTypicalEventsMap();

}
