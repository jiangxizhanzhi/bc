package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.manage.ServiceLogContext.SubModule;
import com.cyberpeace.integratation.model.manage.ServiceLogContext.Type;
import com.cyberpeace.integratation.service.RemoteManageService;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationDeleteReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsAnimationRes;
import com.cyberpeace.situation.controller.res.SituationComponentsChartDetailsRes;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import com.cyberpeace.situation.service.SituationComponentAnimationService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 组件管理-动画组件
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "组件管理-动画组件")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/animation")
public class ComponentAnimationController {

	@Autowired
	private SituationComponentAnimationService situationComponentAnimationService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private RemoteManageService remoteManageService;

	/**
	 * 动画组件-新增
	 *
	 * @param situationComponentsAnimationAddReq 动画组件新增请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = SubModule.DHZJ, type = Type.XZ)
	@ApiOperation("动画组件-新增")
	@PostMapping()
	public BaseRsp<String> addComponentAnimation(
			@RequestBody @Validated(Create.class) SituationComponentsAnimationAddReq situationComponentsAnimationAddReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationComponentsAnimationAddReq.getName(),
				situationComponentsAnimationAddReq.getDescription());

		String resourceId = situationComponentAnimationService
				.addComponentAnimation(situationComponentsAnimationAddReq);

		return new BaseRsp(resourceId);
	}

	/**
	 * 动画组件-修改
	 *
	 * @param situationComponentsAnimationUpdateReq 动画组件修改请求
	 * @return {@code BaseRsp<SituationComponentsAnimationRes>}
	 */
	@AuditLogger(subModule = SubModule.DHZJ, type = Type.XG)
	@ApiOperation("动画组件-修改")
	@PatchMapping("")
	public BaseRsp<SituationComponentsAnimationRes> updateComponentAnimation(
			@RequestBody @Validated(Update.class) SituationComponentsAnimationUpdateReq situationComponentsAnimationUpdateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationComponentsAnimationUpdateReq.getName(),
				situationComponentsAnimationUpdateReq.getDescription());

		return new BaseRsp<>(situationComponentAnimationService
				.updateComponentAnimation(situationComponentsAnimationUpdateReq));
	}

	/**
	 * 动画组件-删除
	 *
	 * @param deleteReq 动画组件删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = SubModule.DHZJ, type = Type.SC)
	@ApiOperation("动画组件-删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteComponentAnimation(
			@RequestBody @Validated SituationComponentsAnimationDeleteReq deleteReq) {
		return new BaseRsp<>(situationComponentAnimationService
				.deleteComponentAnimation(deleteReq.getResourceIds()));
	}

	/**
	 * 动画组件-列表查询
	 *
	 * @param req 动画组件列表查询请求
	 * @return {@code BaseRsp<List<SituationComponentsAnimationRes>>}
	 */
	@AuditLogger(subModule = SubModule.DHZJ, type = Type.CX)
	@ApiOperation("动画组件-列表查询")
	@GetMapping("/list")
	public BaseRsp<List<SituationComponentsAnimationRes>>
			getComponentAnimations(@ApiParam @Validated SituationComponentsAnimationReq req) {
		return new BaseRsp<>(situationComponentAnimationService.getComponentAnimations(req));
	}

	/**
	 * 动画组件-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationComponentsAnimation>}
	 */
	@AuditLogger(subModule = SubModule.DHZJ, type = Type.CX)
	@ApiOperation("动画组件-单个查询")
	@GetMapping("")
	public BaseRsp<SituationComponentsAnimation> getComponentAnimation(@ApiParam(required = true,
			name = "resource_id", value = "资源id") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationComponentAnimationService.getComponentAnimation(resourceId));
	}

	/**
	 * 获取二维组件详情
	 *
	 * @return {@code BaseRsp<List<SituationComponentsChartDetailsRes>>}
	 */
	@AuditLogger(subModule = SubModule.TBZJ, type = Type.CX)
	@ApiOperation("获取二维组件详情")
	@GetMapping("chart/detail")
	public BaseRsp<List<SituationComponentsChartDetailsRes>> getChartDetails() {
		return new BaseRsp<>(situationComponentAnimationService.getChartDetails());
	}
}
