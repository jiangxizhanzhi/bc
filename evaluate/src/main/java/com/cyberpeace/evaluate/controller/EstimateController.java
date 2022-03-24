package com.cyberpeace.evaluate.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.evaluate.controller.res.EstimateVariableConfigBody;
import com.cyberpeace.evaluate.dao.entity.EstimateInfo;
import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;
import com.cyberpeace.evaluate.service.EstimateInfoService;
import com.cyberpeace.evaluate.service.context.EstimateInfoContext;
import com.cyberpeace.integratation.service.CrService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "es-装备评估相关api")
@RestController
@Slf4j
@RequestMapping("/estimate/")
@RequiresAuthentication
public class EstimateController {

	@Autowired
	private EstimateInfoService estimateInfoService;

	@Autowired
	private CrService crService;

	/**
	 * 新增评估
	 * */
	@PostMapping("/create")
	@ApiOperation("新增评估")
	public BaseRsp<String> createEstimate(HttpServletRequest request) {

		EstimateInfoContext estimateInfoContext = parseEstimateInfoBody(request);
		String estimateId = estimateInfoService.addEstimateInfo(estimateInfoContext);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(estimateId);
		return rsp;
	}

	@GetMapping("/delete")
	@ApiOperation("删除评估")
	public BaseRsp
			deleteEstimate(@ApiParam("评估id") @RequestParam("estimate_id") String estimateId) {
		estimateInfoService.deleteEstimate(estimateId);
		return new BaseRsp();
	}

	/**
	 * 分页查询评估
	 *
	 * */
	@GetMapping("/query")
	@ApiOperation("分页查询用户创建的评估")
	public ListRsp<EstimateInfo> queryEstimates(PageReqBase pageReq) {

		Page pageBase = pageReq.toPageParam();
		ListRsp<EstimateInfo> estimateInfoList = estimateInfoService.queryEstimates(pageBase);

		return estimateInfoList;
	}

	/**
	 * 分页查询评估
	 *
	 * */
	@GetMapping("/get")
	@ApiOperation("查询装备评估信息")
	public BaseRsp<EstimateInfo>
			getEstimate(@ApiParam("评估编号") @RequestParam("estimate_id") String estimateId) {

		EstimateInfo retData = estimateInfoService.getEstimates(estimateId);

		BaseRsp<EstimateInfo> rsp = new BaseRsp<>();
		rsp.setData(retData);

		return rsp;
	}

	private EstimateInfoContext parseEstimateInfoBody(HttpServletRequest request) {
		EstimateInfoContext estimateInfoContext = new EstimateInfoContext();
		try {

			// 获取数据的实际报文数据
			String input = request.getReader().lines()
					.collect(Collectors.joining(System.lineSeparator()));
			JSONObject jsonObject = (JSONObject) JSONObject.parse(input);

			estimateInfoContext.setEstimateSchemeId(jsonObject.getString("estimate_scheme_id"));
			List<EstimateVariableConfigBody> variableConfigs = JSON.parseArray(
					jsonObject.getString("variable_configs"), EstimateVariableConfigBody.class);
			estimateInfoContext.setDesc(jsonObject.getString("desc"));
			estimateInfoContext.setLogo(jsonObject.getString("logo"));
			estimateInfoContext.setName(jsonObject.getString("name"));

			// topoId需要重置
			String topoId = crService
					.saveSceneTopo(jsonObject.getJSONObject("topo_id").toJSONString());
			estimateInfoContext.setTopoId(topoId);

			List<EstimateVariableLinks> links = new ArrayList<>();
			variableConfigs.forEach(config -> {
				EstimateVariableLinks link = new EstimateVariableLinks();
				link.setCollectId(config.getCollectId());
				link.setEstimateVariableId(config.getEstimateVariableId());
				link.setHostId(config.getHostId());
				links.add(link);
			});
			estimateInfoContext.setEstimateVariableLinks(links);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException();
		}
		return estimateInfoContext;
	}

}
