package com.cyberpeace.assess.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.assess.controller.req.AssessDataAddReq;
import com.cyberpeace.assess.controller.req.AssessDataUpdateReq;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.controller.res.AssessDataRsp;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.assess.service.AssessTagService;
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

@Api(tags = "评估数据管理")
@RestController
@RequestMapping("/assess")
@RequiresAuthentication
public class AssessDataController {

	@Autowired
	private AssessDataService assessDataService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private AssessTagService assessTagService;

	@ApiOperation("查询单条评估数据")
	@GetMapping("/assess_data")
	public BaseRsp<AssessData> getAssessData(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessData assessData = assessDataService.getById(resource_id);
		AssessDataRsp assessDataRsp = BeanUtils.copy(assessData, AssessDataRsp.class);
		assessDataRsp.setTag(JsonUtil.toJsonObjectArray(assessData.getTag(), String.class));

		String tagStr = assessTagService.getTagContent(assessData.getTag());
		assessDataRsp.setTags(tagStr);

		return new BaseRsp(assessDataRsp);
	}

	@ApiOperation("新增单条评估数据")
	@PostMapping("/assess_data")
	public BaseRsp<String>
			addAssessData(@RequestBody @Validated AssessDataAddReq assessDataAddReq) {
		AssessData assessData = BeanUtils.copy(assessDataAddReq, AssessData.class);

		assessData.setTag(JsonUtil.toJsonString(assessDataAddReq.getTag()));
		assessData.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		assessDataService.save(assessData);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条评估数据")
	@PutMapping("/assess_data")
	public BaseRsp<String>
			modifyAssessData(@RequestBody @Validated AssessDataUpdateReq assessDataUpdateReq) {
		AssessData assessData = BeanUtils.copy(assessDataUpdateReq, AssessData.class);
		assessData.setTag(JsonUtil.toJsonString(assessDataUpdateReq.getTag()));

		assessDataService.updateById(assessData);

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
	public BaseRsp<List<AssessDataRsp>> list(@Validated AssessListReq pageReqBase) {
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
