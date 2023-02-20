package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusDataRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusGroupDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;

import java.util.List;
import java.util.Map;


public interface RbCombatLogService extends IService<RbCombatLog> {

	void handlerLog(String syslog);

	Boolean isZrSyslog(String syslog);

	Boolean isSbSyslog(String syslog);

	Map<String, String> getInitDataFromZrLog(String syslog);

	RbCombatRecordsRsp getCombatRecords(String roomId);

    RbMissionStatusDataRsp getMissionStatus(String roomId);

	RbCombatLog intelligenceInvestigationMission(SubmitEvaluationReq submitEvaluationReq);

	List<RbCombatLog> defenseStageMission(SubmitEvaluationReq submitEvaluationReq);

	RbCombatLog attackStageMission(SubmitEvaluationReq submitEvaluationReq);

	RbMissionStatusGroupDataRsp fullScoreMissionStatus(SubmitEvaluationReq submitEvaluationReq);

	void pushMissionReferee(String roomId, RbCombatLog investigationMission, List<RbCombatLog> defenseStageMissions, RbCombatLog attackStageMission);
}
