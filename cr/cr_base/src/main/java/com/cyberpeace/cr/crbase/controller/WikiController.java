package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.service.WikiService;
import com.cyberpeace.cr.crbase.service.context.wiki.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 百科相关接口
 *
 * @author SN
 * @date 2023/05/15
 */
@Api(tags = "豪密-密码百科")
@RestController
@RequiresAuthentication
@RequestMapping("/wiki")
public class WikiController {

	@Autowired
	private WikiService wikiService;

	@ApiOperation("新增百科条目")
	@PostMapping("/create")
	public BaseRsp create(@RequestBody @Validated WikiCreateReq req) {
		req.validate();
		wikiService.create(req);
		return new BaseRsp<>();
	}

	@ApiOperation("删除百科条目")
	@DeleteMapping("/delete")
	public BaseRsp delete(@RequestBody @Validated IdListReq req) {
		List<String> resourceIds = req.getIds();
		wikiService.deleteByIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改百科条目")
	@PutMapping("/update")
	public BaseRsp modify(@RequestBody @Validated WikiModifyReq req) {
		req.validate();
		wikiService.update(req);
		return new BaseRsp<>();
	}

	@ApiOperation("查看百科条目")
	@GetMapping("/")
	public BaseRsp<WikiRsp> get(@ApiParam(value = "id", name = "resource_id") String resource_id) {
		WikiRsp rsp = wikiService.getById(resource_id);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("百科条目列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<WikiListRsp>> list(@Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String search = pageReq.getPattern();
		ListRsp<WikiListRsp> res = wikiService.page(page, search);
		return new BaseRsp<>(res);
	}
}
