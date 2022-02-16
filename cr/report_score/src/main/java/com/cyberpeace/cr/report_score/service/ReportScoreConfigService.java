package com.cyberpeace.cr.report_score.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;

import java.util.List;
import java.util.Map;

public interface ReportScoreConfigService extends IService<ReportScoreConfig> {

	List<String> getUserIds();

	Map<String, String> getSystemConfig();
}
