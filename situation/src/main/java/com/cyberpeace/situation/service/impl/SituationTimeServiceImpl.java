package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.dao.entity.SituationTime;
import com.cyberpeace.situation.dao.mapper.SituationTimeMapper;
import com.cyberpeace.situation.model.DynamicSchemeProcess;
import com.cyberpeace.situation.model.Instruction;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SceneConfigService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SituationTimeServiceImpl implements SituationTimeService {

	@Autowired
	private SituationTimeMapper situationTimeMapper;

	@Autowired
	private SceneConfigService sceneConfigService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@Override
	public SituationTimeRes getTimeManagement(String topoId) {
		valid(topoId);
		SituationTime situationTime =situationTimeMapper.selectByTopoId(topoId);
		handleSituationTime(situationTime);
		SituationTimeRes situationTimeRes = BeanUtils.copy(situationTime, SituationTimeRes.class);
		situationTimeRes.setCombatTime(TimeUtil.format1(situationTime.getCombatTime()));
		situationTimeRes.setAstronomicalTime(TimeUtil.format1(situationTime.getAstronomicalTime()));
		return situationTimeRes;
	}

	@Override
	public void executeInstruction(Instruction instruction, String topoId) {
		valid(topoId);
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		handleSituationTime(situationTime);
		if (situationTime.getStatus().equals(SituationTime.Status.FINISH.getCode())) {
			throw new BusinessException(SituationErrorCode.THIS_SCENE_HAS_ENDED);
		}
		if (instruction.getCode().equals(Instruction.InstructionType.START.getCode())) {
			situationTime.setStatus(SituationTime.Status.RUNNING.getCode());
			situationTime.setStartTime(new Date());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.FINISH.getCode())) {
			situationTime.setStatus(SituationTime.Status.FINISH.getCode());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.PAUSE.getCode())) {
			situationTime.setStatus(SituationTime.Status.PAUSE.getCode());
			situationTimeMapper.updateById(situationTime);
			sceneConfigService.clearSceneCode(topoId);
		} else if (instruction.getCode().equals(Instruction.InstructionType.CONTINUE.getCode())) {
			DynamicSchemeProcess dynamicSchemeProcess = sceneConfigService.getDynamicSchemeCache(topoId);
			if (dynamicSchemeProcess != null) {
				dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeProcess.getDynamicSchemeReq(),
						dynamicSchemeProcess.getStageName(), dynamicSchemeProcess.getAttackMessageDescription());
			}
			situationTime.setStatus(SituationTime.Status.RUNNING.getCode());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.RATE.getCode())) {
			situationTime.setRate(instruction.getRate());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.ADJUST.getCode())) {
			situationTime.setAstronomicalTime(instruction.getAstronomicalTime());
			situationTime.setCombatTime(instruction.getCombatTime());
			situationTimeMapper.updateById(situationTime);
		}
		WsMessage<SituationTime> wsMessage = new WsMessage<>(MessageType.SituationTime.getDesc(), situationTime);
		situationHandlerService.pushDataToRoom(wsMessage, topoId);
	}

	@Override
	public void valid(String topoId) {
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		if (situationTime == null) {
			situationTime = new SituationTime();
			situationTime.setTopoId(topoId);
			situationTimeMapper.insert(situationTime);
		}
	}

	@Override
	public Map<String, Integer> getRunTime(String topoId) {
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		HashMap<String, Integer> map = new HashMap<>();
		if (situationTime == null || situationTime.getStatus().equals(SituationTime.Status.UNOPENED.getCode())) {
			map.put("minute", 0);
			map.put("hour", 0);
		}else {
			Date nowTime = new Date();
			map.put("minute", (int) (TimeUtil.getIntervalSeconds(situationTime.getStartTime(), nowTime) / 60 % 60));
			map.put("hour", (int) (TimeUtil.getIntervalSeconds(situationTime.getStartTime(), nowTime) / (60 * 60)));
		}
		return map;
	}

	public void handleSituationTime(SituationTime situationTime) {
		Date astronomicalTime = new Date(situationTime.getAstronomicalTime().getTime() + System.currentTimeMillis() - situationTime.getUpdateTime().getTime());
		situationTime.setAstronomicalTime(astronomicalTime);
		if (situationTime.getStatus().equals(SituationTime.Status.UNOPENED.getCode())) {
			Date combatTime = new Date(situationTime.getCombatTime().getTime() + System.currentTimeMillis() - situationTime.getUpdateTime().getTime());
			situationTime.setCombatTime(combatTime);
		} else if (situationTime.getStatus().equals(SituationTime.Status.RUNNING.getCode())) {
			Date combatTime = new Date(situationTime.getCombatTime().getTime() + (System.currentTimeMillis() - situationTime.getUpdateTime().getTime()) * situationTime.getRate());
			situationTime.setCombatTime(combatTime);
		}
	}
}
