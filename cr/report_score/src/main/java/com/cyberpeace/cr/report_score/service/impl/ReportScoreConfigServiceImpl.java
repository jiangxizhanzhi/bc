package com.cyberpeace.cr.report_score.service.impl;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.report_score.controller.res.AttackLogRes;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreConfigMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreConfigService;
import com.cyberpeace.integratation.model.AttackLogPageContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportScoreConfigServiceImpl extends ServiceImpl<ReportScoreConfigMapper, ReportScoreConfig> implements ReportScoreConfigService {

	private static final String USER_TYPE = "user";

	private static final String SYSTEM_TYPE = "system";

	private static final String SCENE_KEY = "scene";

	@Autowired
	private ReportScoreConfigMapper reportScoreConfigMapper;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Override
	public List<String> getUserIds() {
		List<ReportScoreConfig> typeConfigs = reportScoreConfigMapper.getTypeConfigs(USER_TYPE);
		return typeConfigs.stream().map(ReportScoreConfig::getKey)
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, String> getSystemConfig() {
		List<ReportScoreConfig> typeConfigs = reportScoreConfigMapper.getTypeConfigs(SYSTEM_TYPE);
		return typeConfigs.stream()
				.collect(Collectors.toMap(ReportScoreConfig::getKey, ReportScoreConfig::getValue));
	}

	@Override
	public List<AttackLogRes> getAttackLogRes() {
		AttackLogPageContext attackLogPageContext = new AttackLogPageContext();

		ReportScoreConfig typeConfigs = reportScoreConfigMapper.getTypeConfig(SYSTEM_TYPE, SCENE_KEY);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = df.format(new Date(System.currentTimeMillis() - 1000));
		String endTime = df.format(new Date());

		attackLogPageContext.setId(typeConfigs.getValue());
		attackLogPageContext.setStartTime(startTime);
		attackLogPageContext.setEndTime(endTime);

		JSONArray attackLog = remoteSituationService.getAttackLog(attackLogPageContext);
		return null;
	}
}
