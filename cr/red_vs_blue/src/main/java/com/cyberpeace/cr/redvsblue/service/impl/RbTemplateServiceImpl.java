package com.cyberpeace.cr.redvsblue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbTemplateServiceImpl extends ServiceImpl<RbTemplateMapper, RbTemplate> implements RbTemplateService {

	@Autowired
	RbTemplateMapper rbTemplateMapper;

	@Override
	public RbTemplate getByBusinessTemplateId(String businessTemplateId) {
		return rbTemplateMapper.selectByBusinessTemplateId(businessTemplateId);
	}
}
