package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.ShareReq;
import com.cyberpeace.cr.crbase.model.ShareTeacher;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateContext;
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
	public ListRsp<BusinessTemplateContext> page(TemplateReq listReq) {
		Page page = listReq.toPageParam();

		IPage<BusinessTemplate> result = businessTemplateMapper.listTeamTemplate(page, listReq);

		List<BusinessTemplateContext> data = Lists.newArrayList();

		for (BusinessTemplate businessTemplate : result.getRecords()) {
			BusinessTemplateContext context = BeanUtils.copy(businessTemplate,
					BusinessTemplateContext.class);
			Integer nodeNum = remoteSceneService.getTopoDigest(businessTemplate.getTopoId())
					.getNodeCounts();
			context.setNodesNum(nodeNum);

			data.add(context);
		}

		return PageUtil.getListResult(result);

	}

	@Override
	public Share getShares(String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateMapper.selectById(resourceId);

		Share share = new Share();
		share.setShareMode(businessTemplate.getOperationAuthType());
		List<ShareTeacher> shareTeacherList = Lists.newArrayList();

		if (AuthTable.AuthType.CUSTOM_SHARE_MODE.equals(businessTemplate.getOperationAuthType())) {
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
				shareTeacher.setOrganizationNameAll(userInfo.getOrganizationData());
				shareTeacherList.add(shareTeacher);
			});

		}
		share.setTeachers(shareTeacherList);

		return share;
	}

	@Override
	public void batchSetShares(ShareReq shareReq) {
		String shareTeachers = JsonUtil.toJsonString(shareReq.getShareTeachers());
		List<BusinessTemplate> businessTemplateList = listByIds(shareReq.getIds());

		businessTemplateList.forEach(businessTemplate -> {
			businessTemplate.setOperationAuthType(shareReq.getShareMode());
			businessTemplate.setOperationAuthIds(shareTeachers);
		});

		updateBatchById(businessTemplateList);
	}
}
