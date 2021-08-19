package com.cyberpeace.oj.rtks.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategary;
import com.cyberpeace.oj.rtks.dao.mapper.SkillCategaryMapper;
import com.cyberpeace.oj.rtks.service.SkillCategaryService;

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
		IPage<SkillCategary> result = skillCategaryMapper.selectPage(page,
				new QueryWrapper<SkillCategary>().like(
						StringUtils.isNotEmpty(pageReqBase.getPattern()), "content",
						pageReqBase.getPattern()));
		return PageUtil.getListResult(result);
	}
}
