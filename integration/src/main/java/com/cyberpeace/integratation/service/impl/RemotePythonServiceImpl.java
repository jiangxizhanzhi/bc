package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.model.evaluation.AlgorithmContext;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;
import com.cyberpeace.integratation.service.RemotePythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
public class RemotePythonServiceImpl implements RemotePythonService {

    private String PY_URL;

    @PostConstruct
    public void init() {
        String PY_HOST = SystemPropUtil.getProp("PY_HOST");
        String PY_PORT = SystemPropUtil.getProp("PY_PORT");
        PY_URL = "http://" + PY_HOST + ":" + PY_PORT;
    }

    @Override
    public String getPyHost() {
        return PY_URL;
    }

    private JSONObject buildRequestData(String algorithmId, String algorithmCode, List<Serializable> otherArguments) {
        // 构建传入数据
        JSONObject json = new JSONObject();
        json.put("algorithm_id", algorithmId);
        json.put("code", algorithmCode);
        json.put("other_args", otherArguments);

        return json;
    }

    @Override
    public List<String> formatPerform(String algorithmId, String algorithmCode, FormatData formatData, List<Serializable> otherArguments) {
        JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
        json.put("list_data", formatData.getData());

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObjectArray(resp, String.class);
    }

    @Override
    public List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData, List<Serializable> otherArguments) {
        JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
        json.put("data", matchData);

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObjectArray(resp, Integer.class);
    }

    @Override
    public Integer markPerform(String algorithmId, String algorithmCode, MarkData markData, List<Serializable> otherArguments) {
        JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
        json.put("data", markData);

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObject(resp, Integer.class);
    }

    @Override
    public Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data, List<Serializable> otherArguments) {
        JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
        json.put("data", data);

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObject(resp, Integer.class);
    }
}
