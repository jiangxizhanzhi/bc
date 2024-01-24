package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbMaliceProcess;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbMaliceProcessMapper;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//	定义Linux后门检测日志处理类
@Data
@Service
public class BackdoorDiagnoseLogHandler extends AttackCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.BACKDOOR_DIAGNOSE.getCode();
	List<RbCombatLog> tmp = new ArrayList<>();

	@Autowired
	private RbMaliceProcessMapper rbMaliceProcessMapper;

	@Autowired
	private RbCombatLogService rbCombatLogService;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	@Override
	public String getCode() {
		return "backdoor_diagnose";
	}

	@Override
	public void clear() {
		this.tmp = new ArrayList<>();
	}

	public void setSpringboardAttr() {
		setAttackPhase(CrCommonConstants.AttackPhase.EXPLOITATION.getCode());
		setAttackAction(CrCommonConstants.AttackAction.SPRINGBOARD.getCode());
		setActionType(CrCommonConstants.ActionType.BOUNDARY_BREAKTHROUGH.getCode());
		setIsLostHostSign(true);
	}

	public void setDeliveryAttr() {
		setAttackPhase(CrCommonConstants.AttackPhase.DELIVERY.getCode());
		setAttackAction(CrCommonConstants.AttackAction.DELIVERY.getCode());
		setActionType(CrCommonConstants.ActionType.OTHER.getCode());
		setIsLostHostSign(false);
	}

	@Override
	public void setAttr() {
		if (isSpringboard()) {
			setSpringboardAttr();
		}else {
			setDeliveryAttr();
		}
	}

	@Override
	public String getEventContent() {
		if (CrCommonConstants.AttackAction.SPRINGBOARD.getCode().equals(getAttackAction())) {
			return String.format("在%s上建立跳板", getDestName());
		}else if (getAttackAction().equals(CrCommonConstants.AttackAction.DELIVERY.getCode())) {
			return String.format("对%s进行文件投递", getDestName());
		}
		return getInitData().get("event_content");
	}

	@Override
	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		if (CrCommonConstants.AttackAction.DELIVERY.getCode().equals(rbCombatLog.getAttackAction())) {
			return String.format("【文件投递】%s", rbCombatLog.getEventContent());
		}
		return rbCombatLog.getEventContent();
	}


	private void setTmp(RbCombatLog rbCombatLog, Integer code, String content) {
		LambdaQueryWrapper<RbCombatLog> filter = new LambdaQueryWrapper<>();
		filter.eq(RbCombatLog::getRoomId, rbCombatLog.getRoomId());
		filter.eq(RbCombatLog::getAttackPhase,code);
		filter.eq(RbCombatLog::getDestIp, rbCombatLog.getDestIp());
		filter.eq(RbCombatLog::getSourceIp, rbCombatLog.getSourceIp());
		filter.eq(RbCombatLog::getEventType, rbCombatLog.getEventType());
		List<RbCombatLog> rbCombatLogs = rbCombatLogMapper.selectList(filter);
		if(rbCombatLogs.isEmpty()){
			RbCombatLog exp  = new RbCombatLog();
			BeanUtils.copyProperties(rbCombatLog, exp);
			exp.setResourceId(IDGeneratorUtil.getID());
			exp.setIsLostHost(false);
			exp.setAttackPhase(code);
			exp.setEventContent(content);
			exp.setMainLogId(rbCombatLog.getResourceId());
			tmp.add(exp);
		}
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		String sourceGroup = rbAttackEvent.getSourceGroup();
		String destMachine = rbAttackEvent.getDestMachine();
		String action = rbAttackEvent.getAction();
		if (CrCommonConstants.AttackAction.SPRINGBOARD.getCode().equals(rbCombatLog.getAttackAction())) {
			rbAttackEvent.setEventDescribe(String.format("%s在%s上【建立跳板，实现端口转发】", sourceGroup, destMachine));
		}else {
			rbAttackEvent.setEventDescribe(String.format("%s对%s进行了【%s】", sourceGroup, destMachine, action));
		}
		return rbAttackEvent;
	}

	@Override
	public void save() {
		RbCombatLog rbCombatLog = getCombatLog();
		setInstance(rbCombatLog);
		tmp = new ArrayList<>();
		setInstance(rbCombatLog);
		if(rbCombatLog.getAttackPhase().equals(CrCommonConstants.AttackPhase.EXPLOITATION.getCode())){
			Integer destGroup = rbCombatLog.getDestGroup();
			String destName = rbCombatLog.getDestName();
			String destIp = rbCombatLog.getDestIp();
			String destGroupNmae = CrCommonUtil.getGroupName(destGroup);
			setTmp(rbCombatLog, CrCommonConstants.AttackPhase.DELIVERY.getCode(), String.format("对%s的%s(%s)发起了载荷投递", destGroupNmae, destName, destIp));
			setTmp(rbCombatLog, CrCommonConstants.AttackPhase.COMMAND.getCode(), String.format("对%s的%s(%s)发起了命令控制", destGroupNmae, destName, destIp));
			tmp.add(rbCombatLog);
			rbCombatLogService.saveBatch(tmp);
		}
		saveTrojanProcess();
	}

	public void saveTrojanProcess() {
		String redVsBlue = getRedVsBlue();
		String machineId = getInitData().get("agent_id");
		String process = getInitData().get("detail.pid");
		if (StringUtils.isNotBlank(process)) {
			RbMaliceProcess rbMaliceProcess = new RbMaliceProcess(redVsBlue, machineId, process);
			rbMaliceProcessMapper.insert(rbMaliceProcess);
		}
	}

	@Override
	public RbCombatRecordsAllDataRsp addAttackDefenseStage(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog1) {
		if(!rbCombatLog1.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())){
			// 如果实时处理产生的缓存文件为空，则处理当前数据

			List<RbCombatLog> logs = getTmp().size() > 0 ? getTmp() : Lists.newArrayList(rbCombatLog1);

			for(RbCombatLog rbCombatLog:logs){

				RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder().
						resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).attackPhase(rbCombatLog.getAttackPhase()).content(this.getAttackStageContent(rbCombatLog)).build();
				data.getRbAttackStages().add(rbAttackDefenseStage);
			}
		}
		return data;
	}

	@Override
	public RbCombatRecordsAllDataRsp addAttackDefenseEvent(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog){

		if (rbCombatLog.getMainLogId() != null) {
			return data;
		}

		super.addAttackDefenseEvent(data, rbCombatLog);
		List<RbCombatLog> res;

		List<RbCombatLog> subLogs = getTmp().isEmpty() ? rbCombatLogMapper.selectByMainLogId(rbCombatLog.getResourceId()) : getTmp();

		String sourceGroup = CrCommonUtil.getGroupName(rbCombatLog.getSourceGroup());
		String sourceIp = rbCombatLog.getSourceIp();
		String sourceName = rbCombatLog.getSourceName();
		String sourceMachine = getMachineName(sourceGroup, sourceIp, sourceName);
		String destGroup = CrCommonUtil.getGroupName(rbCombatLog.getDestGroup());
		String destIp = rbCombatLog.getDestIp();
		String destName = rbCombatLog.getDestName();
		String destMachine = getMachineName(destGroup, destIp, destName);
		RbAttackEvent rbAttackEvent = data.getRbAttackDefenseEvents().remove(data.getRbAttackDefenseEvents().size()-1);
		String attackEventDescribe = rbAttackEvent.getEventDescribe();
		String attackAction = EnumUtils.getEnumDescByCode(rbCombatLog.getAttackAction(), CrCommonConstants.AttackAction.class);
		Map<String, RbAttackEvent> result = Maps.newHashMap();
		res = subLogs.stream().filter(s->!s.getResourceId().equals(rbCombatLog.getResourceId())).collect(Collectors.toList());
		result.put(rbCombatLog.getResourceId(), rbAttackEvent);
		for(RbCombatLog item: res){
			if(item.getAttackPhase().equals(CrCommonConstants.AttackPhase.DELIVERY.getCode())){

				attackEventDescribe = String.format("对%s发送了文件投递", destIp);
			}
			if(item.getAttackPhase().equals(CrCommonConstants.AttackPhase.COMMAND.getCode())){
				attackEventDescribe = String.format("%s拿到%s的【命令执行权限】", sourceGroup, destMachine);
			}
			RbAttackEvent temp = RbAttackEvent.builder().visible(Boolean.TRUE).resourceId(rbCombatLog.getResourceId()).
					group(null).type(CrCommonConstants.EventCategory.ATTACK.getDesc()).groupType(String.format("%s%s",sourceGroup, CrCommonConstants.EventCategory.ATTACK.getDesc()))
					.sourceGroup(sourceGroup).sourceIp(sourceIp).sourceName(sourceName).sourceMachine(sourceMachine)
					.destGroup(destGroup).destIp(destIp).destName(destName).destMachine(destMachine)
					.action(attackAction).eventTime(TimeUtil.format1(rbCombatLog.getEventTime())).eventDescribe(attackEventDescribe).build();

			result.put(item.getResourceId(), temp);
		}

		// 依据原始顺序排序
		List<RbAttackEvent> rbAttackEvents = Lists.newArrayList();
		for(RbCombatLog sublog: subLogs) {
			rbAttackEvents.add(result.get(sublog.getResourceId()));
		}

		data.getRbAttackDefenseEvents().addAll(rbAttackEvents);

		return data;
	}
}
