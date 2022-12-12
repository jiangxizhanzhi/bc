package com.cyberpeace.assess.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.assess.controller.req.AssessDataAddReq;
import com.cyberpeace.assess.controller.req.AssessDataUpdateReq;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.controller.res.AssessDataRsp;
import com.cyberpeace.assess.controller.res.AssessDataSqlEcharts;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估输出数据管理")
@RestController
@RequestMapping("/assess")
@RequiresAuthentication
@RequiresPermissions(value = { MenuConstant.CMS_ASSESS_RESULT_DATA_MANAGE,
		MenuConstant.CMS_ASSESS_EVALUATE_DATA_MANAGE }, logical = Logical.OR)
public class AssessOutputDataController {

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
	@GetMapping("/assess_output_data")
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

	@ApiOperation("新增单条评估数据")
	@PostMapping("/assess_output_data")
	public BaseRsp<String>
			addAssessData(@RequestBody @Validated AssessDataAddReq assessDataAddReq) {
		AssessData assessData = BeanUtils.copy(assessDataAddReq, AssessData.class);

		// 校验tag是否存在
		List<String> tags = assessDataAddReq.getTag();
		tags = tags.stream().distinct().collect(Collectors.toList());
		assessTagService.checkTag(tags);
		// 校验name是否已存在
		assessDataService.checkNameRepeat(assessDataAddReq.getName(), assessDataAddReq.getType(),
				null);

		assessData.setTag(JsonUtil.toJsonString(assessDataAddReq.getTag()));
		assessData.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				assessDataService.save(assessData);
				assessFileDataService.parseDataFile(assessData.getResourceId(),
						assessData.getInputType(), assessData.getInputData());
			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估数据")
	@PutMapping("/assess_output_data")
	public BaseRsp<String>
			modifyAssessData(@RequestBody @Validated AssessDataUpdateReq assessDataUpdateReq) {
		AssessData assessData = BeanUtils.copy(assessDataUpdateReq, AssessData.class);

		// 校验tag是否存在
		List<String> tags = assessDataUpdateReq.getTag();
		tags = tags.stream().distinct().collect(Collectors.toList());
		assessTagService.checkTag(tags);
		// 校验name是否已存在
		assessDataService.checkNameRepeat(assessDataUpdateReq.getName(),
				assessDataUpdateReq.getType(), assessDataUpdateReq.getResourceId());

		assessData.setTag(JsonUtil.toJsonString(assessDataUpdateReq.getTag()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				assessDataService.updateById(assessData);
				assessFileDataService.parseDataFile(assessData.getResourceId(),
						assessData.getInputType(), assessData.getInputData());
			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("删除评估数据")
	@DeleteMapping("/assess_output_data")
	public BaseRsp<String> deleteAssessData(@RequestBody @Validated DeleteReq deleteReq) {
		assessDataService.removeByIds(deleteReq.getIds());
		// roleService.updateBatchByDeleteRelatedIds(deleteReq, "AssessData");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_output_data/list")
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
