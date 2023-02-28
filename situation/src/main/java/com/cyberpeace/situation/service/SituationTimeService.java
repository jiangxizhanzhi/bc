package com.cyberpeace.situation.service;


import com.cyberpeace.situation.dao.entity.SituationTime;
import com.cyberpeace.situation.model.Instruction;

public interface SituationTimeService {

	SituationTime getTimeManagement(String topoId);

	/**
	 * 执行指令
	 * @param instruction
	 */
	void executeInstruction(Instruction instruction, String topoId);

	void valid(String topoId);
}
