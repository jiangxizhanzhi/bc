package com.cyberpeace.iov.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.iov.dao.mapper.DutMapper;
import com.cyberpeace.iov.service.IovIndexService;
import com.cyberpeace.iov.service.bo.index.RightDemo;
import com.cyberpeace.iov.service.bo.index.TargetCountBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovIndexServiceImpl implements IovIndexService {

	@Autowired
	private DutMapper dutMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Override
	public RightDemo getDemo(HttpServletRequest request) {
		RightDemo res = new RightDemo();
		String demoFilePath = "/WEB-INF/classes/static/demonstration/iov_index.json";
		StringBuilder builder = new StringBuilder();
		try (InputStream stream = request.getServletContext().getResourceAsStream(demoFilePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			String content = "";
			while ((content = br.readLine()) != null) {
				builder.append(content).append(System.lineSeparator());
			}
			String demoJson = builder.toString();
			res = JsonUtil.toJsonObject(demoJson, RightDemo.class);
		} catch (Exception e) {
			log.error("index demo file doesn't exist");
		}
		return res;
	}

	@Override
	public TargetCountBo getTargetCount() {
		TargetCountBo res = new TargetCountBo();
		Integer dutCount = dutMapper.selectCount(new LambdaQueryWrapper<>());
		JSONObject remoteRsp = remoteSceneService.getResourceStatistics();
		res.setEntity(dutCount);

		return res;
	}

}
