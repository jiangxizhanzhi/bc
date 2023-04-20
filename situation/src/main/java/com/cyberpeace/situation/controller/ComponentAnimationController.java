package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationDeleteReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsAnimationRes;
import com.cyberpeace.situation.controller.res.SituationComponentsChartDetailsRes;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import com.cyberpeace.situation.service.SituationComponentAnimationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "组件管理-动画组件")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/animation")
public class ComponentAnimationController {

	@Autowired
	private SituationComponentAnimationService situationComponentAnimationService;

	@ApiOperation("新增模板动画")
	@PostMapping()
	public BaseRsp<String> addComponentAnimation(
			@RequestBody @Validated(Create.class) SituationComponentsAnimationAddReq situationComponentsAnimationAddReq) {

		return new BaseRsp(situationComponentAnimationService
				.addComponentAnimation(situationComponentsAnimationAddReq));
	}

	@ApiOperation("修改模板动画")
	@PatchMapping("")
	public BaseRsp<SituationComponentsAnimationRes> updateComponentAnimation(
			@RequestBody @Validated(Update.class) SituationComponentsAnimationUpdateReq situationComponentsAnimationUpdateReq) {
		return new BaseRsp<>(situationComponentAnimationService
				.updateComponentAnimation(situationComponentsAnimationUpdateReq));
	}

	@ApiOperation("删除动画")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteComponentAnimation(
			@RequestBody @Validated SituationComponentsAnimationDeleteReq deleteReq) {
		return new BaseRsp<>(situationComponentAnimationService
				.deleteComponentAnimation(deleteReq.getResourceIds()));
	}

	@ApiOperation("获取动画列表")
	@GetMapping("/list")
	public BaseRsp<List<SituationComponentsAnimationRes>>
			getComponentAnimations(@ApiParam @Validated SituationComponentsAnimationReq req) {
		return new BaseRsp<>(situationComponentAnimationService.getComponentAnimations(req));
	}

	@ApiOperation("获取动画详情")
	@GetMapping("")
	public BaseRsp<SituationComponentsAnimation> getComponentAnimation(@ApiParam(required = true,
			name = "resource_id", value = "资源id") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationComponentAnimationService.getComponentAnimation(resourceId));
	}

	@ApiOperation("获取二维组件详情")
	@GetMapping("chart/detail")
	public BaseRsp<List<SituationComponentsChartDetailsRes>> getChartDetails() {
		return new BaseRsp<>(situationComponentAnimationService.getChartDetails());
	}
}
