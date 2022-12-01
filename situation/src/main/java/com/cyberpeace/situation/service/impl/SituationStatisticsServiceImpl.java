package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.model.SceneTrend;
import com.cyberpeace.situation.service.SituationStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class SituationStatisticsServiceImpl implements SituationStatisticsService {

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

}
