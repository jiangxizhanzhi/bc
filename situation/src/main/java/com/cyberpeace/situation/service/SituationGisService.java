package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.dao.entity.SituationGis;

import java.util.List;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
public interface SituationGisService {

	SituationGis getSituationGisConfig(String resourceId);

	SituationGis createSituationGisConfig(SituationGisReq situationGisReq);

	SituationGis updateSituationGisConfig(SituationGisReq situationGisReq);

	Integer deleteSituationGisConfig(List<String> idList);

	String getProvince(Float longitude, Float latitude);
}
