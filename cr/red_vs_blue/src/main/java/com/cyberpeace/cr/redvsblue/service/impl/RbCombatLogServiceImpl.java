package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.RbSituationWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.*;
import com.cyberpeace.cr.redvsblue.dao.entity.*;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.cyberpeace.cr.redvsblue.service.impl.LogHandler.BaseCombatLogHandler;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.cyberpeace.cr.crbase.dao.entity.CrTask.TaskStatus.FINISHED_STATUS_LIST;

@Service
public class RbCombatLogServiceImpl extends ServiceImpl<RbCombatLogMapper, RbCombatLog> implements RbCombatLogService {

	private List<String> REPEATED_DATA = new ArrayList<>();

	private Integer FLAG_LENGTH = 0;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

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
			System.out.println("未知来源的数据:" + syslog);
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
		if (initData.get("event_type") == null){
			System.out.println("未知来源的数据:" + syslog);
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
		BaseCombatLogHandler combatLogHandler = BaseCombatLogHandler.get(initData.get("event_type"), initData, null);
		combatLogHandler.save();
		combatLogHandler.sendMessage();
	}

	@Override
	public Boolean isZrSyslog(String syslog) {
		return syslog.contains("event_type");
	}

	@Override
	public Boolean isSbSyslog(String syslog) {
		return syslog.contains("{");
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
				initMap.put(spStr[0], spStr[1].replace("\"","").trim());
			}
		}
		initMap.put("original_log", syslog);
		return initMap;
	}

    @Override
    public RbCombatRecordsRsp getCombatRecords(String roomId) {
		LambdaQueryWrapper<RbCombatLog> filter = new LambdaQueryWrapper<>();
		filter.eq(RbCombatLog::getRoomId, roomId);
		filter.orderByDesc(RbCombatLog::getEventTime);
		List<RbCombatLog> rbCombatLogs = rbCombatLogMapper.selectList(filter);
		RbCombatRecordsRsp res;
		RbCombatRecordsAllDataRsp tmpData = new RbCombatRecordsAllDataRsp();
		rbCombatLogs.forEach(
				rbCombatLog -> {
					Integer eventType = rbCombatLog.getEventType();
					BaseCombatLogHandler baseCombatLogHandler = BaseCombatLogHandler.get(
							EnumUtils.getEnumByCode(eventType, CrCommonConstants.EventType.class).getName(), null, rbCombatLog);
					baseCombatLogHandler.addData(tmpData);
				}
		);
		res = handlerCombatData(tmpData);
		return res;
    }

	@Override
	public RbMissionStatusDataRsp getMissionStatus(String roomId) {
		RbMissionStatusDataRsp res = new RbMissionStatusDataRsp();
		List<RbMissionStatusGroupDataRsp> redRole = new ArrayList<>();
		List<RbMissionStatusGroupDataRsp> blueRole = new ArrayList<>();

		List<CrTask> crTasks = crTaskMapper.selectByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = StreamApiUtils.groupBy(crTasks, CrTask::getRoleId);

		List<CrRole> rolesByRoom = crRoleMapper.getRolesByRoom(roomId, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		Map<String, CrRole> stringCrRoleMap = StreamApiUtils.toMap(rolesByRoom, CrRole::getResourceId, tmp -> tmp);

		List<Mission> missions = missionMapper.selectByRoomId(roomId);
		Map<String, String> missionMaps = StreamApiUtils.toMap(missions, Mission::getResourceId, MissionBase::getTitle);

		for (Map.Entry<String, List<CrTask>> entry : taskMap.entrySet()) {
			String roleId = entry.getKey();
			CrRole crRole = stringCrRoleMap.get(roleId);
			List<CrTask> value = entry.getValue();
			Map<String, List<CrTask>> missons = StreamApiUtils.groupBy(value, CrTask::getMissionId);
			for (Map.Entry<String, List<CrTask>> mission : missons.entrySet()) {
				List<CrTask> sorted = mission.getValue().stream().sorted(Comparator.comparing(CrTask::getUpdateTime)).collect(Collectors.toList());
				CrTask task = sorted.get(sorted.size()-1);
				if(crRole.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode())){
					RbMissionStatusGroupDataRsp tmp = getRbMissionStatusGroupDataRsp(missionMaps, task);
					redRole.add(tmp);
				}
				if(crRole.getRoleGroup().equals(CrRole.RbRoleGroup.BLUE.getCode())){
					RbMissionStatusGroupDataRsp tmp = getRbMissionStatusGroupDataRsp(missionMaps, task);
					blueRole.add(tmp);
				}
			}
		}
		res.setRedRole(redRole);
		res.setBlueRole(blueRole);
		return res;
	}


	private RbMissionStatusGroupDataRsp getRbMissionStatusGroupDataRsp(Map<String, String> missionMaps, CrTask val) {
		RbMissionStatusGroupDataRsp tmp = new RbMissionStatusGroupDataRsp();
		tmp.setTask(missionMaps.get(val.getMissionId()));
		Integer tmpStatue = 0;
		Integer valStatue = val.getStatus();
		Mission mission = missionMapper.selectById(val.getMissionId());
		Integer score = val.getScore();
		if (FINISHED_STATUS_LIST.contains(valStatue)) {
			if (score.equals(0)) {
				tmpStatue = CrTask.TaskStatus.FAIL.getCode();
			}else if (score.equals(mission.getScore())) {
				tmpStatue = CrTask.TaskStatus.SUCCESS.getCode();
			}else {
				// 部分正确
				tmpStatue = CrTask.TaskStatus.IMCOMPLETE.getCode();
			}
		}
		tmp.setStatus(tmpStatue);
		return tmp;
	}

	/**
	 * 判断该任务是否属于情报侦察
	 *
	 * @param submitEvaluationReq
	 * @return
	 */
	@Override
	public RbCombatLog intelligenceInvestigationMission(SubmitEvaluationReq submitEvaluationReq){
		CrTaskSubmit crTaskSubmit = crTaskSubmitMapper.selectById(submitEvaluationReq.getSubmitId());
		CrTask crTask = crTaskMapper.selectById(crTaskSubmit.getTaskId());
		Mission mission = missionMapper.selectById(crTask.getMissionId());
		String title = mission.getTitle();
		RbCombatLog rbCombatLog = RbCombatLog.builder().build();
		if ("1_情报侦察".equals(title)){
			if(submitEvaluationReq.getScore() > 0){
				String roomId = submitEvaluationReq.getRoomId();
				CrRole crRoleByRoomIdAndUserId = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, crTask.getUserId());
				Integer sourceGroup = crRoleByRoomIdAndUserId.getRoleGroup();
				Integer destGroup = CrRole.RbRoleGroup.BLUE.getCode().equals(sourceGroup) ?
						CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();

				String content = String.format("%s对%s的目标靶机进行情报侦察", CrCommonUtil.getGroupName(sourceGroup), CrCommonUtil.getGroupName(destGroup));
				rbCombatLog.setRoomId(roomId);
				rbCombatLog.setEventType(CrCommonConstants.EventType.ATTACK_MISSION.getCode());
				rbCombatLog.setEventCategory(CrCommonConstants.EventCategory.ATTACK.getCode());
				CrCommonConstants.Mission enumByCode = EnumUtils.getEnumByCode(EnumUtils.getEnumCodeByDesc(mission.getTitle(), CrCommonConstants.Mission.class), CrCommonConstants.Mission.class);
				rbCombatLog.setAttackPhase(enumByCode.getStage());
				rbCombatLog.setAttackAction(enumByCode.getAction());
				rbCombatLog.setSourceGroup(sourceGroup);
				rbCombatLog.setDestGroup(destGroup);
				rbCombatLog.setEventContent(content);
				rbCombatLogMapper.insert(rbCombatLog);
				return rbCombatLogMapper.selectById(rbCombatLog.getResourceId());
			}
		}
		return null;

	}


	@Override
	public List<RbCombatLog> defenseStageMission(SubmitEvaluationReq submitEvaluationReq) {
		CrTaskSubmit crTaskSubmit = crTaskSubmitMapper.selectById(submitEvaluationReq.getSubmitId());
		CrTask crTask = crTaskMapper.selectById(crTaskSubmit.getTaskId());
		Mission mission = missionMapper.selectById(crTask.getMissionId());
		List<RbCombatLog> combatLogList = new ArrayList<>();
		if (Arrays.asList("5_被攻击链条复现","6_定位攻击源", "7_清除跳板").contains(mission.getTitle())){
			Integer submitScore = submitEvaluationReq.getScore();
			if(null != submitScore && submitScore.equals(crTask.getScore()) && submitScore.equals(mission.getScore())
					&& !submitEvaluationReq.getEvaluateStatus().equals(CrTask.TaskStatus.REJECT.getCode())){
				String roomId = submitEvaluationReq.getRoomId();
				CrRole crRoleByRoomIdAndUserId = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, crTask.getUserId());
				Integer destGroup = crRoleByRoomIdAndUserId.getRoleGroup();
				Integer sourceGroup = CrRole.RbRoleGroup.BLUE.getCode().equals(destGroup) ?
						CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
				RbCombatLog rbCombatLog = new RbCombatLog();
				rbCombatLog.setRoomId(submitEvaluationReq.getRoomId());
				rbCombatLog.setEventType(CrCommonConstants.EventType.DEFENSE_MISSION.getCode());
				rbCombatLog.setEventCategory(CrCommonConstants.EventCategory.DEFENSE.getCode());
				CrCommonConstants.Mission enumByCode = EnumUtils.getEnumByCode(EnumUtils.getEnumCodeByDesc(mission.getTitle(), CrCommonConstants.Mission.class), CrCommonConstants.Mission.class);
				rbCombatLog.setDefensePhase(enumByCode.getStages().get(0));
				rbCombatLog.setDefenseAction(enumByCode.getAction());
				rbCombatLog.setSourceGroup(sourceGroup);
				rbCombatLog.setDestGroup(destGroup);
				rbCombatLog.setEventContent(CrCommonUtil.getGroupName(destGroup) + enumByCode.getContent());
				rbCombatLogMapper.insert(rbCombatLog);
				combatLogList.add(rbCombatLogMapper.selectById(rbCombatLog.getResourceId()));

				if (!"7_清除跳板".equals(mission.getTitle())){
					rbCombatLog.setDefensePhase(enumByCode.getStages().get(1));
					rbCombatLog.setResourceId(null);
					rbCombatLog.setUpdateTime(null);
					rbCombatLog.setCreateTime(null);
					rbCombatLogMapper.insert(rbCombatLog);
					combatLogList.add(rbCombatLogMapper.selectById(rbCombatLog.getResourceId()));
				}
				return combatLogList;
			}
		}
		return null;

	}

	@Override
	public RbCombatLog attackStageMission(SubmitEvaluationReq submitEvaluationReq){
		CrTaskSubmit crTaskSubmit = crTaskSubmitMapper.selectById(submitEvaluationReq.getSubmitId());
		CrTask crTask = crTaskMapper.selectById(crTaskSubmit.getTaskId());
		Mission mission = missionMapper.selectById(crTask.getMissionId());
		String title = mission.getTitle();
		if(!title.contains("_")){
			return null;
		}
		String[] nodeNames = title.split("_");
		String nodeName = nodeNames[1];
		String[] serverNames = title.split("攻陷");
		String serverName = serverNames[serverNames.length - 1];
		Integer enumCodeByDesc = EnumUtils.getEnumCodeByDesc(nodeName, CrCommonConstants.Mission.class);
		if(null != enumCodeByDesc){
			Integer score = submitEvaluationReq.getScore();
			if (null != score && score.equals(mission.getScore()) &&
					!submitEvaluationReq.getScore().equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())){
				String roomId = submitEvaluationReq.getRoomId();
				CrRole crRoleByRoomIdAndUserId = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, crTask.getUserId());
				Integer sourceGroup = crRoleByRoomIdAndUserId.getRoleGroup();
				Integer destGroup = CrRole.RbRoleGroup.BLUE.getCode().equals(sourceGroup) ?
						CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
				String ip = getNodeIpDataByName(roomId, serverName, destGroup);
				String eventContent = String.format("%s攻陷%s的%s",
						CrCommonUtil.getGroupName(sourceGroup), CrCommonUtil.getGroupName(destGroup), serverName);
				RbCombatLog rbCombatLog = new RbCombatLog();
				rbCombatLog.setRoomId(roomId);
				rbCombatLog.setEventType(CrCommonConstants.EventType.ATTACK_MISSION.getCode());
				rbCombatLog.setEventCategory(CrCommonConstants.EventCategory.ATTACK.getCode());
				CrCommonConstants.Mission enumByCode = EnumUtils.getEnumByCode(EnumUtils.getEnumCodeByDesc(nodeName, CrCommonConstants.Mission.class), CrCommonConstants.Mission.class);

				rbCombatLog.setAttackPhase(enumByCode.getStage());
				rbCombatLog.setAttackAction(enumByCode.getAction());
				rbCombatLog.setSourceGroup(sourceGroup);
				rbCombatLog.setDestGroup(destGroup);
				rbCombatLog.setDestIp(ip);
				rbCombatLog.setEventContent(eventContent);
				rbCombatLogMapper.insert(rbCombatLog);
				return rbCombatLogMapper.selectById(rbCombatLog.getResourceId());
			}
		}
		return null;
	}

	@Override
	public RbMissionStatusGroupWsDataRsp fullScoreMissionStatus(SubmitEvaluationReq submitEvaluationReq) {
		CrTaskSubmit crTaskSubmit = crTaskSubmitMapper.selectById(submitEvaluationReq.getSubmitId());
		CrTask crTask = crTaskMapper.selectById(crTaskSubmit.getTaskId());
		Mission mission = missionMapper.selectById(crTask.getMissionId());
		CrRole crRole = crRoleMapper.selectById(crTask.getRoleId());
		String title = mission.getTitle();
		String[] serverNames = title.split("攻陷");
		String serverName = serverNames[serverNames.length - 1];
		String roomId = submitEvaluationReq.getRoomId();
		String ip = getIpByServerName(crTask, serverName, roomId);
		if(crTaskSubmit.getMissionScore().equals(crTaskSubmit.getScore())){
			RbMissionStatusGroupWsDataRsp missionStatusGroupDataRsp = new RbMissionStatusGroupWsDataRsp();
			missionStatusGroupDataRsp.setTask(title);
			missionStatusGroupDataRsp.setStatus(CrTask.TaskStatus.SUCCESS.getCode());
			missionStatusGroupDataRsp.setGroup(crRole.getRoleGroup());
			missionStatusGroupDataRsp.setDestIp(ip);
			return missionStatusGroupDataRsp;
		}
		return null;
	}

	private String getIpByServerName(CrTask crTask, String serverName, String roomId) {
		CrRole crRoleByRoomIdAndUserId = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, crTask.getUserId());
		Integer sourceGroup = crRoleByRoomIdAndUserId.getRoleGroup();
		Integer destGroup = CrRole.RbRoleGroup.BLUE.getCode().equals(sourceGroup) ?
				CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
		return getNodeIpDataByName(roomId, serverName, destGroup);
	}

	@Override
	public void pushMissionReferee(String roomId, RbCombatLog investigationMission, List<RbCombatLog> defenseStageMissions, RbCombatLog attackStageMission) {
		RbCombatRecordsAllDataRsp rbCombatRecordsAllDataRsp = new RbCombatRecordsAllDataRsp();
		rbCombatRecordsAllDataRsp.setCode(RbSituationWsCode.ONLY_PROVIDED_FRONT_CODE);
		if (investigationMission != null) {
			RbAttackDefenseStage attackDefenseStage = RbAttackDefenseStage.builder().resourceId(investigationMission.getResourceId())
					.group(investigationMission.getSourceGroup()).attackPhase(investigationMission.getAttackPhase())
					.content(String.format("【%s】%s", EnumUtils.getEnumByCode(investigationMission.getEventType(),
							CrCommonConstants.EventType.class).getDesc(),investigationMission.getEventContent())).build();
			rbCombatRecordsAllDataRsp.getRbAttackStages().add(attackDefenseStage);
			RbAttackEvent rbAttackEvent = RbAttackEvent.builder().resourceId(investigationMission.getResourceId())
					.type(CrCommonConstants.EventCategory.ATTACK.getDesc())
					.sourceGroup(EnumUtils.getEnumByCode(investigationMission.getSourceGroup(), CrRole.RbRoleGroup.class).getChinese())
					.sourceIp("").sourceName(investigationMission.getSourceName()).destGroup(EnumUtils.getEnumByCode(investigationMission.getDestGroup(), CrRole.RbRoleGroup.class).getChinese())
					.destIp("").destName(investigationMission.getDestName()).groupType(String.format("%s%s",
							EnumUtils.getEnumByCode(investigationMission.getSourceGroup(), CrRole.RbRoleGroup.class).getChinese(), CrCommonConstants.EventCategory.ATTACK.getDesc()))
					.action(EnumUtils.getEnumDescByCode(investigationMission.getAttackAction(), CrCommonConstants.AttackAction.class))
					.eventTime(TimeUtil.format1(investigationMission.getEventTime()))
					.eventDescribe(investigationMission.getEventContent())
					.visible(Boolean.TRUE).build();
			rbCombatRecordsAllDataRsp.getRbAttackDefenseEvents().add(rbAttackEvent);
		}
		if (defenseStageMissions != null && defenseStageMissions.size() > 0) {
			RbCombatLog rbCombatLog = defenseStageMissions.get(0);
			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder()
					.resourceId(rbCombatLog.getResourceId())
					.group(rbCombatLog.getDestGroup())
					.attackPhase(rbCombatLog.getDefensePhase())
					.content(String.format("【%s】%s", EnumUtils.getEnumByCode(rbCombatLog.getEventType(),
							CrCommonConstants.EventType.class).getDesc(),rbCombatLog.getEventContent())).build();
			rbCombatRecordsAllDataRsp.getRbDefenseStages().add(rbAttackDefenseStage);
			if (defenseStageMissions.size() == 2) {
				RbCombatLog tmpRbCombatLog = defenseStageMissions.get(1);
				rbAttackDefenseStage = RbAttackDefenseStage.builder()
						.resourceId(tmpRbCombatLog.getResourceId())
						.group(tmpRbCombatLog.getDestGroup())
						.attackPhase(tmpRbCombatLog.getDefensePhase())
						.content(String.format("【%s】%s", EnumUtils.getEnumByCode(tmpRbCombatLog.getEventType(),
								CrCommonConstants.EventType.class).getDesc(),tmpRbCombatLog.getEventContent())).build();
				rbCombatRecordsAllDataRsp.getRbDefenseStages().add(rbAttackDefenseStage);
			}
			RbAttackEvent rbAttackEvent = RbAttackEvent.builder()
					.resourceId(rbCombatLog.getResourceId())
					.type(CrCommonConstants.EventCategory.DEFENSE.getDesc())
					.sourceGroup(EnumUtils.getEnumByCode(rbCombatLog.getSourceGroup(), CrRole.RbRoleGroup.class).getChinese())
					.sourceIp(rbCombatLog.getSourceIp()).sourceName(rbCombatLog.getSourceName())
					.destGroup(EnumUtils.getEnumByCode(rbCombatLog.getDestGroup(), CrRole.RbRoleGroup.class).getChinese())
					.destIp(rbCombatLog.getDestIp()).destName(rbCombatLog.getDestName())
					.groupType(String.format("%s%s",
							EnumUtils.getEnumByCode(rbCombatLog.getDestGroup(), CrRole.RbRoleGroup.class).getChinese(), CrCommonConstants.EventCategory.DEFENSE.getDesc()))
					.action(EnumUtils.getEnumDescByCode(rbCombatLog.getDefenseAction(), CrCommonConstants.DefenseAction.class))
					.eventTime(TimeUtil.format1(rbCombatLog.getEventTime()))
					.eventDescribe(rbCombatLog.getEventContent())
					.visible(Boolean.TRUE).build();
			rbCombatRecordsAllDataRsp.getRbAttackDefenseEvents().add(rbAttackEvent);
		}
		if (attackStageMission != null) {
			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder()
					.resourceId(attackStageMission.getResourceId())
					.group(attackStageMission.getSourceGroup())
					.attackPhase(attackStageMission.getAttackPhase())
					.content(String.format("【%s】%s", EnumUtils.getEnumDescByCode(attackStageMission.getEventType(),
							CrCommonConstants.EventType.class), attackStageMission.getEventContent())).build();
			rbCombatRecordsAllDataRsp.getRbAttackStages().add(rbAttackDefenseStage);
			RbAttackEvent rbAttackEvent = RbAttackEvent.builder()
					.resourceId(attackStageMission.getResourceId())
					.type(CrCommonConstants.EventCategory.ATTACK.getDesc())
					.sourceGroup(EnumUtils.getEnumByCode(attackStageMission.getSourceGroup(), CrRole.RbRoleGroup.class).getChinese())
					.sourceIp(attackStageMission.getSourceIp()).sourceName(attackStageMission.getSourceName())
					.destGroup(EnumUtils.getEnumByCode(attackStageMission.getDestGroup(), CrRole.RbRoleGroup.class).getChinese())
					.destIp(attackStageMission.getDestIp()).destName(attackStageMission.getDestName())
					.groupType(String.format("%s%s",
							EnumUtils.getEnumByCode(attackStageMission.getSourceGroup(), CrRole.RbRoleGroup.class).getChinese(), CrCommonConstants.EventCategory.ATTACK.getDesc()))
					.action(EnumUtils.getEnumDescByCode(attackStageMission.getAttackAction(), CrCommonConstants.AttackAction.class))
					.eventTime(TimeUtil.format1(attackStageMission.getEventTime()))
					.eventDescribe(attackStageMission.getEventContent())
					.visible(Boolean.TRUE).build();
			rbCombatRecordsAllDataRsp.getRbAttackDefenseEvents().add(rbAttackEvent);
		}

		pushDataToRoom(rbCombatRecordsAllDataRsp, roomId);
	}

	@Override
	public void	pushMissionToRoom(String roomId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", RbSituationWsCode.DISTRIBUTE_MISSION_CODE);
		pushDataToRoom(jsonObject, roomId);
	}

	public <T> void pushDataToRoom(T data, String roomId) {

		RbWsMessageIdentifier identifier = new RbWsMessageIdentifier();
		identifier.setPath(WsPath.RB_COMBAT_LOG);
		identifier.setRoomId(roomId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
																		.data(JsonUtil.toJsonString(data,
																				PropertyNamingStrategy.SnakeCase))
																		.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

	private String getNodeIpDataByName(String roomId, String name, Integer destGroup) {
		RbRoom rbRoom = rbRoomMapper.selectById(roomId);
		String localSceneId = rbRoom.getLocalSceneId();
		LocalScene localScene = localSceneMapper.selectById(localSceneId);
		String mpSceneId = localScene.getMpSceneId();
		List<JSONObject> nodeInfoList = remoteSceneService.getAllElements(mpSceneId);
		for (JSONObject nodeInfo : nodeInfoList) {
			String nodeName = nodeInfo.getString("name");
			if (name.equals(nodeName)) {
				List<JSONObject> ports = nodeInfo.getObject("ports", List.class);
				for (JSONObject port: ports) {
					if (StringUtils.isNotBlank(port.getString("ip"))) {
						return port.getString("ip");
					}
				}
			}
		}
		return "";
	}

	private RbCombatRecordsRsp handlerCombatData(RbCombatRecordsAllDataRsp tmpData) {
		RbCombatRecordsRsp res = new RbCombatRecordsRsp();
		res.setNoGroup(tmpData.getRbAttackDefenseEvents().stream().sorted(Comparator.comparing(RbAttackEvent::getEventTime).reversed()).collect(Collectors.toList()));
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
		// todo 处理源节点所属为空的情况
		rbAttackDefenseStages = rbAttackDefenseStages.stream().filter(rbAttackDefenseStage -> rbAttackDefenseStage.getGroup() != null).collect(Collectors.toList());
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
			String[] split = originalLog.replace("{", "").replace("}", "").split(",");
			JSONObject jsonObject = new JSONObject();
			for (String str:split){
				String[] strings = str.split(":");
				jsonObject.put(strings[0],strings.length==1 ? "" : (strings.length>2? str.split("cTime")[1] : strings[1]));
			}
//			JSONObject jsonObject = JSONObject.parseObject(originalLog);
			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				initMap.put(entry.getKey(), entry.getValue().toString().replace("\"",""));
			}
			if (StringUtils.isNotBlank(initMap.get("srcAddrIp")) && StringUtils.isNotBlank(initMap.get("destAddrIp"))) {
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
