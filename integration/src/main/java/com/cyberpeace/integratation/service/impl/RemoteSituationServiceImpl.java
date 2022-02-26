package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.AttackLogPageContext;
import com.cyberpeace.integratation.model.context.AttackCountContext;
import com.cyberpeace.integratation.model.context.AttackIPContext;
import com.cyberpeace.integratation.model.mp.MpAttackCount;
import com.cyberpeace.integratation.model.mp.MpAttackIPCount;
import com.cyberpeace.integratation.service.RemoteSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

	private String ATTACK_COUNT_URL;

    private String ATTACK_LOG;

    private String ATTACK_IP_URL;

    @Autowired
    private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		ATTACK_COUNT_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.situation.attack.count");

		ATTACK_LOG = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.situation.attack.log");

		ATTACK_IP_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.situation.attack.ip.log");
	}

	@Override
	public MpAttackCount getAttackCountTop5(AttackCountContext attackCountContext) {
		return HttpClientUtil.rawGetByParam(attackCountContext, ATTACK_COUNT_URL, MpAttackCount.class);
	}

	@Override
	public List<MpAttackIPCount> getAttackIPCount(AttackIPContext attackIPContext) {
		JSONArray objects = HttpClientUtil
				.rawGetByParam(attackIPContext, ATTACK_IP_URL, JSONArray.class);
		return objects.toJavaList(MpAttackIPCount.class);
	}

    @Override
    public JSONObject getAttackLog(AttackLogPageContext attackLogPageContext) {
        return HttpClientUtil.rawGetByParam(attackLogPageContext, ATTACK_LOG, JSONObject.class);
    }
}
