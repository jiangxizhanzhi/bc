package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.SkillRes;

import java.util.List;
import java.util.Map;

public interface TrainingPlanService {

	void parseTree(LevelTreeOutput frame);

	Map<String, List<SkillRes>> getSkillTree();
}
