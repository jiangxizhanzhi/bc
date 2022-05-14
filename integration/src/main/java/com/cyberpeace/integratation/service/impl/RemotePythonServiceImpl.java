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

    @Override
    public List<String> formatPerform(AlgorithmContext context, String algorithmId, String algorithmCode, FormatData formatData, List<Serializable> otherArguments) {
        // 构建传入数据
        JSONObject json = new JSONObject();
        json.put("context", context);
        json.put("algorithm_id", algorithmId);
        json.put("algorithm_code", algorithmCode);
        json.put("list_data", formatData.getData());
        json.put("other_args", otherArguments);

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObjectArray(resp, String.class);
    }

    @Override
    public List<Integer> matchPerform(AlgorithmContext context, String algorithmId, String algorithmCode, MatchData matchData, List<Serializable> otherArguments) {
        JSONObject json = new JSONObject();
        json.put("context", context);
        json.put("algorithm_id", algorithmId);
        json.put("algorithm_code", algorithmCode);
        json.put("data", matchData);
        json.put("other_args", otherArguments);

        String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, String.class);
        return JsonUtil.toJsonObjectArray(resp, Integer.class);
    }

    @Override
    public Integer markPerform(AlgorithmContext context, String algorithmId, String algorithmCode, MarkData markData, List<Serializable> otherArguments) {
        JSONObject json = new JSONObject();
        json.put("context", context);
        json.put("algorithm_id", algorithmId);
        json.put("algorithm_code", algorithmCode);
        json.put("data", markData);
        json.put("other_args", otherArguments);

        return HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, Integer.class);
    }

    @Override
    public Integer indexPerform(AlgorithmContext context, String algorithmId, String algorithmCode, List<Integer> data, List<Serializable> otherArguments) {
        JSONObject json = new JSONObject();
        json.put("context", context);
        json.put("algorithm_id", algorithmId);
        json.put("algorithm_code", algorithmCode);
        json.put("data", data);
        json.put("other_args", otherArguments);

        return HttpClientUtil.post(getPyHost() + "/algorithm/run/", json, Integer.class);

    }
}
