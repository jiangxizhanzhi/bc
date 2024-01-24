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
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 定义本地提权日志处理类
@Data
@Service
public class PrivilegeEscalationLogHandler extends AttackCombatLogHandler {

	@Autowired
	private RbCombatLogService rbCombatLogService;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	Integer eventType = CrCommonConstants.EventType.PRIVILEGE_ESCALATION.getCode();
	Integer attackPhase = CrCommonConstants.AttackPhase.COMMAND.getCode();
	Integer attackAction = CrCommonConstants.AttackAction.HOST_FALL.getCode();
	Integer actionType = CrCommonConstants.ActionType.PRIVILEGE_ESCALATION.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.PRIVILEGE_ESCALATION.getCode();
	Boolean isLostHostSign = true;
	List<RbCombatLog> tmp = new ArrayList<>();

	@Override
	public String getCode() {
		return "privilege_escalation";
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		String sourceGroup = rbAttackEvent.getSourceGroup();
		String destMachine = rbAttackEvent.getDestMachine();
		rbAttackEvent.setEventDescribe(String.format("%s拿到%s的【命令执行权限】", sourceGroup, destMachine));
		return rbAttackEvent;
	}

	@Override
	public void save() {

		RbCombatLog rbCombatLog = getCombatLog();
		setInstance(rbCombatLog);
		tmp = new ArrayList<>();
		Integer destGroup = rbCombatLog.getDestGroup();
		String destName = rbCombatLog.getDestName();
		String destIp = rbCombatLog.getDestIp();
		String destGroupNmae = CrCommonUtil.getGroupName(destGroup);
		setTmp(rbCombatLog, CrCommonConstants.AttackPhase.DELIVERY.getCode(), String.format("对%s的%s(%s)发起了载荷投递", destGroupNmae, destName, destIp));
		tmp.add(rbCombatLog);
		setTmp(rbCombatLog, CrCommonConstants.AttackPhase.EXPLOITATION.getCode(), String.format("对%s的%s(%s)成功渗透", destGroupNmae, destName, destIp));
		rbCombatLogService.saveBatch(tmp);
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
	public RbCombatRecordsAllDataRsp addAttackDefenseStage(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog1) {

		if(!rbCombatLog1.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())){

			// 如果实时处理产生的缓存文件为空，则处理当前数据
			List<RbCombatLog> logs = getTmp().size() > 0 ? getTmp() : Lists.newArrayList(rbCombatLog1);

			for(RbCombatLog rbCombatLog:logs){
				RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder().
						resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).attackPhase(rbCombatLog.getAttackPhase()).content(rbCombatLog.getEventContent()).build();
				data.getRbAttackStages().add(rbAttackDefenseStage);
			}
		}
		return data;
	}

	@Override
	public void clear() {
		this.tmp = new ArrayList<>();
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
			if(item.getAttackPhase().equals(CrCommonConstants.AttackPhase.EXPLOITATION.getCode())){

				if("14.1.10.190".equals(destIp) || "15.1.10.72".equals(destIp)){
					attackEventDescribe = String.format("获取了%s命令执行权限", destIp);
				}
				if("14.1.10.101".equals(destIp) || "15.1.10.101".equals(destIp)){
					attackEventDescribe = String.format("对%s进行漏洞扫描", destIp);
				}
				if("192.168.30.23".equals(destIp) || "20.20.20.245".equals(destIp)){
					attackEventDescribe = String.format("获取了%s的控制权限", destIp);
				}
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
