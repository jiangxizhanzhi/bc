package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Set;

public class RemoteHaiboTokenService {

    private String HAIBO_TOKEN_ANALYSE;

    @PostConstruct
    public void init() {
        this.HAIBO_TOKEN_ANALYSE = ConfigUtil.getContextProperty("haibo.token.analyse");
    }

    public Set<String> getValidCourses(String haiboToken) {
        String targetUrl = String.format(HAIBO_TOKEN_ANALYSE, haiboToken);
        JSONObject jsonObject = HttpClientUtil.rawGet(targetUrl, JSONObject.class);
    }
}
