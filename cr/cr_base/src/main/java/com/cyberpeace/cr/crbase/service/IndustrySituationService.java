package com.cyberpeace.cr.crbase.service;

public interface IndustrySituationService {

	/**
	 * 推送态势ws到前端
	 * @return boolean
	 * @param roomId
	 * @param missionTemplateId
	 */
	boolean pushSituationWs(String roomId, String missionTemplateId);

	boolean pushSituationWs(String businessId, Integer tsId);

	boolean doDemoEffect(String businessId, Integer tsId);
}
