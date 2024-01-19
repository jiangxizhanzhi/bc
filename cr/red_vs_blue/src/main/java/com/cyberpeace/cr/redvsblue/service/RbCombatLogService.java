package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusDataRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusGroupWsDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;

import java.util.List;
import java.util.Map;


public interface RbCombatLogService extends IService<RbCombatLog> {

	void handlerLog(String syslog) throws Exception;

	Boolean isZrSyslog(String syslog);

	Boolean isSbSyslog(String syslog);

	Map<String, String> getInitDataFromZrLog(String syslog);

	RbCombatRecordsRsp getCombatRecords(String roomId);

    RbMissionStatusDataRsp getMissionStatus(String roomId);

	RbCombatLog intelligenceInvestigationMission(SubmitEvaluationReq submitEvaluationReq);

	RbCombatLog newRecord(Integer sourceGroup, String title, RbCombatLog rbCombatLog, String roomId);

	List<RbCombatLog> defenseStageMission(SubmitEvaluationReq submitEvaluationReq);

	RbCombatLog attackStageMission(SubmitEvaluationReq submitEvaluationReq);

	RbMissionStatusGroupWsDataRsp fullScoreMissionStatus(SubmitEvaluationReq submitEvaluationReq);

	void pushMissionReferee(String roomId, RbCombatLog investigationMission, List<RbCombatLog> defenseStageMissions, RbCombatLog attackStageMission);

	void pushMissionToRoom(String roomId);
}
