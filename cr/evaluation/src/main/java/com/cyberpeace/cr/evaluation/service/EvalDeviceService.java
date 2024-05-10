package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Component;


@Component
public interface EvalDeviceService {
    JSONArray getDeviceList(String resourceId, Integer category, String name);
}
