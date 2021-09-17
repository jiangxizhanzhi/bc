package com.cyberpeace.integratation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cyberpeace.common.model.evaluation.TimeSequenceValue;
import com.cyberpeace.common.util.LocalClient;
import com.cyberpeace.integratation.model.evaluation.FormatDataRsp;
import com.cyberpeace.integratation.service.RemotePythonRuntimeService;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.util.ConfigUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemotePythonRuntimeServiceImpl implements RemotePythonRuntimeService {

	@Override
	public List<TimeSequenceValue> formatProbeData(String sceneId, String hostId, String collectId,
												   String pythonScript, Date startTime, Date endTime, List<String> selfDefineValue) {
		long start = System.currentTimeMillis();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BasicNameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("code", pythonScript));
		nvps.add(new BasicNameValuePair("scene_hash", sceneId));
		nvps.add(new BasicNameValuePair("server_id", hostId));
		nvps.add(new BasicNameValuePair("probe_hash", collectId));
		nvps.add(new BasicNameValuePair("start_time", dateFormat.format(startTime)));
		nvps.add(new BasicNameValuePair("end_time", dateFormat.format(endTime)));
		nvps.add(new BasicNameValuePair("args", JSON.toJSONString(selfDefineValue)));

		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.probe.run.solution");

		FormatDataRsp ret = LocalClient.postWithForm(nvps, url, FormatDataRsp.class);

		List<TimeSequenceValue> retDateList = Lists.newArrayList();
		if (!ret.getData().isEmpty()) {
			ret.getData().forEach(data -> {
				TimeSequenceValue retData = new TimeSequenceValue();
				try {
					retData.setTime(dateFormat.parse(data.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				retData.setValue(data.getValue());
				retDateList.add(retData);
			});
		}

		log.info(
				"formatProbeData, sceneId=[{}], hostId=[{}], collectId=[{}], startTime=[{}], endTime=[{}], timeCost=[{}]",
				new Object[] { sceneId, hostId, collectId, startTime, endTime,
						(System.currentTimeMillis() - start) });

		return retDateList;
	}

	@Override
	public List<TimeSequenceValue> modelDetection(List<TimeSequenceValue> formattedData,
			List<String> selfDefineValue, String pythonScript) {

		String result = commonRunner(pythonScript, formattedData, selfDefineValue);
		// String resultValue = JSON.parseObject(result).getString("result");
		List<TimeSequenceValue> retDateList = JSONArray.parseArray(result, TimeSequenceValue.class);
		return retDateList;
	}

	@Override
	public Integer calculateEstimateVariable(List<TimeSequenceValue> modelDetectionData,
			List<TimeSequenceValue> originData, List<String> selfDefineValue, String pythonScript) {
		long start = System.currentTimeMillis();
		String result = commonRunner(pythonScript, modelDetectionData, originData, selfDefineValue);
		// return JSON.parseObject(result).getInteger("result");

		log.info("calculateEstimateVariable timeCost=[{}]", (System.currentTimeMillis() - start));
		return parseInt(result);
	}

	@Override
	public Integer aggregation(List<Integer> variables, List<String> selfDefineValue,
			String pythonScript) {
		String pythonResult = commonRunner(pythonScript, variables, selfDefineValue);

		return parseInt(pythonResult);
	}

	@Override
	public String runGeneralScript(List<String> selfDefineValue, String pythonScript) {
		return commonRunner(pythonScript, selfDefineValue);
	}

	@Override
	public String runToolScript(List<String> selfDefineValue, List<String> toolRsp,
			String actionId) {
		List<List<String>> params = Lists.newArrayList();
		params.add(selfDefineValue);
		params.add(toolRsp);

		List<BasicNameValuePair> nvps = Lists.newArrayList();

		if (CollectionUtils.isNotEmpty(selfDefineValue)) {
			nvps.add(new BasicNameValuePair("second_args", JSON.toJSONString(selfDefineValue)));
		}

		if (CollectionUtils.isNotEmpty(toolRsp)) {
			String toolRspString = JSON.toJSONString(toolRsp);
			nvps.add(new BasicNameValuePair("third_args", toolRspString));
		}

		nvps.add(new BasicNameValuePair("action_hash", actionId));

		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.run.tool");

		String result = LocalClient.postWithForm(nvps, url, String.class);

		return result;
	}

	private Integer parseInt(String pythonResult) {
		Integer result = null;
		try {
			result = Integer.parseInt(pythonResult);
		} catch (Exception e) {
			log.error("aggregation error");
			// todo
			// 终止当前计算任务
		}

		return result;
	}

	private String commonRunner(String pythonScript, Object... args) {
		List<Object> params = Lists.newArrayList();
		if (null != args && args.length > 0) {
			for (Object arg : args) {
				params.add(arg);
			}
		}

		List<BasicNameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("args", JSON.toJSONString(params)));
		// nvps.add(new BasicNameValuePair("args", JSON.toJSONString(params)));
		nvps.add(new BasicNameValuePair("main_func", "solution"));
		nvps.add(new BasicNameValuePair("kwargs", JSON.toJSONString(new HashMap<>())));
		nvps.add(new BasicNameValuePair("code", pythonScript));

		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.run.common.code");

		String result = LocalClient.postWithForm(nvps, url, String.class);

		return result;
	}
}
