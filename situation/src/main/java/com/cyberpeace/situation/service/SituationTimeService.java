package com.cyberpeace.situation.service;


import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.model.Instruction;

public interface SituationTimeService {

	SituationTimeRes getTimeManagement(String topoId);

	/**
	 * 执行指令
	 * @param instruction
	 */
	void executeInstruction(Instruction instruction, String topoId);

	void valid(String topoId);
}
