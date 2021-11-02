package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.model.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.ShareTeacher;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
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

	@Autowired
	private RemoteUserService remoteUserService;

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
		List<ShareTeacher> shareTeacherList = Lists.newArrayList();

		if (businessTemplate.getOperationAuthType() == 2) {
			String teachers = businessTemplate.getOperationAuthIds();

			List<BasicNameValuePair> nvps = Lists.newArrayList();
			nvps.add(new BasicNameValuePair("ids",
					teachers.substring(1, teachers.length() - 1).replaceAll("\"", "")));
			nvps.add(new BasicNameValuePair("perPage", String.valueOf(1000)));
			nvps.add(new BasicNameValuePair("page", String.valueOf(1)));

			List<UserInfo> userInfoList = remoteUserService.getLazyUserList(nvps);
			userInfoList.forEach(userInfo -> {
				ShareTeacher shareTeacher = new ShareTeacher();
				shareTeacher.setId(userInfo.getUserId());
				shareTeacher.setName(userInfo.getName());
				shareTeacher.setUsername(userInfo.getUserName());
				shareTeacher.setOrganization(userInfo.getOrganizationId());
				shareTeacher.setOrganization_name_all(userInfo.getOrganizationData());
				shareTeacherList.add(shareTeacher);
			});

		}
		share.setTeachers(shareTeacherList);

		return share;
	}
}
