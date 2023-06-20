package com.cyberpeace.situation.controller;

import java.util.List;

import com.cyberpeace.situation.controller.req.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cs.controller.CommonController;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.dao.entity.SituationBusinessComponentCategory;
import com.cyberpeace.situation.service.BusinessComponentService;
import com.cyberpeace.situation.service.SituationBusinessComponentCategoryService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

	@Autowired
	private SituationBusinessComponentCategoryService situationBusinessComponentCategoryService;

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
	public BaseRsp<BusinessComponentRes>
			getBusinessComponentInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "资源id") String resourceId) {
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

	/**
	 * 图标组件类型-列表查询
	 *
	 * @param name 类型名称模糊查询条件
	 * @return {@code BaseRsp<List<SituationBusinessComponentCategory>>}
	 */
	@ApiOperation("业务组件类型-列表查询")
	@GetMapping("/category")
	public BaseRsp<List<SituationBusinessComponentCategory>>
			getBusinessComponentCategoryList(@RequestParam(value = "name",
					required = false) @ApiParam(name = "name", value = "名称") String name) {
		return new BaseRsp<>(
				situationBusinessComponentCategoryService.getBusinessComponentCategoryList(name));
	}

	/**
	 * 业务组件类型-修改
	 *
	 * @param businessComponentCategoryReq 业务组件类型请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("业务组件类型-修改")
	@PatchMapping("/category")
	public BaseRsp<String> updateBusinessComponentCategoryCategory(
			@RequestBody @Validated(Update.class) BusinessComponentCategoryReq businessComponentCategoryReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(businessComponentCategoryReq.getName(), null);

		return new BaseRsp<>(situationBusinessComponentCategoryService
				.updateBusinessComponentCategoryCategory(businessComponentCategoryReq));
	}

	/**
	 * 业务组件类型-批量删除
	 *
	 * @param businessComponentDelReq 业务组件类型删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("业务组件类型-批量删除")
	@DeleteMapping("/category")
	public BaseRsp<Integer> deleteBusinessComponentCategoryCategory(
			@RequestBody @Validated BusinessComponentDelReq businessComponentDelReq) {
		return new BaseRsp<>(
				situationBusinessComponentCategoryService.deleteBusinessComponentCategoryCategory(
						businessComponentDelReq.getIdList()));
	}

	/**
	 * 业务组件类型-新增
	 *
	 * @param SituationBusinessComponentCategoryReq 业务组件类型请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("业务组件类型-新增")
	@PostMapping("/category")
	public BaseRsp<String> createBusinessComponentCategoryCategory(
			@RequestBody @Validated(Create.class) BusinessComponentCategoryReq SituationBusinessComponentCategoryReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(SituationBusinessComponentCategoryReq.getName(),
				null);

		return new BaseRsp<>(situationBusinessComponentCategoryService
				.createBusinessComponentCategoryCategory(SituationBusinessComponentCategoryReq));
	}

	/**
	 * 业务组件类型-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationBusinessComponentCategory>}
	 */
	@ApiOperation("业务组件类型-单个查询")
	@GetMapping("/category/info")
	public BaseRsp<SituationBusinessComponentCategory>
			getBusinessComponentCategory(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationBusinessComponentCategoryService
				.getBusinessComponentCategory(resourceId));
	}
}
