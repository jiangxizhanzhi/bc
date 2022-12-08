package com.cyberpeace.assess.controller.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.controller.res.AssessModelRsp;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.AssessModelService;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估模型管理")
@RestController
@RequestMapping("/web/assess")
@RequiresAuthentication
@RequiresPermissions(MenuConstant.WEB_ASSESS_EVALUATE_MODEL_DATA_MANAGE)
public class AssessModelController {

	@Autowired
	private AssessModelService assessModelService;

	@Autowired
	private AssessFileDataService assessFileDataService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private AssessTagService assessTagService;

	@Autowired
	private UtilService utilService;

	@ApiOperation("查询单条评估模型")
	@GetMapping("/assess_model")
	public BaseRsp<AssessModelRsp> getAssessModel(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessModel assessModel = assessModelService.getById(resource_id);

		AssessModelRsp assessModelRsp = BeanUtils.copy(assessModel, AssessModelRsp.class);
		assessModelRsp.setTag(JsonUtil.toJsonObjectArray(assessModel.getTag(), String.class));
		assessModelRsp
				.setParams(JsonUtil.toJsonObjectArray(assessModel.getParams(), JSONObject.class));
		assessModelRsp.setDataBaseTable(JsonUtil.toJsonObject(
				assessFileDataService.getDataBaseTableByRelatedId(assessModel.getResourceId()),
				JSONObject.class));

		String tagStr = assessTagService.getTagContent(assessModel.getTag());
		assessModelRsp.setTags(tagStr);
		if (assessModel.getInterfaceType().equals(AssessModel.interfaceType.OTHER.getCode())) {
			assessModelRsp.setShowAttachmentUrl(utilService.officeModelUrl(
					assessModelRsp.getAttachmentUrl(), assessModel.getResourceId()));
		}

		return new BaseRsp(assessModelRsp);
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_model/list")
	public BaseRsp<List<AssessModelRsp>> list(@Validated AssessListReq pageReqBase) {
		pageReqBase.setSort("-create_time");
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
