package com.cyberpeace.oj.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.common.exception.RemoteUserAuthticationException;
import com.cyberpeace.oj.common.model.UserInfo;
import com.cyberpeace.oj.common.util.ConfigUtil;
import com.cyberpeace.oj.integratation.service.RemoteUserService;
import com.cyberpeace.oj.integratation.service.mp.HttpClient;
import com.cyberpeace.oj.integratation.service.mp.HttpClientException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteUserServiceImpl implements RemoteUserService {

    private String TOKEN_AUTHENTICATE_URL;

    private String USERINFO_BY_ID_URL;

    private String USERINFO_LIST_URL;

    @PostConstruct
    public void init() {
        TOKEN_AUTHENTICATE_URL = ConfigUtil.getContextProperty("token.authenticate");
        USERINFO_BY_ID_URL = ConfigUtil.getContextProperty("userinfo.by.id");
        USERINFO_LIST_URL = ConfigUtil.getContextProperty("userinfo.list");
    }

    @Override
    public UserInfo doAuthentication(String token) throws RemoteUserAuthticationException {
        JSONObject jsonObject;
        HttpClient httpClient = new HttpClient();
        try {
            jsonObject = httpClient.get("/api/mp/unauth/authenticate?token=" + token);
        } catch (HttpClientException e) {
            throw new RemoteUserAuthticationException();
        }
        return this.userInfoFromJsonObject(jsonObject);
    }

    @Override
    public UserInfo getById(String userId) {
        HttpClient httpClient = new HttpClient();
        JSONObject jsonObject = httpClient.get("/api/mp/auth/users/" + userId);
        return this.userInfoFromJsonObject(jsonObject);
    }

    @Override
    public List<UserInfo> getList() {
        HttpClient httpClient = new HttpClient();
        JSONArray jsonArray = httpClient.getList("/api/mp/auth/org_users/page/byorg");
        List<UserInfo> userInfoList = new ArrayList<>();
        for (Object object : jsonArray) {
            userInfoList.add(this.userInfoFromJsonObject((JSONObject) object));
        }
        return userInfoList;
    }

    private UserInfo userInfoFromJsonObject(JSONObject jsonObject) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(jsonObject.getObject("id", String.class));
        userInfo.setUserName(jsonObject.getObject("username", String.class));
        userInfo.setAvatarUrl(jsonObject.getObject("avatar", String.class));
        List<String> org_ids = jsonObject.getJSONArray("org_ids").toJavaList(String.class);
        if (org_ids.size() > 0) {
            userInfo.setOrganizationId(org_ids.get(0));
            // userInfo.setOrganizationData("");
        }
        // userInfo.setRoles();
        userInfo.setAdmin((int) jsonObject.get("is_admin") == 0);
        userInfo.setSuperuser((int) jsonObject.get("is_super_user") == 0);
        return userInfo;
    }

}
