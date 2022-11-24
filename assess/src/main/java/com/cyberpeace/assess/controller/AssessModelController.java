package com.cyberpeace.assess.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.assess.controller.req.AssessModelAddReq;
import com.cyberpeace.assess.controller.req.AssessModelUpdateReq;
import com.cyberpeace.assess.controller.res.AssessModelRsp;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.AssessModelService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;

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

	@Autowired
	private AssessFileDataService assessFileDataService;
	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("查询单条评估模型")
	@GetMapping("/assess_model")
	public BaseRsp<AssessModelRsp> getAssessModel(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessModel assessModel = assessModelService.getById(resource_id);

		AssessModelRsp assessModelRsp = BeanUtils.copy(assessModel, AssessModelRsp.class);
		assessModelRsp.setTag(JsonUtil.toJsonObjectArray(assessModel.getTag(), String.class));
		assessModelRsp
				.setParams(JsonUtil.toJsonObjectArray(assessModel.getParams(), JSONObject.class));
		assessModelRsp.setDataBaseTable(JsonUtil
				.toJsonObject(assessFileDataService.getDataBaseTableByRelatedId(
						assessModel.getResourceId()), JSONObject.class));

		return new BaseRsp(assessModelRsp);
	}

	@ApiOperation("新增单条评估模型")
	@PostMapping("/assess_model")
	public BaseRsp<String>
			addAssessModel(@RequestBody @Validated AssessModelAddReq assessModelAddReq) {
		AssessModel assessModel = BeanUtils.copy(assessModelAddReq, AssessModel.class);
		assessModel.setTag(JsonUtil.toJsonString(assessModelAddReq.getTag()));
		assessModel.setParams(JsonUtil.toJsonString(assessModelAddReq.getParams()));
		assessModel.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		assessModelService.save(assessModel);

		assessFileDataService.parseModelFile(assessModel.getResourceId(),
				assessModel.getInterfaceType(), assessModel.getAttachmentUrl());

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估模型")
	@PutMapping("/assess_model")
	public BaseRsp<String>
			modifyAssessModel(@RequestBody @Validated AssessModelUpdateReq assessModelUpdateReq) {
		AssessModel assessModel = BeanUtils.copy(assessModelUpdateReq, AssessModel.class);
		assessModel.setTag(JsonUtil.toJsonString(assessModelUpdateReq.getTag()));
		assessModel.setParams(JsonUtil.toJsonString(assessModelUpdateReq.getParams()));

		assessModelService.updateById(assessModel);

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
	public BaseRsp<List<AssessModelRsp>> list(@Validated PageReqBase pageReqBase) {
		IPage<AssessModel> data = assessModelService.page(pageReqBase);

		List<String> userIds = data.getRecords().stream().map(AssessModel::getCreateUserId)
				.collect(Collectors.toList());

		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		data.convert(assessModel -> {

			AssessModelRsp assessModelRsp = BeanUtils.copy(assessModel, AssessModelRsp.class);
			assessModelRsp.setTag(JsonUtil.toJsonObjectArray(assessModel.getTag(), String.class));
			assessModelRsp.setCreateUser(idNameMap.get(assessModel.getCreateUserId()));
			assessModelRsp.setParams(
					JsonUtil.toJsonObjectArray(assessModel.getParams(), JSONObject.class));

			return assessModelRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));

	}

}
