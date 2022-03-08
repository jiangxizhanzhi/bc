package com.cyberpeace.cr.report_score.service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.report_score.controller.res.AttackLogRes;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;

import java.util.List;
import java.util.Map;

public interface ReportScoreConfigService extends IService<ReportScoreConfig> {

	List<String> getUserIds();

	Map<String, String> getSystemConfig();

	List<AttackLogRes> getAttackLogRes();

	String getSceneIds();

	Map<String,String> getUserIps();

	JSONArray getAttackTypeCount(String sceneId);

	JSONArray getSceneTrend(String sceneId);

	JSONArray getAttackIpCount(String sceneId);
}
