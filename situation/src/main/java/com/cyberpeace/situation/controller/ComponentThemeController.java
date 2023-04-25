package com.cyberpeace.situation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ColorThemeDelReq;
import com.cyberpeace.situation.controller.req.ColorThemeImportReq;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.controller.req.ColorThemeReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;
import com.cyberpeace.situation.service.SituationColorThemeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author xuxz
 * @Date 7/12/2022 下午6:18
 */
@RestController
@Api(tags = "组件管理-图表主题")
@RequiresAuthentication
@RequestMapping("situation/color/theme")
public class ComponentThemeController {

	@Autowired
	private SituationColorThemeService situationColorThemeService;

	@ApiOperation("图表主题-新增")
	@PostMapping()
	public BaseRsp<ColorThemeReq>
			createColorTheme(@RequestBody @Validated(Create.class) ColorThemeReq colorThemeReq) {
		return new BaseRsp<>(situationColorThemeService.createColorTheme(colorThemeReq));
	}

	@ApiOperation("图表主题-修改")
	@PatchMapping("")
	public BaseRsp<String>
			updateColorTheme(@RequestBody @Validated(Update.class) ColorThemeReq ColorThemeReq) {
		return new BaseRsp<>(situationColorThemeService.updateColorTheme(ColorThemeReq));
	}

	@ApiOperation("图表主题-单个查询")
	@GetMapping("")
	public BaseRsp<ColorTheme>
			getColorThemeInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationColorThemeService.getColorThemeInfo(resourceId));
	}

	@ApiOperation("图表主题-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer>
			deleteColorTheme(@RequestBody @Validated ColorThemeDelReq ColorThemeDelReq) {
		return new BaseRsp<>(
				situationColorThemeService.deleteColorTheme(ColorThemeDelReq.getIdList()));
	}

	@ApiOperation("图表主题-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<ColorTheme>> getColorThemePage(ColorThemePageReq colorThemePageReq) {
		return new BaseRsp<>(situationColorThemeService.getColorThemePage(colorThemePageReq));
	}

	@ApiOperation("图表主题-导入")
	@PostMapping("upload")
	public BaseRsp<String> importColorTheme(ColorThemeImportReq colorThemeImportReq) {
		return new BaseRsp<>(situationColorThemeService.importColorTheme(colorThemeImportReq));
	}
}
