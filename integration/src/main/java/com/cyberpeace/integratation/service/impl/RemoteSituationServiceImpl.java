package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.AttackLogPageContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

    private String ATTACK_COUNT;

    private String ATTACK_LOG;

    @Autowired
    private MpEnvService mpEnvService;

    @PostConstruct
    public void init() {
        ATTACK_COUNT = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.situation.attack.count");
        ATTACK_LOG = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.situation.attack.log");
    }

    @Override
    public JSONArray getAttackLog(AttackLogPageContext attackLogPageContext) {
        return HttpClientUtil.rawGetByParam(attackLogPageContext, ATTACK_LOG, JSONArray.class);
    }
}
