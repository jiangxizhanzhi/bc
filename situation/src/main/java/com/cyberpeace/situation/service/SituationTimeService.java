package com.cyberpeace.situation.service;


import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.dao.entity.SituationTime;

public interface SituationTimeService {

	SituationTimeRes getTimeManagement(String topoId);

	void valid(String topoId);

	String getRunTime(String topoId);

	Boolean addRunTime(String topoId, Integer time);

	void handleSituationTime(SituationTime situationTime);
}
