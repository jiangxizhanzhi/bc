package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategary;
import com.cyberpeace.oj.rtks.dao.mapper.SkillCategaryMapper;
import com.cyberpeace.oj.rtks.service.SkillCategaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SkillCategaryServiceImpl extends ServiceImpl<SkillCategaryMapper, SkillCategary> implements SkillCategaryService {

	@Autowired
	private SkillCategaryMapper skillCategaryMapper;

	@Override
	public ListRsp<SkillCategary> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<SkillCategary> result = skillCategaryMapper.listSkillCategary(page,
				pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}
}
