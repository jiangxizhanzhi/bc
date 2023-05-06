package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.constant.datasource.ApiRequestType;
import com.cyberpeace.situation.controller.req.ApiConnectDelReq;
import com.cyberpeace.situation.controller.req.ApiConnectReq;
import com.cyberpeace.situation.controller.req.ApiRequestPageReq;
import com.cyberpeace.situation.controller.res.ApiRequestInfoRes;
import com.cyberpeace.situation.controller.res.ApiRequestRes;
import com.cyberpeace.situation.dao.entity.ApiRequest;
import com.cyberpeace.situation.service.ApiRequestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:47
 */
@Api(tags = "数据源管理-API")
@RestController
@RequiresAuthentication
@RequestMapping("/api/request/")
@Slf4j
public class DataSourceAPIController {

	@Autowired
	private ApiRequestService apiRequestService;

	@ApiOperation("API-新增")
	@PostMapping("message")
	public BaseRsp<String>
			createApiConnect(@RequestBody @Validated(Create.class) ApiConnectReq apiConnectReq) {
		return new BaseRsp<>(apiRequestService.createApiRequest(apiConnectReq));
	}

	@ApiOperation("API-修改")
	@PatchMapping("message")
	public BaseRsp<String>
			updateApiConnect(@RequestBody @Validated(Update.class) ApiConnectReq apiConnectReq) {
		return new BaseRsp<>(apiRequestService.updateApiConnect(apiConnectReq));
	}

	@ApiOperation("API-单个查询")
	@GetMapping("message")
	public BaseRsp<ApiRequestInfoRes>
			getApiConnectInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(apiRequestService.getApiConnectInfo(resourceId));
	}

	@ApiOperation("API-批量删除")
	@DeleteMapping("message")
	public BaseRsp<Integer>
			deleteApiConnect(@RequestBody @Validated ApiConnectDelReq apiConnectDelReq) {
		return new BaseRsp<>(
				apiRequestService.deleteApiConnect(apiConnectDelReq.getApiConnectIdList()));
	}

	@ApiOperation("API-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<ApiRequestRes>> getApiConnectPage(ApiRequestPageReq apiRequestPageReq) {
		return new BaseRsp<>(apiRequestService.getApiConnectPage(apiRequestPageReq));
	}

	@ApiOperation("API-列表查询（全量）")
	@GetMapping("/list/all/")
	public BaseRsp<List<ApiRequest>> getAllApi() {
		return new BaseRsp<>(apiRequestService.getAllApi());
	}

	@ApiOperation("数据测试")
	@PostMapping("test")
	public BaseRsp<String> apiConnectTest(@RequestBody @Validated ApiConnectReq apiConnectReq) {
		return new BaseRsp<>(apiRequestService.apiConnectTest(apiConnectReq));
	}

	@ApiOperation("获取请求方式选项")
	@GetMapping("type/list")
	public BaseRsp<List<String>> getRequestType() {
		return new BaseRsp<>(ApiRequestType.getRequestType());
	}

	@ApiOperation("获取API原始返回数据")
	@PostMapping("source/data")
	public BaseRsp<JSONObject>
			getConnectSourceData(@RequestBody @Validated ApiConnectReq apiConnectReq) {
		return new BaseRsp<>(
				JSONObject.parseObject(apiRequestService.apiConnectTest(apiConnectReq)));
	}

	@ApiOperation("连接测试")
	@PostMapping("list/test")
	public BaseRsp<String> apiConnectTest(@RequestParam("resource_id") @ApiParam(required = true,
			name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(apiRequestService.apiConnectTest(resourceId));
	}
}
