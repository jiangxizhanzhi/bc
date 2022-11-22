package com.cyberpeace.assess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.dao.mapper.AssessDataMapper;
import com.cyberpeace.assess.service.AssessDataService;

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
				pageReqBase.getType());

		return result;
	}
}
