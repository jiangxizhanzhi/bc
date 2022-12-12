package com.cyberpeace.assess.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.dao.mapper.AssessDataMapper;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

/**
 *
 */
@Service
public class AssessDataServiceImpl extends ServiceImpl<AssessDataMapper, AssessData> implements AssessDataService {

	@Autowired
	AssessDataMapper assessDataMapper;

	@Override
	public IPage<AssessData> page(AssessListReq pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessData> result = assessDataMapper.listAssessData(page, pageReqBase.getPattern(),
				pageReqBase.getType(), pageReqBase.getTag());

		return result;
	}

	@Override
	public void checkNameRepeat(String name, Integer type, String id) {
		LambdaQueryWrapper<AssessData> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AssessData::getName, name);
		queryWrapper.eq(AssessData::getType, type);

		if (StringUtils.isNotBlank(id)) {
			queryWrapper.ne(AssessData::getResourceId, id);
		}
		AssessData assessData = assessDataMapper.selectOne(queryWrapper);
		if (null != assessData) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}
}
