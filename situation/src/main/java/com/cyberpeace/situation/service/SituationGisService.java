package com.cyberpeace.situation.service;

import java.util.List;

import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.service.context.ConfigContext;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
public interface SituationGisService {

	ConfigContext getSituationGisConfig(String resourceId);

	ConfigContext createSituationGisConfig(SituationGisReq situationGisReq);

	ConfigContext updateSituationGisConfig(SituationGisReq situationGisReq);

	Integer deleteSituationGisConfig(List<String> idList);

	String getProvince(Float longitude, Float latitude);

	List<ConfigContext> getAllConfig();

	Boolean checkAuthorizationIp(String ip);
}
