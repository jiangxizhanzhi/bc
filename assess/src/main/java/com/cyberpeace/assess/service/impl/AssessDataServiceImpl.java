package com.cyberpeace.assess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.dao.mapper.AssessDataMapper;
import com.cyberpeace.assess.service.AssessDataService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;

/**
 *
 */
@Service
public class AssessDataServiceImpl extends ServiceImpl<AssessDataMapper, AssessData> implements AssessDataService {

	@Autowired
	AssessDataMapper assessDataMapper;

	@Override
	public ListRsp<AssessData> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessModel> result = assessDataMapper.listAssessData(page, pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}
}
