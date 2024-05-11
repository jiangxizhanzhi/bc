package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;


@Component
public interface EvalDeviceService {
    JSONArray getDeviceList(String resourceId, Integer category, String name);

    JSONObject getDeviceStatistics(String resourceId);
}
