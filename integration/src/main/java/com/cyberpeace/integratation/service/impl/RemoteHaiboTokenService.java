package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteHaiboTokenService {

    private String HAIBO_TOKEN_ANALYSE;

    private static RemoteHaiboTokenService INSTANCE;

    public static List<String> getValidCourses(String haiboToken) {
        return INSTANCE.doGetValidCourses(haiboToken);
    }

    @PostConstruct
    public void init() {
        INSTANCE = this;
        this.HAIBO_TOKEN_ANALYSE = ConfigUtil.getContextProperty("haibo.token.analyse");
    }

    private List<String> doGetValidCourses(String haiboToken) {
//        String targetUrl = String.format(HAIBO_TOKEN_ANALYSE, haiboToken);
//        JSONObject jsonObject = HttpClientUtil.rawGet(targetUrl, JSONObject.class);
//        HaiboUser haiboUser = BeanUtils.deepCopy(jsonObject.get("data"), HaiboUser.class);
//        List<String> result = new ArrayList<>();
//        result.add("aaaaaaaaaaaaaaaaaaaaa");
//        if (haiboUser.getCourses() != null) {
//            result.addAll(haiboUser.getCourses());
//        }
//        return result;


        List<String> result = new ArrayList<>();
        result.add("aaaaaaaaaaaaaaaaaaaaa");
        result.add("NS0600WNS");
        return result;
    }

    @Getter
    @Setter
    private static class HaiboUser {
        private String userId;
        private String name;
        private String token;
        private String username;
        private List<String> courses ;
    }
}
