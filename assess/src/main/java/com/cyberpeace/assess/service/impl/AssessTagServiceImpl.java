package com.cyberpeace.assess.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.common.exception.BusinessException;
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

import java.util.List;

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

    @Override
    public void checkTagIds(List<String> tags) {
		LambdaQueryWrapper<AssessTag> queryWrapper = new LambdaQueryWrapper<>();
		if(!tags.isEmpty()){
			queryWrapper.in(AssessTag::getResourceId, tags);
		}
		List<AssessTag> assessTags = assessTagMapper.selectList(queryWrapper);
		if(assessTags.isEmpty()){
			throw new BusinessException(AssessErrorCode.TAG_ID_ERROR);
		}
    }
}
