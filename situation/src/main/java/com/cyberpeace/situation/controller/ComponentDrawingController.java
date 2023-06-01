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
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.controller.req.SituationDrawingPageReq;
import com.cyberpeace.situation.controller.req.SituationDrawingReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.res.SituationDrawingPageRes;
import com.cyberpeace.situation.dao.entity.SituationDrawing;
import com.cyberpeace.situation.dao.entity.SituationDrawingCategory;
import com.cyberpeace.situation.dao.mapper.SituationDrawingCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDrawingMapper;
import com.cyberpeace.situation.service.SituationDrawingService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 组件管理-样式组件
 *
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

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 样式组件-新增
	 *
	 * @param situationDrawingReq 样式组件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.XZ)
	@ApiOperation("样式组件-新增")
	@PostMapping()
	public BaseRsp<String> createSituationDrawing(
			@RequestBody @Validated(Create.class) SituationDrawingReq situationDrawingReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationDrawingReq.getName(), null);

		return new BaseRsp<>(situationDrawingService.createSituationDrawing(situationDrawingReq));
	}

	/**
	 * 样式组件-修改
	 *
	 * @param situationDrawingReq 样式组件请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.XG)
	@ApiOperation("样式组件-修改")
	@PatchMapping("")
	public BaseRsp<String> updateSituationDrawing(
			@RequestBody @Validated(Update.class) SituationDrawingReq situationDrawingReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationDrawingReq.getName(), null);

		return new BaseRsp<>(situationDrawingService.updateSituationDrawing(situationDrawingReq));
	}

	/**
	 * 样式组件-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationDrawing>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("样式组件-单个查询")
	@GetMapping("")
	public BaseRsp<SituationDrawing>
			getSituationDrawingInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "资源id") String resourceId) {
		return new BaseRsp<>(situationDrawingService.getSituationDrawingInfo(resourceId));
	}

	/**
	 * 样式组件-批量删除
	 *
	 * @param situationIconDelReq 样式组件删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.SC)
	@ApiOperation("样式组件-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteSituationDrawing(
			@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
		return new BaseRsp<>(
				situationDrawingService.deleteSituationDrawing(situationIconDelReq.getIdList()));
	}

	/**
	 * 样式组件-列表查询
	 *
	 * @param situationDrawingPageReq 样式组件分页请求
	 * @return {@code BaseRsp<ListRsp<SituationDrawingPageRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("样式组件-列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDrawingPageRes>>
			getSituationDrawingPage(SituationDrawingPageReq situationDrawingPageReq) {
		return new BaseRsp<>(
				situationDrawingService.getSituationDrawingPage(situationDrawingPageReq));
	}

	/**
	 * 样式组件-全量查询
	 *
	 * @return {@code BaseRsp<List<SituationDrawingPageRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.YSZJ, type = ServiceLogContext.Type.CX)
	@ApiOperation("样式组件-全量查询")
	@GetMapping("/all")
	public BaseRsp<List<SituationDrawingPageRes>> getSituationDrawing() {

		// 查询所有样式组件
		List<SituationDrawing> situationDrawing = situationDrawingMapper.selectList(null);
		List<SituationDrawingCategory> situationDrawingCategories = situationDrawingCategoryMapper
				.selectList(null);

		// 组装样式组件类型
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

	/**
	 * 样式组件类型-列表查询
	 *
	 * @return {@code BaseRsp<List<SituationDrawingCategory>>}
	 */
	@ApiOperation("样式组件类型-列表查询")
	@GetMapping("/category")
	public BaseRsp<List<SituationDrawingCategory>> getSituationDrawingCategory() {
		return new BaseRsp<>(situationDrawingService.getSituationCategory());
	}
}
