package com.cyberpeace.integratation.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
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

/**
 * 远程蟒Service实现
 *
 * @author SN
 * @version 1.0.0
 * @see RemotePythonService
 * @since 1.0.0@date 2024/11/14
 */
@Slf4j
@Service
public class RemotePythonServiceImpl implements RemotePythonService {

	/**
	 * python 网址
	 *
	 * @see String
	 */
	private String PY_URL;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		String PY_HOST = SystemPropUtil.getProp("PY_HOST");
		String PY_PORT = SystemPropUtil.getProp("PY_PORT");
		PY_URL = "http://" + PY_HOST + ":" + PY_PORT;
	}

	/**
	 * 获取python主机
	 *
	 * @throws
	 * @see String@return {@code String }
	 */
	@Override
	public String getPyHost() {
		return PY_URL;
	}

	/**
	 * 生成请求数据
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param otherArguments 其他参数
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	private JSONObject buildRequestData(String algorithmId, String algorithmCode,
			List<Serializable> otherArguments) {
		// 构建传入数据
		JSONObject json = new JSONObject();
		json.put("algorithm_id", algorithmId);
		json.put("code", algorithmCode);
		json.put("other_args", otherArguments);

		return json;
	}

	/**
	 * 调用
	 *
	 * @param requestData 请求数据
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see JSONObject
	 */
	private String invoke(JSONObject requestData) {
		String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", requestData,
				String.class);
		if (resp == null) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}

		return resp;
	}

	/**
	 * format perform
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param formatData     设置数据格式
	 * @param otherArguments 其他参数
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
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

	/**
	 * match perform
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param matchData      匹配数据
	 * @param otherArguments 其他参数
	 * @return {@code List<Integer> }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
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

	/**
	 * 标记执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param markData       标记数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
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

	/**
	 * 编号执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param data           数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
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

	/**
	 * 编号AHP执行
	 *
	 * @param algorithmId    算法id
	 * @param matrixData     矩阵数据
	 * @param algorithmCode  算法代号
	 * @param data           数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	@Override
	public Integer indexAhpPerform(String algorithmId, String matrixData, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);
		json.put("matrix_data", getFormatMatrixData(matrixData, data));

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	/**
	 * 获取格式矩阵数据
	 *
	 * @param matrixData 矩阵数据
	 * @param scores     分数
	 * @return {@code List<List<String>> }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	public List<List<String>> getFormatMatrixData(String matrixData, List<Integer> scores){
		JSONArray objects = JSON.parseArray(matrixData);
		List<List<String>> res = new ArrayList<>();
		if(objects.size() != scores.size()){
			throw new RemoteServiceException(ErrorCode.UNFIT_MATRIX_DATA);
		}
		for (int i = 0; i < objects.size(); i++) {
			JSONObject jsonObject = objects.getJSONObject(i);
			String data = jsonObject.get("data").toString();
			JSONObject dataJsonObj = JsonUtil.toJsonObject(data, JSONObject.class);
			Collection<Object> values = dataJsonObj.values();
			Iterator<Object> iterator = values.iterator();
			List<String> tmp = new ArrayList<>();
			while (iterator.hasNext()){
				Object next = iterator.next();
				if(null == next){
					continue;
				}
				String str = String.valueOf(next);
				if(StringUtils.isBlank(str)){
					continue;
				}
				tmp.add(str);
			}
			res.add(tmp);

		}
		return res;
	}
}
