package com.cyberpeace.integratation.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;
import com.cyberpeace.integratation.service.RemotePythonService;

import lombok.extern.slf4j.Slf4j;

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

	private JSONObject buildRequestData(String algorithmId, String algorithmCode,
			List<Serializable> otherArguments) {
		// 构建传入数据
		JSONObject json = new JSONObject();
		json.put("algorithm_id", algorithmId);
		json.put("code", algorithmCode);
		json.put("other_args", otherArguments);

		return json;
	}

	private String invoke(JSONObject requestData) {
		String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", requestData,
				String.class);
		if (resp == null) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}

		return resp;
	}

	@Override
	public List<String> formatPerform(String algorithmId, String algorithmCode,
			FormatData formatData, List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("list_data", formatData.getData());

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		List<String> results = JsonUtil.toJsonObjectArray(resp, String.class);
		if (results.contains(null)) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}

		return results;
	}

	@Override
	public List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", matchData);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObjectArray(resp, Integer.class);
	}

	@Override
	public Integer markPerform(String algorithmId, String algorithmCode, MarkData markData,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", markData);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	@Override
	public Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	@Override
	public Integer indexAhpPerform(String algorithmId, String matrixData, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);
		json.put("matrix_data", getFormatMatrixData(matrixData));

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	public List<List<Integer>> getFormatMatrixData(String matrixData){
		JSONArray objects = JSONArray.parseArray(matrixData);
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			JSONObject jsonObject = objects.getJSONObject(i);
			String data = jsonObject.get("data").toString();
			JSONObject dataJsonObj = JsonUtil.toJsonObject(data, JSONObject.class);
			Collection<Object> values = dataJsonObj.values();
			Iterator<Object> iterator = values.iterator();
			List<Integer> tmp = new ArrayList<>();
			while (iterator.hasNext()){
				Object next = iterator.next();
				if(null == next){
					continue;
				}
				String str = String.valueOf(next);
				if(StringUtils.isBlank(str)){
					continue;
				}
				double ceil = Math.ceil(Double.parseDouble(str));
				int intValue = (int) ceil;
				tmp.add(intValue);
			}
			res.add(tmp);

		}
		return res;
	}
}
