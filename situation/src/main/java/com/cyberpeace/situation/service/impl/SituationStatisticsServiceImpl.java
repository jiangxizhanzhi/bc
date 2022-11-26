package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.model.AttackTypeCount;
import com.cyberpeace.situation.model.DefenseTypeCount;
import com.cyberpeace.situation.model.SceneTrend;
import com.cyberpeace.situation.service.SituationStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
public class SituationStatisticsServiceImpl implements SituationStatisticsService {

	Map<String, Map<AttackType, Integer>> attackTypeCountBySceneId = new HashMap<>();

	Map<String, Map<DefenseType, Integer>> defenseTypeCountBySceneId = new HashMap<>();

	Queue<SceneTrend> sceneTrendList = new LinkedList<>();

	Queue<SceneTrend> flowList = new LinkedList<>();

	@Override
	public Queue<SceneTrend> getSceneTrendList() {
		return sceneTrendList;
	}

	@Override
	public Queue<SceneTrend> getFlowList(){
		return flowList;
	}

	@PostConstruct
	public void init() {
		for (int i = 39; i >= 0; i--){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - (i * 5) );
			sceneTrendList.add(new SceneTrend(0, TimeUtil.format1(calendar.getTime())));
			flowList.add(new SceneTrend(0, TimeUtil.format1(calendar.getTime())));
		}
	}

	@Override
	public void addAttackType(String sceneId, AttackType attackType) {
		if (attackTypeCountBySceneId.get(sceneId) == null) {
			Map<AttackType, Integer> attackTypeCount = new TreeMap<>();
			attackTypeCount.put(attackType, 1);
			attackTypeCountBySceneId.put(sceneId, attackTypeCount);
		} else {
			Map<AttackType, Integer> attackTypeCountMap = attackTypeCountBySceneId.get(sceneId);
			attackTypeCountMap.merge(attackType, 1, Integer::sum);
		}
	}

	@Override
	public void clearAttackTypeCountBySceneId(String sceneId) {
		attackTypeCountBySceneId.remove(sceneId);
	}

	@Override
	public List<AttackTypeCount> getAttackTop5(String sceneId) {
		Map<AttackType, Integer> attackTypeCountMap = attackTypeCountBySceneId.get(sceneId);
		List<Map.Entry<AttackType, Integer>> attackTypeCountSortList = new ArrayList<>(attackTypeCountMap.entrySet());
		//排序
		attackTypeCountSortList.sort((o1, o2) -> o2.getValue()
												   .compareTo(o1.getValue()));
		List<AttackTypeCount> attackTypeCountList = new ArrayList<>();
		for (Map.Entry<AttackType, Integer> mapping : attackTypeCountSortList) {
			AttackTypeCount attackTypeCount = new AttackTypeCount();
			attackTypeCount.setAttackType(mapping.getKey()
												 .getMsg());
			attackTypeCount.setTimes(mapping.getValue());
			attackTypeCountList.add(attackTypeCount);
			if (attackTypeCountList.size() > 4) {
				break;
			}
		}
		return attackTypeCountList;
	}

	@Override
	public void addDefenseType(String sceneId, DefenseType defenseType) {
		if (defenseTypeCountBySceneId.get(sceneId) == null) {
			Map<DefenseType, Integer> DefenseTypeCount = new TreeMap<>();
			DefenseTypeCount.put(defenseType, 1);
			defenseTypeCountBySceneId.put(sceneId, DefenseTypeCount);
		} else {
			Map<DefenseType, Integer> defenseTypeCountMap = defenseTypeCountBySceneId.get(sceneId);
			defenseTypeCountMap.merge(defenseType, 1, Integer::sum);
		}
	}

	@Override
	public void clearDefenseTypeCountBySceneId(String sceneId) {
		defenseTypeCountBySceneId.remove(sceneId);
	}

	@Override
	public List<DefenseTypeCount> getDefenseTop5(String sceneId) {
		Map<DefenseType, Integer> defenseTypeCountMap = defenseTypeCountBySceneId.get(sceneId);
		List<Map.Entry<DefenseType, Integer>> defenseTypeCountSortList = new ArrayList<>(defenseTypeCountMap.entrySet());
		//排序
		defenseTypeCountSortList.sort((o1, o2) -> o2.getValue()
												   .compareTo(o1.getValue()));
		List<DefenseTypeCount> defenseTypeCountList = new ArrayList<>();
		for (Map.Entry<DefenseType, Integer> mapping : defenseTypeCountSortList) {
			DefenseTypeCount defenseTypeCount = new DefenseTypeCount();
			defenseTypeCount.setDefenseType(mapping.getKey()
												 .getMsg());
			defenseTypeCount.setTimes(mapping.getValue());
			defenseTypeCountList.add(defenseTypeCount);
			if (defenseTypeCountList.size() > 4) {
				break;
			}
		}
		return defenseTypeCountList;
	}

}
