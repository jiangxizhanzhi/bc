package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.controller.req.SituationIconCategoryReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIcon;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.dao.mapper.SituationIconCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationIconMapper;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import com.cyberpeace.situation.service.SituationIconService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午10:34
 */
@Api(tags = "组件管理-图标组件")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/icon")
public class ComponentIconController {

	@Autowired
	private SituationIconService situationIconService;

	@Autowired
	private SituationIconMapper situationIconMapper;

	@Autowired
	private SituationIconCategoryMapper situationIconCategoryMapper;

	@Autowired
	private SituationIconCategoryService situationIconCategoryService;

	@ApiOperation("新增图标")
	@PostMapping()
	public BaseRsp<String> createSituationIcon(
			@RequestBody @Validated(Create.class) SituationIconReq situationIconReq) {
		return new BaseRsp<>(situationIconService.createSituationIcon(situationIconReq));
	}

	@ApiOperation("修改图标")
	@PatchMapping("")
	public BaseRsp<String> updateSituationIcon(
			@RequestBody @Validated(Update.class) SituationIconReq SituationIconReq) {
		return new BaseRsp<>(situationIconService.updateSituationIcon(SituationIconReq));
	}

	@ApiOperation("获取改图标详情")
	@GetMapping("")
	public BaseRsp<SituationIconRes>
			getSituationIconInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationIconService.getSituationIconInfo(resourceId));
	}

	@ApiOperation("批量删除改图标")
	@DeleteMapping("")
	public BaseRsp<Integer>
			deleteSituationIcon(@RequestBody @Validated SituationIconDelReq SituationIconDelReq) {
		return new BaseRsp<>(
				situationIconService.deleteSituationIcon(SituationIconDelReq.getIdList()));
	}

	@ApiOperation("图标分页查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<SituationIconRes>>
			getSituationIconPage(SituationIconPageReq situationIconPageReq) {
		return new BaseRsp<>(situationIconService.getSituationIconPage(situationIconPageReq));
	}

	@ApiOperation("图标全量查询")
	@GetMapping("all")
	public BaseRsp<List<SituationIconRes>>
			getSituationIcon(SituationIconPageReq situationIconPageReq) {

		List<SituationIcon> situationIcons = situationIconMapper.selectList(null);

		List<SituationIconCategory> situationIconCategories = situationIconCategoryMapper
				.selectList(null);
		Map<String, String> toMap = StreamApiUtils.toMap(situationIconCategories,
				SituationIconCategory::getResourceId,
				situationIconCategory -> situationIconCategory.getName());

		situationIcons.forEach(situationIcon -> {
			SituationIconRes situationIconRes = new SituationIconRes();
			BeanUtils.copyProperties(situationIcon, situationIconRes);
			String categoryName = toMap.get(situationIcon.getResourceId());
			if (StringUtils.isNotBlank(categoryName)) {
				situationIconRes.setTypeName(categoryName);
			}
		});
		return new BaseRsp(situationIcons);
	}

	@ApiOperation("获取图标类型")
	@GetMapping("/category")
	public BaseRsp<List<SituationIconCategory>>
			getSituationIconCategoryList(@RequestParam(value = "name",
					required = false) @ApiParam(name = "name", value = "名称") String name) {
		return new BaseRsp<>(situationIconCategoryService.getSituationCategoryList(name));
	}

	@ApiOperation("修改图标类型")
	@PatchMapping("/category")
	public BaseRsp<String> updateituationIconCategory(
			@RequestBody @Validated(Update.class) SituationIconCategoryReq situationIconCategoryReq) {
		return new BaseRsp<>(
				situationIconCategoryService.updateSituationIconCategory(situationIconCategoryReq));
	}

	@ApiOperation("批量删除改图标类型")
	@DeleteMapping("/category")
	public BaseRsp<Integer> deleteituationIconCategory(
			@RequestBody @Validated SituationIconDelReq SituationIconDelReq) {
		return new BaseRsp<>(situationIconCategoryService
				.deleteSituationIconCategory(SituationIconDelReq.getIdList()));
	}

	@ApiOperation("新增图标类型")
	@PostMapping("/category")
	public BaseRsp<String> createituationIconCategory(
			@RequestBody @Validated(Create.class) SituationIconCategoryReq situationIconCategoryReq) {
		return new BaseRsp<>(
				situationIconCategoryService.createSituationIcon(situationIconCategoryReq));
	}

	@ApiOperation("获取图标类型详情")
	@GetMapping("/category/info")
	public BaseRsp<SituationIconCategory>
			getSituationIconCategory(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationIconCategoryService.getSituationIconCategory(resourceId));
	}
}
