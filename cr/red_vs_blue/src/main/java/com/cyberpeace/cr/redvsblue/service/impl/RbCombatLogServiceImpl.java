package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.redvsblue.controller.rsp.*;
import com.cyberpeace.cr.redvsblue.dao.entity.*;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.cyberpeace.cr.redvsblue.service.impl.LogHandler.BaseCombatLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RbCombatLogServiceImpl extends ServiceImpl<RbCombatLogMapper, RbCombatLog> implements RbCombatLogService {

	private List<String> REPEATED_DATA = new ArrayList<>();

	private Integer FLAG_LENGTH = 0;

	@Autowired
	private BaseCombatLogHandler baseCombatLogHandler;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private RbTemplateMapper rbTemplateMapper;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Override
	public void handlerLog(String syslog) {
		Map<String, String> initData = new HashMap<>();
		if (isZrSyslog(syslog)) {
			initData = getInitDataFromZrLog(syslog);
			if (Arrays.asList("bruteforce_ext", "bruteforce_inter")
					  .contains(initData.get("event_type"))) {
				Map<String, String> bruteforceData = new HashMap<>();
				bruteforceData.put("source_ip", initData.get("detail.src_ip"));
				bruteforceData.put("dest_ip", initData.get("agent_ip"));
				REPEATED_DATA.add(bruteforceData.toString());
				int dataSize = new HashSet<>(REPEATED_DATA).size();
				if (FLAG_LENGTH != dataSize){
					FLAG_LENGTH = dataSize;
				}else {
					throw new BusinessException(CrBusinessErrorCode.LOG_REPEATED);
				}
			}
		} else if (isSbSyslog(syslog)) {
			initData = getInitDataFromSbLog(syslog);
		} else {
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
		if (initData.get("event_type") == null){
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}

		BaseCombatLogHandler combatLogHandler = BaseCombatLogHandler.get(initData.get("datatype"), initData, null);
		combatLogHandler.save();
		combatLogHandler.sendMessage();

		System.out.println(initData.toString());
	}

	@Override
	public Boolean isZrSyslog(String syslog) {
		if (syslog.contains("event_type")) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isSbSyslog(String syslog) {
		if (syslog.contains("{")) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> getInitDataFromZrLog(String syslog) {
		List<String> stringList = new ArrayList<>(new HashSet<>(LocalStringUtil.getMatchStr(syslog, "(?=[ ]*)" +
				"[0-9a-zA-Z_.]+=\"[^\"]*\"(?=[ ]*)")));
		Map<String, String> initMap = new HashMap<>();
		for (String str : stringList) {
			String[] spStr = str.split("=");
			if (spStr.length == 1) {
				System.out.println(spStr);
			} else {
				initMap.put(spStr[0], spStr[1].replace("\"",""));
			}
		}
		initMap.put("original_log", syslog);
		return initMap;
	}

    @Override
    public RbCombatRecordsRsp getCombatRecords(String roomId) {
		LambdaQueryWrapper<RbCombatLog> filter = new LambdaQueryWrapper<>();
		filter.eq(RbCombatLog::getRoomId, roomId);
		List<RbCombatLog> rbCombatLogs = rbCombatLogMapper.selectList(filter);
		RbCombatRecordsRsp res;
		RbCombatRecordsAllDataRsp tmpData = new RbCombatRecordsAllDataRsp();
		rbCombatLogs.forEach(
				rbCombatLog -> {
					Integer eventType = rbCombatLog.getEventType();
					BaseCombatLogHandler baseCombatLogHandler = BaseCombatLogHandler.get(String.valueOf(eventType), null, rbCombatLog);
					baseCombatLogHandler.addData(rbCombatLog, tmpData);
				}
		);
		res = handlerCombatData(tmpData);
		return res;
    }

	@Override
	public RbMissionStatusDataRsp getMissionStatus(String sceneId) {
		RbRoom rbRoom = rbRoomMapper.selectById(sceneId);
		String templateId = rbRoom.getTemplateId();
		RbTemplate rbTemplate = rbTemplateMapper.selectById(templateId);
		String roleConfig = rbTemplate.getRoleConfig();
		List<MissionTemplate> missionTemplates = missionTemplateService.batchGetByMissionSchemeJson(roleConfig);
		return null;
	}

	public RbCombatLog intelligenceInvestigationMission(SubmitEvaluationReq submitEvaluationReq, String userId){
		CrTaskSubmit crTaskSubmit = crTaskSubmitMapper.selectById(submitEvaluationReq.getSubmitId());
		CrTask crTask = crTaskMapper.selectById(crTaskSubmit.getTaskId());
		Mission mission = missionMapper.selectById(crTask.getMissionId());
		String title = mission.getTitle();
		RbCombatLog rbCombatLog = RbCombatLog.builder().build();
		if (title.equals("1_情报侦查")){
			if(submitEvaluationReq.getScore() > 0){
				String roomId = submitEvaluationReq.getRoomId();
				RbRoom rbRoom = rbRoomMapper.selectById(roomId);
				CrRole crRoleByRoomIdAndUserId = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);
				Integer sourceGroup = crRoleByRoomIdAndUserId.getRoleGroup();
				Integer destGroup = CrRole.RbRoleGroup.BLUE.getCode().equals(sourceGroup) ?
						CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
				String content = String.format("%s对%s的目标靶机进行情报侦察");
				rbCombatLog.setRoomId(roomId);

			}
		}
		return rbCombatLog;

	}

	private RbCombatRecordsRsp handlerCombatData(RbCombatRecordsAllDataRsp tmpData) {
		RbCombatRecordsRsp res = new RbCombatRecordsRsp();
		res.setNoGroup(tmpData.getRbAttackDefenseEvents());
		Integer redCode = CrRole.RbRoleGroup.RED.getCode();
		Integer blueCode = CrRole.RbRoleGroup.BLUE.getCode();
		Map<Integer, List<RbAttackPhaseData>> attackDefenseStageByGroup = handlerAttackDefenseStage(tmpData.getRbAttackStages());
		Map<Integer, List<RbAttackPhaseData>> defenseStageByGroup = handlerAttackDefenseStage(tmpData.getRbDefenseStages());
		Map<Integer, List<RbLostHost>> lostHostByGroup = handlerLostHost(tmpData.getLostHosts());
		RbCombatRecordsGroupDataRsp redGroup = new RbCombatRecordsGroupDataRsp();
		List<RbAttackPhaseData> rAttackPhaseData = attackDefenseStageByGroup.get(redCode);
		redGroup.setAttackPhase(rAttackPhaseData);
		redGroup.setLostHost(lostHostByGroup.get(redCode));
		redGroup.setDefensePhase(defenseStageByGroup.get(redCode));
		RbCombatRecordsGroupDataRsp blueGroup = new RbCombatRecordsGroupDataRsp();
		blueGroup.setLostHost(lostHostByGroup.get(blueCode));
		List<RbAttackPhaseData> bAttackPhaseData = attackDefenseStageByGroup.get(blueCode);
		blueGroup.setAttackPhase(bAttackPhaseData);
		blueGroup.setDefensePhase(defenseStageByGroup.get(blueCode));
		RbCombatRecordsAddDataRsp groups = new RbCombatRecordsAddDataRsp();
		groups.setBlueRole(blueGroup);
		groups.setRedRole(redGroup);
		res.setGroup(groups);
		return res;

	}



	private Map<Integer, List<RbLostHost>> handlerLostHost(List<RbLostHost> lostHosts) {
		return StreamApiUtils.groupBy(lostHosts, RbLostHost::getGroup);
	}


	private Map<Integer, List<RbAttackPhaseData>> handlerAttackDefenseStage(List<RbAttackDefenseStage> rbAttackDefenseStages) {
		Map<Integer, List<RbAttackDefenseStage>> integerListMap = StreamApiUtils.groupBy(rbAttackDefenseStages, RbAttackDefenseStage::getGroup);
		Map<Integer, List<RbAttackPhaseData>> groupStageData = new HashMap();
		for (Map.Entry<Integer, List<RbAttackDefenseStage>> entry : integerListMap.entrySet()) {
			Integer group = entry.getKey();
			List<RbAttackPhaseData> tmpList = new ArrayList<>();
			List<RbAttackDefenseStage> value = entry.getValue();
			Map<Integer, List<RbAttackDefenseStage>> attackPhasesMap = StreamApiUtils.groupBy(value, RbAttackDefenseStage::getAttackPhase);
			for (Map.Entry<Integer, List<RbAttackDefenseStage>> attackPhases : attackPhasesMap.entrySet()) {
				Integer attackPhase = attackPhases.getKey();
				List<RbAttackDefenseStage> stages = attackPhases.getValue();
				int size = stages.size();
				RbAttackPhaseData tmp = RbAttackPhaseData.builder().attackPhase(attackPhase).stage(stages).count(size).build();
				tmpList.add(tmp);
			}
			groupStageData.put(group, tmpList);
		}
		return groupStageData;
	}

	public Map<String, String> getInitDataFromSbLog(String syslog) {
		Map<String, String> initMap = new HashMap<>();
		List<String> stringList = new ArrayList<>(new HashSet<>(LocalStringUtil.getMatchStr(syslog, "\\{.*\\}")));
		if (stringList.size() == 0){
			return new HashMap<>();
		}else if (stringList.size() ==1){
			String originalLog = stringList.get(0);
			RbCombatLog rbCombatLog = rbCombatLogMapper.selectOneByOriginalLog(originalLog);
			if (rbCombatLog != null){
				throw new BusinessException(CrBusinessErrorCode.LOG_REPEATED);
			}
			JSONObject jsonObject = JSONObject.parseObject(originalLog);
			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				initMap.put(entry.getKey(), entry.getValue().toString().replace("\"",""));
			}
			if (initMap.get("srcAddrIp") != null && initMap.get("destAddrIp") != null) {
				initMap.put("event_type", "firewall_policy");
				initMap.put("original_log", syslog);
				return initMap;
			}else {
				return new HashMap<>();
			}
		}else {
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
	}



}
