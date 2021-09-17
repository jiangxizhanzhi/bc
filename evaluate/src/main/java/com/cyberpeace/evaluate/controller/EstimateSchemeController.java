package com.cyberpeace.evaluate.controller;

import com.cyberpeace.evaluate.controller.req.EstimateSchemeReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;
import com.cyberpeace.evaluate.service.EstimateSchemeService;
import com.cyberpeace.evaluate.service.context.EstimateSchemeContext;
import com.google.common.base.Preconditions;

@Api(tags = "es-装备评估方案相关api")
@RestController
@RequestMapping("/estimate/scheme")
@RequiresAuthentication
public class EstimateSchemeController {

	@Autowired
	private EstimateSchemeService estimateSchemeService;

	/**
	 * 分页查询评估方案列表
	 * @param pageReq
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("分页查询评估方案列表")
	public BaseRsp<ListRsp<EstimateScheme>> list(PageReqBase pageReq) {

		Page pageBase = pageReq.toPageParam();
		ListRsp<EstimateScheme> schemes = estimateSchemeService.selectList(pageBase);
		return new BaseRsp(schemes);
	}

	/**
	 * 新增评估方案
	 * @param req
	 * @return
	 */
	@PostMapping("/create")
	@ApiOperation("新增或修改装备评估方案")
	public BaseRsp<String> addScheme(@RequestBody EstimateSchemeReq req) {
		req.validate();

		String schemeId = estimateSchemeService.add(req);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(schemeId);
		return rsp;

	}

	/**
	 * 新增评估方案
	 * @param req
	 * @return
	 */
	@PatchMapping("/create")
	@ApiOperation("修改评估方案")
	public BaseRsp<String> modifyScheme(@RequestBody EstimateSchemeReq req) {
		req.validate();

		String schemeId = estimateSchemeService.add(req);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(schemeId);
		return rsp;

	}

	@GetMapping("delete")
	@ApiOperation("删除评估方案")
	public BaseRsp deleteScheme(@RequestParam("scheme_id") String schemeId) {
		estimateSchemeService.deleteById(schemeId);

		return new BaseRsp();
	}

	/**
	 * 根据方案编号查询方案详细信息
	 * @param schemeId
	 * @return
	 */
	@GetMapping("/{scheme_id}")
	@ApiOperation("查询方案详细信息")
	public BaseRsp<EstimateSchemeContext>
			getScheme(@ApiParam("方案编号") @PathVariable("scheme_id") String schemeId) {

		Preconditions.checkArgument(StringUtils.isNotBlank(schemeId), "estimate_scheme_id null");

		EstimateSchemeContext estimateSchemeContext = estimateSchemeService.selectById(schemeId);

		BaseRsp<EstimateSchemeContext> rsp = new BaseRsp<>();
		rsp.setData(estimateSchemeContext);
		return rsp;
	}

}
