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
import com.cyberpeace.situation.controller.req.SituationDrawingPageReq;
import com.cyberpeace.situation.controller.req.SituationDrawingReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.res.SituationDrawingPageRes;
import com.cyberpeace.situation.dao.entity.SituationDrawing;
import com.cyberpeace.situation.dao.entity.SituationDrawingCategory;
import com.cyberpeace.situation.dao.mapper.SituationDrawingCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDrawingMapper;
import com.cyberpeace.situation.service.SituationDrawingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuxz
 * @Date 4/1/2023 上午11:11
 */
@Api(tags = "组件管理-样式组件")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/drawing")
public class ComponentDrawingController {

	@Autowired
	private SituationDrawingService situationDrawingService;

	@Autowired
	private SituationDrawingMapper situationDrawingMapper;

	@Autowired
	private SituationDrawingCategoryMapper situationDrawingCategoryMapper;

	@ApiOperation("样式组件-新增")
	@PostMapping()
	public BaseRsp<String> createSituationDrawing(
			@RequestBody @Validated(Create.class) SituationDrawingReq situationDrawingReq) {
		return new BaseRsp<>(situationDrawingService.createSituationDrawing(situationDrawingReq));
	}

	@ApiOperation("样式组件-修改")
	@PatchMapping("")
	public BaseRsp<String> updateSituationDrawing(
			@RequestBody @Validated(Update.class) SituationDrawingReq situationDrawingReq) {
		return new BaseRsp<>(situationDrawingService.updateSituationDrawing(situationDrawingReq));
	}

	@ApiOperation("样式组件-单个查询")
	@GetMapping("")
	public BaseRsp<SituationDrawing>
			getSituationDrawingInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "资源id") String resourceId) {
		return new BaseRsp<>(situationDrawingService.getSituationDrawingInfo(resourceId));
	}

	@ApiOperation("样式组件-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteSituationDrawing(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(
				situationDrawingService.deleteSituationDrawing(situationIconDelReq.getIdList()));
	}

	@ApiOperation("样式组件-列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDrawingPageRes>>
			getSituationDrawingPage(SituationDrawingPageReq situationDrawingPageReq) {
		return new BaseRsp<>(
				situationDrawingService.getSituationDrawingPage(situationDrawingPageReq));
	}

	@ApiOperation("样式组件-全量查询")
	@GetMapping("/all")
	public BaseRsp<List<SituationDrawingPageRes>>
			getSituationDrawing(SituationDrawingPageReq situationDrawingPageReq) {
		List<SituationDrawing> situationDrawing = situationDrawingMapper.selectList(null);

		List<SituationDrawingCategory> situationDrawingCategories = situationDrawingCategoryMapper
				.selectList(null);

		Map<String, String> categoryNameMap = StreamApiUtils.toMap(situationDrawingCategories,
				SituationDrawingCategory::getResourceId,
				situationDrawingCategory -> situationDrawingCategory.getName());
		situationDrawing.forEach(tmp -> {
			SituationDrawingPageRes situationDrawingPageRes = new SituationDrawingPageRes();
			BeanUtils.copyProperties(tmp, situationDrawingPageRes);
			String category = categoryNameMap.get(tmp.getType());
			if (StringUtils.isNotBlank(category)) {
				situationDrawingPageRes.setType(category);
			}
		});
		return new BaseRsp(situationDrawing);
	}

	@ApiOperation("样式组件类型-列表查询")
	@GetMapping("/category")
	public BaseRsp<List<SituationDrawingCategory>> getSituationDrawingCategory() {
		return new BaseRsp<>(situationDrawingService.getSituationCategory());
	}
}
