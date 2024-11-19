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

/**
 * 态势配置Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "态势自定义配置")
@RestController
@RequestMapping("/situation/config")
public class SituationConfigController {

	/**
	 * 态势配置Mapper
	 *
	 * @see SituationConfigMapper
	 */
	@Autowired
	private SituationConfigMapper situationConfigMapper;

	/**
	 * 设置配置
	 *
	 * @param situationConfigAddReq 态势配置新增请求体
	 * @return {@code BaseRsp<SituationConfig> }
	 * @throws
	 * @see SituationConfigAddReq
	 * @see BaseRsp<SituationConfig>
	 */
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

	/**
	 * 获取配置
	 *
	 * @param situationConfigFilterReq 态势配置过滤器请求体
	 * @return {@code BaseRsp<List<SituationConfig>> }
	 * @throws
	 * @see SituationConfigFilterReq
	 * @see BaseRsp<List<SituationConfig>>
	 */
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

	/**
	 * 删除配置
	 *
	 * @param situationConfigFilterReq 态势配置过滤器请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SituationConfigFilterReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("删除配置")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteConfig(SituationConfigFilterReq situationConfigFilterReq) {
		List<String> situationConfigIds = situationConfigMapper.filterIds(situationConfigFilterReq);
		if (!situationConfigIds.isEmpty()) {
			situationConfigMapper.deleteBatchIds(situationConfigIds);
		}
		return new BaseRsp<>(true);
	}
}