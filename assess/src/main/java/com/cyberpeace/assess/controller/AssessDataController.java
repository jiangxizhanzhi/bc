package com.cyberpeace.assess.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.assess.controller.req.AssessDataAddReq;
import com.cyberpeace.assess.controller.req.AssessDataUpdateReq;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估数据管理")
@RestController
@RequestMapping("/assess")
@RequiresAuthentication
public class AssessDataController {

	@Autowired
	private AssessDataService assessDataService;

	@ApiOperation("查询单条评估数据")
	@GetMapping("/assess_data")
	public BaseRsp<AssessData> getAssessData(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessData assessData = assessDataService.getById(resource_id);

		return new BaseRsp(assessData);
	}

	@ApiOperation("新增单条评估数据")
	@PostMapping("/assess_data")
	public BaseRsp<String>
			addAssessData(@RequestBody @Validated AssessDataAddReq assessDataAddReq) {
		AssessData toAddAssessData = new AssessData();
		BeanUtils.copyProperties(assessDataAddReq, toAddAssessData);

		assessDataService.save(toAddAssessData);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估数据")
	@PutMapping("/assess_data")
	public BaseRsp<String>
			modifyAssessData(@RequestBody @Validated AssessDataUpdateReq assessDataUpdateReq) {
		AssessData toUpdateAssessData = new AssessData();
		BeanUtils.copyProperties(assessDataUpdateReq, toUpdateAssessData);

		assessDataService.updateById(toUpdateAssessData);

		return new BaseRsp<>();
	}

	@ApiOperation("删除评估数据")
	@DeleteMapping("/assess_data")
	public BaseRsp<String> deleteAssessData(@RequestBody @Validated DeleteReq deleteReq) {
		assessDataService.removeByIds(deleteReq.getIds());
		// roleService.updateBatchByDeleteRelatedIds(deleteReq, "AssessData");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_data/list")
	public BaseRsp<List<AssessData>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<AssessData> data = assessDataService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
