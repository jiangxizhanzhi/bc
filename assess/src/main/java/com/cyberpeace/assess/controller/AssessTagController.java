package com.cyberpeace.assess.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.assess.controller.req.AssessTagAddReq;
import com.cyberpeace.assess.controller.req.AssessTagUpdateReq;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估标签管理")
@RestController
@RequestMapping("/assess")
@RequiresAuthentication
public class AssessTagController {

	@Autowired
	private AssessTagService assessTagService;

	@ApiOperation("查询单条评估模型")
	@GetMapping("/assess_tag")
	public BaseRsp<AssessTag> getAssessTag(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessTag assessTag = assessTagService.getById(resource_id);

		return new BaseRsp(assessTag);
	}

	@ApiOperation("新增单条评估模型")
	@PostMapping("/assess_tag")
	public BaseRsp<String> addAssessTag(@RequestBody @Validated AssessTagAddReq assessTagAddReq) {
		AssessTag toAddAssessTag = new AssessTag();
		BeanUtils.copyProperties(assessTagAddReq, toAddAssessTag);
		// 校验name是否已存在
		assessTagService.checkNameRepeat(assessTagAddReq.getContent(), null);
		assessTagService.save(toAddAssessTag);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估模型")
	@PutMapping("/assess_tag")
	public BaseRsp<String>
			modifyAssessTag(@RequestBody @Validated AssessTagUpdateReq assessTagUpdateReq) {
		AssessTag toUpdateAssessTag = new AssessTag();
		BeanUtils.copyProperties(assessTagUpdateReq, toUpdateAssessTag);
		// 校验name是否已存在
		assessTagService.checkNameRepeat(assessTagUpdateReq.getContent(), assessTagUpdateReq.getResourceId());
		assessTagService.updateById(toUpdateAssessTag);

		return new BaseRsp<>();
	}

	@ApiOperation("删除评估模型")
	@DeleteMapping("/assess_tag")
	public BaseRsp<String> deleteAssessTag(@RequestBody @Validated DeleteReq deleteReq) {
		assessTagService.removeByIds(deleteReq.getIds());
		// roleService.updateBatchByDeleteRelatedIds(deleteReq, "AssessTag");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_tag/list")
	public BaseRsp<List<AssessTag>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<AssessTag> data = assessTagService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
