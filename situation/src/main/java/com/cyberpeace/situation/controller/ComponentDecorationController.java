package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.controller.req.SituationDecorationPageReq;
import com.cyberpeace.situation.controller.req.SituationDecorationReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.res.SituationDecorationPageRes;
import com.cyberpeace.situation.dao.entity.SituationDecoration;
import com.cyberpeace.situation.service.SituationDecorationService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 组件管理-显示控件
 *
 * @Author xuxz
 * @Date 4/1/2023 上午11:11
 */
@Api(tags = "组件管理-显示控件")
@RestController
@Slf4j
@RequestMapping("/situation/decoration")
public class ComponentDecorationController {

	@Autowired
	private SituationDecorationService situationDecorationService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 显示控件-新增
	 *
	 * @param situationDecorationReq 显示控件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.XSKJ, type = ServiceLogContext.Type.XZ)
	@ApiOperation("显示控件-新增")
	@PostMapping()
	public BaseRsp<String> createSituationDecoration(
			@RequestBody @Validated(Create.class) SituationDecorationReq situationDecorationReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationDecorationReq.getName(), null);

		return new BaseRsp<>(
				situationDecorationService.createSituationDecoration(situationDecorationReq));
	}

	/**
	 * 显示控件-修改
	 *
	 * @param situationDecorationReq 显示控件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.XSKJ, type = ServiceLogContext.Type.XG)
	@ApiOperation("显示控件-修改")
	@PatchMapping("")
	public BaseRsp<String> updateSituationDecoration(
			@RequestBody @Validated(Update.class) SituationDecorationReq situationDecorationReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationDecorationReq.getName(), null);

		return new BaseRsp<>(
				situationDecorationService.updateSituationDecoration(situationDecorationReq));
	}

	/**
	 * 显示控件-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationDecoration>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.XSKJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("显示控件-单个查询")
	@GetMapping("")
	public BaseRsp<SituationDecoration>
			getSituationDecorationInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "资源id") String resourceId) {
		return new BaseRsp<>(situationDecorationService.getSituationDecorationInfo(resourceId));
	}

	/**
	 * 显示控件-批量删除
	 *
	 * @param situationIconDelReq 显示控件删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.XSKJ, type = ServiceLogContext.Type.SC)
	@ApiOperation("显示控件-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteSituationDecoration(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(situationDecorationService
				.deleteSituationDecoration(situationIconDelReq.getIdList()));
	}

	/**
	 * 显示控件-列表查询
	 *
	 * @param situationDecorationPageReq 显示控件列表查询请求
	 * @return {@code BaseRsp<ListRsp<SituationDecorationPageRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.XSKJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("显示控件-列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDecorationPageRes>>
			getSituationDecorationPage(SituationDecorationPageReq situationDecorationPageReq) {
		return new BaseRsp<>(
				situationDecorationService.getSituationDecorationPage(situationDecorationPageReq));
	}
}
