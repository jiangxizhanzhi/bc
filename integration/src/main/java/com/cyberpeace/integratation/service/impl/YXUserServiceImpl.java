package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.YXUserService;
import com.cyberpeace.integratation.service.bo.YxRvbUserColl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class YXUserServiceImpl implements YXUserService {

    private String YX_HOST;

    private String RVB_URL = "/openapi/get_match_user";

    @PostConstruct
    public void init() {
        YX_HOST = SystemPropUtil.getProp("YX_HOST");
        log.info("yx host=[{}]", YX_HOST);
        RVB_URL = ConfigUtil.getContextProperty("yx.rvb.user");
    }

    @Override
    public YxRvbUserColl getYxRvbUsers(Integer rvbId) {
        if (StringUtils.isBlank(YX_HOST)){
            return null;
        }
        String url = YX_HOST + RVB_URL;
        JSONObject body = new JSONObject();
        body.put("rvb_id", rvbId);
        return HttpClientUtil.post(url, body, YxRvbUserColl.class);
    }
}
