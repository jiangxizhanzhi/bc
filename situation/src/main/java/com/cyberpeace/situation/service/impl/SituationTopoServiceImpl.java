package com.cyberpeace.situation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.dao.entity.SituationTopo;
import com.cyberpeace.situation.dao.mapper.SituationTopoMapper;
import com.cyberpeace.situation.service.SituationTopoService;
import com.cyberpeace.situation.service.context.ConfigContext;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
@Service
public class SituationTopoServiceImpl extends ServiceImpl<SituationTopoMapper, SituationTopo> implements SituationTopoService {

	@Autowired
	private SituationTopoMapper situationTopoMapper;

	@Override
	public ConfigContext getSituationTopoConfig(String resourceId) {
		SituationTopo situationTopo = situationTopoMapper.selectById(resourceId);
		if (situationTopo == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return BeanUtils.copy(situationTopo, ConfigContext.class);
	}

	@Override
	public ConfigContext createSituationTopoConfig(SituationTopoReq situationTopoReq) {
		SituationTopo situationTopo = BeanUtils.copy(situationTopoReq, SituationTopo.class);
		situationTopo.setResourceId(IDGeneratorUtil.getID());
		situationTopoMapper.insert(situationTopo);
		return BeanUtils.copy(situationTopo, ConfigContext.class);
	}

	@Override
	public ConfigContext updateSituationTopoConfig(SituationTopoReq situationTopoReq) {
		String resourceId = situationTopoReq.getResourceId();
		SituationTopo situationTopo = situationTopoMapper.selectById(resourceId);
		if (situationTopo == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SituationTopo copy = BeanUtils.copy(situationTopoReq, SituationTopo.class);
		situationTopoMapper.updateById(copy);
		return BeanUtils.copy(copy, ConfigContext.class);
	}

	@Override
	public Integer deleteSituationTopoConfig(List<String> idList) {
		return situationTopoMapper.deleteBatchIds(idList);
	}

	@Override
	public List<ConfigContext> getAllConfig() {
		List<SituationTopo> topoConfigs = situationTopoMapper
				.selectList(new LambdaQueryWrapper<>());
		return JsonUtil.toJsonObjectArray(JsonUtil.toJsonString(topoConfigs), ConfigContext.class);
	}
}
