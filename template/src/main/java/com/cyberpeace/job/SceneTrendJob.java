package com.cyberpeace.job;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.model.SceneTrend;
import com.cyberpeace.situation.service.SituationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Queue;

@Component
public class SceneTrendJob {

	@Autowired
	private SituationStatisticsService situationStatisticsService;

	@Scheduled(fixedRate = 5 * 1000)
	public void add() {
		Queue<SceneTrend> sceneTrendList = situationStatisticsService.getSceneTrendList();
		SceneTrend sceneTrend = new SceneTrend();
		sceneTrend.setDataSize((int)(5*Math.random()));
		sceneTrend.setTime(TimeUtil.format1(new Date()));
		sceneTrendList.add(sceneTrend);
		if(sceneTrendList.size()>40){
			sceneTrendList.remove();
		}
	}
}
