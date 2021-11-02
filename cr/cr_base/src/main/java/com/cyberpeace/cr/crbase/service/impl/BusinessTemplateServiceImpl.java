package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.model.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class BusinessTemplateServiceImpl extends ServiceImpl<BusinessTemplateMapper, BusinessTemplate> implements BusinessTemplateService {

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Override
	public ListRsp<BusinessTemplateRsp> page(TemplateReq listReq) {
		Page page = listReq.toPageParam();

		IPage<BusinessTemplateRsp> result = businessTemplateMapper.listTeamTemplate(page, listReq);

		for (BusinessTemplateRsp businessTemplate : result.getRecords()) {
			businessTemplate.setNodesNum(
					remoteSceneService.getTopoDigest(businessTemplate.getTopoId()).getNodeCounts());
		}

		return PageUtil.getListResult(result);

	}

	@Override
	public Share getShares(String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateMapper.selectById(resourceId);
		Share share = new Share();
		share.setShareMode(businessTemplate.getOperationAuthType());
		List<String> teachers = Lists.newArrayList();
		if (businessTemplate.getOperationAuthType() == 2) {
			teachers = JsonUtil.toJsonObjectArray(businessTemplate.getOperationAuthIds(),
					String.class);

		}
		share.setTeachers(teachers);
		return share;
	}
}
