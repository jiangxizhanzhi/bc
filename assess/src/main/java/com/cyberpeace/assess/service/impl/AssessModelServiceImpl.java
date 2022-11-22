package com.cyberpeace.assess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.dao.mapper.AssessModelMapper;
import com.cyberpeace.assess.service.AssessModelService;
import com.cyberpeace.common.model.PageReqBase;

/**
 *
 */
@Service
public class AssessModelServiceImpl extends ServiceImpl<AssessModelMapper, AssessModel> implements AssessModelService {

	@Autowired
	AssessModelMapper assessModelMapper;

	@Override
	public IPage<AssessModel> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessModel> result = assessModelMapper.listAssessModel(page,
				pageReqBase.getPattern());

		return result;
	}
}
