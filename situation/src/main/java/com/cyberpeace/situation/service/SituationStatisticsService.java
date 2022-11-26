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

	void addAttackType(String sceneId, AttackType attackType);

	void clearAttackTypeCountBySceneId(String sceneId);

	List<AttackTypeCount> getAttackTop5(String sceneId);

	void addDefenseType(String sceneId, DefenseType defenseType);

	void clearDefenseTypeCountBySceneId(String sceneId);

	List<DefenseTypeCount> getDefenseTop5(String sceneId);
}
