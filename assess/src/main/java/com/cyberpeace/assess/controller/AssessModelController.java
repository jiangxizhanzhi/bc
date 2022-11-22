package com.cyberpeace.assess.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.assess.controller.req.AssessModelAddReq;
import com.cyberpeace.assess.controller.req.AssessModelUpdateReq;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.service.AssessModelService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估模型管理")
@RestController
@RequestMapping("/assess")
@RequiresAuthentication
public class AssessModelController {

	@Autowired
	private AssessModelService assessModelService;

	@ApiOperation("查询单条评估模型")
	@GetMapping("/assess_model")
	public BaseRsp<AssessModel> getAssessModel(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessModel assessModel = assessModelService.getById(resource_id);

		return new BaseRsp(assessModel);
	}

	@ApiOperation("新增单条评估模型")
	@PostMapping("/assess_model")
	public BaseRsp<String>
			addAssessModel(@RequestBody @Validated AssessModelAddReq assessModelAddReq) {
		AssessModel toAddAssessModel = new AssessModel();
		BeanUtils.copyProperties(assessModelAddReq, toAddAssessModel);

		assessModelService.save(toAddAssessModel);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估模型")
	@PutMapping("/assess_model")
	public BaseRsp<String>
			modifyAssessModel(@RequestBody @Validated AssessModelUpdateReq assessModelUpdateReq) {
		AssessModel toUpdateAssessModel = new AssessModel();
		BeanUtils.copyProperties(assessModelUpdateReq, toUpdateAssessModel);

		assessModelService.updateById(toUpdateAssessModel);

		return new BaseRsp<>();
	}

	@ApiOperation("删除评估模型")
	@DeleteMapping("/assess_model")
	public BaseRsp<String> deleteAssessModel(@RequestBody @Validated DeleteReq deleteReq) {
		assessModelService.removeByIds(deleteReq.getIds());
		// roleService.updateBatchByDeleteRelatedIds(deleteReq, "AssessModel");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_model/list")
	public BaseRsp<List<AssessModel>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<AssessModel> data = assessModelService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
