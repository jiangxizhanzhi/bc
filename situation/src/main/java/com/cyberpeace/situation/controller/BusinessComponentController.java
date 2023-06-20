package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.BusinessComponentDelReq;
import com.cyberpeace.situation.controller.req.BusinessComponentPageReq;
import com.cyberpeace.situation.controller.req.BusinessComponentRes;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.CommonController;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.controller.req.BusinessComponentReq;
import com.cyberpeace.situation.service.BusinessComponentService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 态势大屏-业务组件
 *
 * @Author zhangjb
 * @Date 10/16/2023 上午11:58
 */

@Api(tags = "态势大屏-业务组件")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("dashboard/template/business_component")
public class BusinessComponentController {

	@Autowired
	private BusinessComponentService businessComponentService;
	@Autowired
	private CommonController commonController;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 新建业务组件
	 *
	 * @param BusinessComponentReq 业务组件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.XZ)
	@ApiOperation("业务组件-新增")
	@PostMapping()
	public BaseRsp<String> createBusinessComponent(
			@RequestBody @Validated(Create.class) BusinessComponentReq BusinessComponentReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(BusinessComponentReq.getName(), null);

		return new BaseRsp<>(
				businessComponentService.createBusinessComponent(BusinessComponentReq));
	}

	/**
	 * 修改业务组件
	 *
	 * @param BusinessComponentReq 业务组件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.XG)
	@ApiOperation("业务组件-修改")
	@PatchMapping("")
	public BaseRsp<String> updateBusinessComponent(
			@RequestBody @Validated(Update.class) BusinessComponentReq BusinessComponentReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(BusinessComponentReq.getName(), null);

		return new BaseRsp<>(
				businessComponentService.updateBusinessComponent(BusinessComponentReq));
	}

/*	*//**
	 * 查看业务组件详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<BusinessComponentRes>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.CX)
	@ApiOperation("业务组件-单个查询")
	@GetMapping("")
	public BaseRsp<BusinessComponentRes> getBusinessComponentInfo(
			@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id",
					value = "资源id") String resourceId) {
		return new BaseRsp<>(
				businessComponentService.getBusinessComponentInfo(resourceId, null, Boolean.FALSE));
	}

	/**
	 * 删除业务组件
	 *
	 * @param BusinessComponentDelReq 业务组件删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.SC)
	@ApiOperation("原始模板-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteBusinessComponent(
			@RequestBody @Validated BusinessComponentDelReq BusinessComponentDelReq) {
		return new BaseRsp<>(businessComponentService
				.deleteBusinessComponent(BusinessComponentDelReq.getIdList()));
	}

	/**
	 * 查询业务组件列表
	 *
	 * @param BusinessComponentPageReq 业务组件分页请求
	 * @return {@code BaseRsp<ListRsp<BusinessComponentPageRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.CX)
	@ApiOperation("业务组件-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<BusinessComponentRes>>
	getBusinessComponentPage(BusinessComponentPageReq BusinessComponentPageReq) {
		return new BaseRsp<>(
				businessComponentService.getBusinessComponentPage(BusinessComponentPageReq));
	}
}
