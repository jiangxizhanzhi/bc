package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.SituationConfigAddReq;
import com.cyberpeace.situation.req.SituationConfigFilterReq;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import com.cyberpeace.situation.dao.mapper.SituationConfigMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "态势自定义配置")
@RestController
@RequestMapping("/situation/config")
public class SituationConfigController {

	@Autowired
	private SituationConfigMapper situationConfigMapper;

	@ApiOperation("新增配置")
	@PostMapping("")
	public BaseRsp<SituationConfig>
			setConfig(@RequestBody SituationConfigAddReq situationConfigAddReq) {

		SituationConfig situationConfig = BeanUtils.copy(situationConfigAddReq,
				SituationConfig.class);
		situationConfig.setScope(JsonUtil.toJsonString(situationConfigAddReq.getScope()));
		situationConfigMapper.insert(situationConfig);
		return new BaseRsp<>(situationConfig);
	}

	@ApiOperation("查询配置")
	@GetMapping("")
	public BaseRsp<List<SituationConfig>>
			getConfig(SituationConfigFilterReq situationConfigFilterReq) {

		List<String> situationConfigIds = situationConfigMapper.filterIds(situationConfigFilterReq);
		List<SituationConfig> situationConfigList = Lists.newArrayList();
		if (!situationConfigIds.isEmpty()) {
			situationConfigList = situationConfigMapper.selectBatchIds(situationConfigIds);
		}
		return new BaseRsp<>(situationConfigList);
	}

	@ApiOperation("删除配置")
	@DeleteMapping("")
	public BaseRsp deleteConfig(SituationConfigFilterReq situationConfigFilterReq) {
		List<String> situationConfigIds = situationConfigMapper.filterIds(situationConfigFilterReq);
		if (!situationConfigIds.isEmpty()) {
			situationConfigMapper.deleteBatchIds(situationConfigIds);
		}
		return new BaseRsp<>();
	}
}