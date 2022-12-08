package com.cyberpeace.assess.controller.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.controller.res.AssessDataRsp;
import com.cyberpeace.assess.controller.res.AssessDataSqlEcharts;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估输入数据管理")
@RestController
@RequestMapping("/web/assess")
@RequiresAuthentication
@RequiresPermissions(value = {MenuConstant.WEB_ASSESS_INPUT_DATA_MANAGE,MenuConstant.WEB_ASSESS_EVALUATE_DATA_MANAGE}, logical = Logical.OR)
public class AssessDataController {

	@Autowired
	private AssessDataService assessDataService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private AssessTagService assessTagService;

	@Autowired
	private AssessFileDataService assessFileDataService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("查询单条评估数据")
	@GetMapping("/assess_data")
	public BaseRsp<AssessData> getAssessData(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessData assessData = assessDataService.getById(resource_id);
		AssessDataRsp assessDataRsp = BeanUtils.copy(assessData, AssessDataRsp.class);
		assessDataRsp.setTag(JsonUtil.toJsonObjectArray(assessData.getTag(), String.class));

		String tagStr = assessTagService.getTagContent(assessData.getTag());
		assessDataRsp.setTags(tagStr);
		String dataBaseTableByRelatedId = assessFileDataService
				.getDataBaseTableByRelatedId(assessData.getResourceId());
		AssessDataSqlEcharts assessDataSqlEcharts = JsonUtil.toJsonObject(dataBaseTableByRelatedId,
				AssessDataSqlEcharts.class);
		assessDataRsp.setData(assessDataSqlEcharts);
		return new BaseRsp(assessDataRsp);
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_data/list")
	public BaseRsp<List<AssessDataRsp>> list(@Validated AssessListReq pageReqBase) {
		pageReqBase.setSort("-create_time");

		IPage<AssessData> data = assessDataService.page(pageReqBase);

		List<String> userIds = data.getRecords().stream().map(AssessData::getCreateUserId)
				.collect(Collectors.toList());

		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		data.convert(assessData -> {

			AssessDataRsp assessDataRsp = BeanUtils.copy(assessData, AssessDataRsp.class);
			assessDataRsp.setCreateUser(idNameMap.get(assessData.getCreateUserId()));
			assessDataRsp.setTag(JsonUtil.toJsonObjectArray(assessData.getTag(), String.class));

			return assessDataRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

}
