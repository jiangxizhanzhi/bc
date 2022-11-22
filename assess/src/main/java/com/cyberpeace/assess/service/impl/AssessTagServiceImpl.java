package com.cyberpeace.assess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;

/**
 *
 */
@Service
public class AssessTagServiceImpl extends ServiceImpl<AssessTagMapper, AssessTag> implements AssessTagService {

	@Autowired
	AssessTagMapper assessTagMapper;

	@Override
	public ListRsp<AssessTag> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessTag> result = assessTagMapper.listAssessTag(page, pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}
}
