package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.practice.dao.entity.PresetMock;
import com.cyberpeace.oj.practice.dao.mapper.PresetMockMapper;
import com.cyberpeace.oj.practice.service.PresetMockService;
import com.cyberpeace.oj.practice.service.bo.mock.CountAndScore;
import com.cyberpeace.oj.practice.service.bo.mock.PresetMockBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PresetMockServiceImpl extends ServiceImpl<PresetMockMapper, PresetMock> implements PresetMockService {

	@Override
	public PresetMockBo getBoById(String presetId) {
		PresetMock mock = this.getBaseMapper().selectById(presetId);
		if (null == mock) {
			throw new BusinessException();
		}
		PresetMockBo bo = BeanUtils.copy(mock, PresetMockBo.class);
		bo.setOperationDist(JsonUtil.toJsonObjectArray(bo.getOperation(), CountAndScore.class));
		bo.setTheoryDist(JsonUtil.toJsonObjectArray(bo.getTheory(), CountAndScore.class));

		return bo;
	}

	@Override
	public List<PresetMock> listAllMock(boolean actual) {
		return this.getBaseMapper().selectAll(actual);
	}
}
