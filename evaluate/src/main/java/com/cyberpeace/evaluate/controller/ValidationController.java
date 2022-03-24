package com.cyberpeace.evaluate.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.evaluate.controller.res.TestInfoRsp;
import com.cyberpeace.evaluate.dao.entity.TestInfo;
import com.cyberpeace.evaluate.service.ValidationService;
import com.cyberpeace.evaluate.service.bo.TestRecordBO;
import com.cyberpeace.integratation.service.CrService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "es-系统评估相关api")
@Slf4j
@RestController
@RequestMapping("/validation/info")
@RequiresAuthentication
public class ValidationController {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private CrService crService;

	@GetMapping("/list")
	@ApiOperation("分页查询用户的系统评估")
	public BaseRsp<ListRsp> list(PageReqBase pageReq) {

		Page pageBase = pageReq.toPageParam();

		String userId = UserInfoContext.getUserContext().getUserId();

		ListRsp<TestInfo> infos = validationService.pageInfo(userId, pageBase);

		List<TestInfoRsp> data = Lists.newArrayList();

		infos.getList().forEach(info -> {
			data.add(TestInfoRsp.builder().desc(info.getInfoDesc()).infoId(info.getInfoId())
					.logo(info.getLogoId()).name(info.getInfoName()).schemeId(info.getSchemeId())
					.build());
		});
		ListRsp<TestInfoRsp> listRsp = new ListRsp<>();
		listRsp.setList(data);
		listRsp.setTotal(infos.getTotal());

		return new BaseRsp<>(listRsp);
	}

	@DeleteMapping("/{info_id}")
	@ApiOperation("删除系统评估")
	public BaseRsp deleteInfo(@ApiParam("系统评估id") @PathVariable("info_id") String infoId) {
		validationService.deleteTestInfo(infoId);

		return new BaseRsp();
	}

	@PostMapping()
	@ApiOperation("新增系统评估")
	public BaseRsp<String> add(HttpServletRequest request) {
		try {
			// 获取数据的实际报文数据
			String input = request.getReader().lines()
					.collect(Collectors.joining(System.lineSeparator()));
			JSONObject jsonObject = (JSONObject) JSONObject.parse(input);

			TestInfo testInfo = new TestInfo();
			testInfo.setInfoId(IDGeneratorUtil.getID());
			testInfo.setUserId(UserInfoContext.getUserContext().getUserId());
			testInfo.setInfoDesc(jsonObject.getString("info_desc"));
			testInfo.setInfoName(jsonObject.getString("info_name"));
			testInfo.setLogoId(jsonObject.getString("logo_id"));
			testInfo.setSchemeId(jsonObject.getString("scheme_id"));
			String topoId = crService
					.saveSceneTopo(jsonObject.getJSONObject("topo_id").toJSONString());
			testInfo.setTopoId(topoId);

			validationService.addTestInfo(testInfo);

			BaseRsp<String> rsp = new BaseRsp<>();
			rsp.setData(testInfo.getInfoId());

			return rsp;
		} catch (IOException e) {
			log.error("add testinfo error:", e);
			throw new RuntimeException();
		}
	}

	@GetMapping("/record/{info_id}")
	@ApiOperation("评估历史记录")
	public BaseRsp<TestRecordBO>
			getTestRecord(@ApiParam("评估id") @PathVariable("info_id") String infoId) {

		Preconditions.checkArgument(StringUtils.isNotBlank(infoId), "info_id null");
		TestRecordBO testRecordBO = validationService.getTestRecord(infoId);
		BaseRsp<TestRecordBO> rsp = new BaseRsp<>();
		rsp.setData(testRecordBO);
		return rsp;
	}

}
