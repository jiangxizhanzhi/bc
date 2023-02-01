package com.cyberpeace.cr.redvsblue.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
//其他日志处理类
//@Service("operation")
public class RbCommonCombatLogHandler
//		implements PracticeEvaluateHandler
{

//	private static Map<Integer, PracticeEvaluateService> evaluateMap = Maps.newHashMap();

//	static {
		// 暂时没有别的解题方案 暂时默认key为零
//		evaluateMap.put(CommonConstant.ZERO, new OperationEvaluate());
//	}


	public RbCombatRecordsRsp AddAttackDefenseStage(RbCombatRecordsRsp data, RbCombatLog rbCombatLog) {
		if(!rbCombatLog.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())){
			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder().
					resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).attackPhase(rbCombatLog.getAttackPhase()).build();

			data.setAttackDefenseStage(rbAttackDefenseStage);
		}
		return data;
	}

	public RbCombatRecordsRsp AddLostHost(RbCombatRecordsRsp data, RbCombatLog rbCombatLog){
		if(rbCombatLog.getIsLostHost()){

			RbLostHost rbLostHost = RbLostHost.builder().build();
			data.setLostHost(rbLostHost);
		}
		return data;
	}

	public RbCombatRecordsRsp AddAttackDefenseEvent(RbCombatRecordsRsp data){
		RbAttackDefenseEvent rbAttackDefenseEvent = new RbAttackDefenseEvent();
		data.setAttackDefenseEvent(rbAttackDefenseEvent);
		return data;
	}


}
