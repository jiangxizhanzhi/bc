package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateListReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "红蓝对抗模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<RbTemplate> teamTemplates = rbTemplateService
				.getRbTemplateByBusinessIds(deleteReq.getIds());
		List<String> roomIds = teamTemplates.stream().map(RbTemplate::getResourceId).distinct()
				.collect(Collectors.toList());

		rbTemplateService.batchDelete(deleteReq.getIds(), roomIds);

		return new BaseRsp<>();
	}

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<RbTemplateRsp>> list(RbTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());

		IPage<RbTemplateData> data = rbTemplateService.getList(listQuery);

		data.convert(teamTemplateData -> getRbTemplateRspByBaseInfoData(teamTemplateData));

		return new BaseRsp(data);
	}

	private Object getRbTemplateRspByBaseInfoData(RbTemplateData teamTemplateData) {
		return null;
	}

}
