package com.cyberpeace.cr.crbase.service;

/**
 * 工业态势Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface IndustrySituationService {

	/**
	 * 推送态势ws到前端
	 *
	 * @param roomId
	 * @param missionTemplateId 任务模板id
	 * @return boolean
	 */
	boolean pushSituationWs(String roomId, String missionTemplateId);

	/**
	 * 推送态势WS
	 *
	 * @param businessId 业务id
	 * @param tsId       态势id
	 * @return boolean
	 */
	boolean pushSituationWs(String businessId, Integer tsId);

	/**
	 * 做演示动效
	 *
	 * @param businessId 业务id
	 * @param tsId       态势id
	 * @return boolean
	 */
	boolean doDemoEffect(String businessId, Integer tsId);
}
