package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.req.ColorThemeDelReq;
import com.cyberpeace.situation.controller.req.ComponentLayerReq;
import com.cyberpeace.situation.controller.res.ComponentLayerRes;
import com.cyberpeace.situation.service.SituationComponentLayerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午9:34
 */

@Api(tags = "态势大屏-组件图层管理")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/layer")
public class DashboardLayerController {

	@Autowired
	private SituationComponentLayerService situationComponentLayerService;

	@ApiOperation("组件图层-新增")
	@PostMapping()
	public BaseRsp<String> addComponentLayer(
			@RequestBody @Validated(Create.class) List<ComponentLayerReq> componentLayerReqList) {

		return new BaseRsp(situationComponentLayerService.addComponentLayer(componentLayerReqList));
	}

	@ApiOperation("组件图层-修改")
	@PatchMapping("")
	public BaseRsp<String> updateComponentLayer(
			@RequestBody @Validated(Update.class) List<ComponentLayerReq> componentLayerReqList) {
		return new BaseRsp<>(
				situationComponentLayerService.updateComponentLayer(componentLayerReqList));
	}

	@ApiOperation("组件图层-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer>
			deleteComponentLayer(@RequestBody @Validated ColorThemeDelReq colorThemeDelReq) {
		return new BaseRsp<>(
				situationComponentLayerService.deleteComponentLayer(colorThemeDelReq.getIdList()));
	}

	@ApiOperation("组件图层-列表查询")
	@GetMapping("")
	public BaseRsp<List<ComponentLayerRes>> getComponentLayer(@ApiParam(required = true,
			name = "resource_id", value = "资源id") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationComponentLayerService.getComponentLayer(resourceId));
	}
}
