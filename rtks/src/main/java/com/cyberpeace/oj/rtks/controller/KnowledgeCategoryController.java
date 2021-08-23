package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeCategoryAddReq;
import com.cyberpeace.oj.rtks.dao.entity.KnowledgeCategory;
import com.cyberpeace.oj.rtks.service.KnowledgeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-知识点类别管理")
@RestController
@RequestMapping("/rtks/knowledge-category")
@RequiresAuthentication
public class KnowledgeCategoryController {

	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<KnowledgeCategory> getKnowledge(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		KnowledgeCategory knowledgeCategory = knowledgeCategoryService.getById(resource_id);

		return new BaseRsp<>(knowledgeCategory);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String>
			addKnowledge(@RequestBody @Validated KnowledgeCategoryAddReq knowledgeCategoryAddReq) {
		KnowledgeCategory knowledgeCategory = new KnowledgeCategory();
		BeanUtils.copyProperties(knowledgeCategoryAddReq, knowledgeCategory);
		knowledgeCategoryService.save(knowledgeCategory);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String>
			modifyKnowledge(@RequestBody @Validated AbilityAddReq knowledgeCatagaryOrSystemAddReq) {
		KnowledgeCategory knowledgeCategory = new KnowledgeCategory();
		BeanUtils.copyProperties(knowledgeCatagaryOrSystemAddReq, knowledgeCategory);
		knowledgeCategoryService.updateById(knowledgeCategory);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteKnowledgeCategory(@RequestBody @Validated DeleteReq deleteReq) {
		knowledgeCategoryService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<KnowledgeCategory>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<KnowledgeCategory> data = knowledgeCategoryService.page(pageReqBase);

		return new BaseRsp(data);
	}
}
