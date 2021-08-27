package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;

public interface TrainingPlanService {

	void parseTree(LevelTreeOutput frame);

	Map<String, List<SkillRes>> getSkillTree();

	RoleDetailRes getRoleOutputByRoleId(String resource_id, String type);
}
