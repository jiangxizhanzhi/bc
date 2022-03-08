package com.cyberpeace.cr.report_score.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.report_score.controller.res.AttackLogRes;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreConfigMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreConfigService;
import com.cyberpeace.cr.report_score.utils.AttackLogEnumUtils;
import com.cyberpeace.integratation.model.AttackLogPageContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.ws.JyjWsEvent;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
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

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private TopoHandlerService topoHandlerService;

    @Override
    public List<String> getUserIds() {
        List<ReportScoreConfig> typeConfigs = reportScoreConfigMapper.getTypeConfigs(USER_TYPE);
        return typeConfigs.stream().map(ReportScoreConfig::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getSystemConfig() {
        return getConfigByType(SYSTEM_TYPE);
    }

	@Override
	public String getSceneIds() {

		ReportScoreConfig typeConfig = reportScoreConfigMapper
				.getTypeConfig(SYSTEM_TYPE, SCENE_KEY, null);

		if (typeConfig == null) {
			log.info("getSceneIds is null");
			return "";
		}
		return typeConfig.getValue();
	}

    @Override
    public Map<String, String> getUserIps() {
        return getConfigByType(USER_TYPE);
    }

    private Map<String, String> getConfigByType(String type) {
        List<ReportScoreConfig> typeConfigs = reportScoreConfigMapper.getTypeConfigs(type);
        return typeConfigs.stream()
                .collect(Collectors.toMap(ReportScoreConfig::getKey, ReportScoreConfig::getValue));
    }

    @Override
    public List<AttackLogRes> getAttackLogRes() {
        AttackLogPageContext attackLogPageContext = new AttackLogPageContext();

        ReportScoreConfig typeConfig = reportScoreConfigMapper.getTypeConfig(SYSTEM_TYPE, SCENE_KEY, null);

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String startTime = df.format(new Date(System.currentTimeMillis() - 1000));
//		String endTime = df.format(new Date());

        attackLogPageContext.setId(typeConfig.getValue());
//		attackLogPageContext.setStartTime(startTime);
//		attackLogPageContext.setEndTime(endTime);

        JSONObject attackLog = remoteSituationService.getAttackLog(attackLogPageContext);
        List<JSONObject> objects = JsonUtil.toJsonObjectArray(attackLog.getJSONArray("list").toString(), JSONObject.class);

        List<AttackLogRes> arrayObjects = Lists.newArrayList();

        for (JSONObject object : objects) {
            AttackLogRes attackLogRes = new AttackLogRes();
            attackLogRes.setAttackTime(object.getString("time"));
            attackLogRes.setSourceIp(object.getString("source_ip"));
            attackLogRes.setTargetIp(object.getString("target_ip"));
            attackLogRes.setTargetNodeName(object.getString("target_node_name"));

            String userName = getUserName(object.getString("source_ip"));
            attackLogRes.setUserName(userName);

            // ws推送大屏消息
            JyjWsEvent wsEvent = getWsEvent(object, userName);
            JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsEvent), JSONObject.class);
            topoHandlerService.pushDataToRoom(data, typeConfig.getValue());

            arrayObjects.add(attackLogRes);
        }

        return arrayObjects;
    }

    private String getUserName(String sourceIp) {
        ReportScoreConfig typeConfig = reportScoreConfigMapper.getTypeConfig(USER_TYPE, null, sourceIp);
        if (typeConfig == null) {
            log.info(String.format("value is %s, getTypeConfig is null", sourceIp));
            return "";
        }
        UserInfo user = remoteUserService.getById(typeConfig.getKey());
        return user.getUserName();
    }

    private JyjWsEvent getWsEvent(JSONObject object, String userName) {
        JyjWsEvent jyjWsEvent = new JyjWsEvent();
        jyjWsEvent.setSourceName(userName);
        jyjWsEvent.setLookAtTarget(true);
        jyjWsEvent.setSourceId(object.getString("source_node_name"));
        jyjWsEvent.setTargetId(object.getString("target_node_name"));

        String enDesc = object.getObject("attack_info", JSONObject.class).getString("attack_type");
        AttackType enumByEnDesc = AttackLogEnumUtils.getEnumByEnDesc(enDesc, AttackType.class);
        if (enumByEnDesc == null) {
            log.info(String.format("enDesc is %s, getEnumByEnDesc is null", enDesc));
            return null;
        }
        Integer code = enumByEnDesc.getCode();
        String attackType = enumByEnDesc.getCnDesc();
        jyjWsEvent.setAttackType(code);
        String desc = String.format("%s对%s发起%s", userName, object.getString("target_node_name"), attackType);
        jyjWsEvent.setDescription(desc);

        LineStyle lineStyle = new LineStyle();
        lineStyle.setLineType("dashed");
        lineStyle.setWidth(5);
        lineStyle.setColor("#FF0000");
        jyjWsEvent.setLineStyle(lineStyle);

        return jyjWsEvent;
    }

}
