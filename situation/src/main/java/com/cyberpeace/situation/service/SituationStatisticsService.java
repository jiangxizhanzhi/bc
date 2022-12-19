package com.cyberpeace.situation.service;

import com.cyberpeace.situation.model.SceneTrend;

import java.util.Queue;

public interface SituationStatisticsService {

	Queue<SceneTrend> getSceneTrendList();

	Queue<SceneTrend> getFlowList();

}
