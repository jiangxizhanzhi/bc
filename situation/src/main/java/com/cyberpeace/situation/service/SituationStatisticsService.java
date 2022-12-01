package com.cyberpeace.situation.service;

import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.model.AttackTypeCount;
import com.cyberpeace.situation.model.DefenseTypeCount;
import com.cyberpeace.situation.model.SceneTrend;

import java.util.List;
import java.util.Queue;

public interface SituationStatisticsService {

	Queue<SceneTrend> getSceneTrendList();

	Queue<SceneTrend> getFlowList();

}
