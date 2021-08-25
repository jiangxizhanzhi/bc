package com.cyberpeace.oj.rtks.service;

import com.cyberpeace.oj.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;

import java.util.List;
import java.util.Map;

public interface TrainingPlanService {

	void parseTree(LevelTreeOutput frame);

	Map<String, List<SkillRes>> getSkillTree();
}
