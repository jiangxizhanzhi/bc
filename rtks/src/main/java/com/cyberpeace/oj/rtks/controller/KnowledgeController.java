package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeAddReq;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.service.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-知识点管理")
@RestController
@RequestMapping("/rtks/knowledge")
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<Knowledge> getKnowledge(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		Knowledge knowledge = knowledgeService.getKnowledgeByResourceId(resource_id);

		return new BaseRsp<>(knowledge);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {
		Knowledge knowledge = new Knowledge();
		BeanUtils.copyProperties(knowledgeAddReq, knowledge);
		knowledge.setCourses(JsonUtil.toJsonString(knowledgeAddReq.getCourseList()));
		knowledgeService.save(knowledge);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	public BaseRsp<String>
			modifyKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {
		Knowledge knowledge = new Knowledge();
		BeanUtils.copyProperties(knowledgeAddReq, knowledge);
		knowledge.setCourses(JsonUtil.toJsonString(knowledgeAddReq.getCourseList()));
		knowledgeService.updateById(knowledge);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteKnowledge(@RequestBody @Validated DeleteReq deleteReq) {
		knowledgeService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<KnowledgeRes>> list(@Validated KnowledgeListReq knowledgeListReq) {
		ListRsp<KnowledgeRes> data = knowledgeService.page(knowledgeListReq);

		return new BaseRsp(data);
	}
}
