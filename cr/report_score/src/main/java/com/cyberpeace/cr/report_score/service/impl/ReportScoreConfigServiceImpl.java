package com.cyberpeace.cr.report_score.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreConfigMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportScoreConfigServiceImpl extends ServiceImpl<ReportScoreConfigMapper, ReportScoreConfig> implements ReportScoreConfigService {

	private static final String USER_TYPE = "user";

	private static final String SYSTEM_TYPE = "system";

	@Autowired
	private ReportScoreConfigMapper reportScoreConfigMapper;

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
}
