package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationDecorationPageReq;
import com.cyberpeace.situation.controller.req.SituationDecorationReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.res.SituationDecorationPageRes;
import com.cyberpeace.situation.dao.entity.SituationDecoration;
import com.cyberpeace.situation.service.SituationDecorationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
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

	@ApiOperation("新增图形资源")
	@PostMapping()
	public BaseRsp<String> createSituationDecoration(
			@RequestBody @Validated(Create.class) SituationDecorationReq situationDecorationReq) {
		return new BaseRsp<>(
				situationDecorationService.createSituationDecoration(situationDecorationReq));
	}

	@ApiOperation("修改图形资源")
	@PatchMapping("")
	public BaseRsp<String> updateSituationDecoration(
			@RequestBody @Validated(Update.class) SituationDecorationReq situationDecorationReq) {
		return new BaseRsp<>(
				situationDecorationService.updateSituationDecoration(situationDecorationReq));
	}

	@ApiOperation("获取改图形资源详情")
	@GetMapping("")
	public BaseRsp<SituationDecoration>
			getSituationDecorationInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "资源id") String resourceId) {
		return new BaseRsp<>(situationDecorationService.getSituationDecorationInfo(resourceId));
	}

	@ApiOperation("批量删除改图形资源")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteSituationDecoration(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(situationDecorationService
				.deleteSituationDecoration(situationIconDelReq.getIdList()));
	}

	@ApiOperation("图形资源分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDecorationPageRes>>
			getSituationDecorationPage(SituationDecorationPageReq situationDecorationPageReq) {
		return new BaseRsp<>(
				situationDecorationService.getSituationDecorationPage(situationDecorationPageReq));
	}
}
