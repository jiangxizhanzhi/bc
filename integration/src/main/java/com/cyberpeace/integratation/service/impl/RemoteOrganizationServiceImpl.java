package com.cyberpeace.integratation.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.google.common.collect.Lists;

@Service
public class RemoteOrganizationServiceImpl implements RemoteOrganizationService {

	private String MP_ORG_TREE_URL;

	private String MP_ORG_INFO;

	@PostConstruct
	public void init() {
		String mpHost = mpEnvService.getMpHost();
		MP_ORG_TREE_URL = mpHost + ConfigUtil.getContextProperty("mp.org.tree");
		MP_ORG_INFO = mpHost + ConfigUtil.getContextProperty("mp.org.info");
	}

	@Autowired
	private MpEnvService mpEnvService;

	@Override
	public List<String> childrenList(String orgId, boolean self) {

		JSONArray orgs = HttpClientUtil.rawGet(MP_ORG_TREE_URL + orgId, JSONArray.class);

		// 空数组校验
		if (CollectionUtils.isEmpty(orgs)) {
			return Lists.newArrayList();
		}

		// 空对象校验
		JSONObject realOrgTree = orgs.getJSONObject(CommonConstant.ZERO);
		if (realOrgTree.isEmpty()) {
			return Lists.newArrayList();
		}

		List<String> childrenIds = getChildrenIds(realOrgTree);
		if (self) {
			childrenIds.add(CommonConstant.ZERO, realOrgTree.getString("id"));
		}

		return childrenIds;
	}

	@Override
	public String orgNameById(String orgId) {
		JSONObject orgInfo = HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);
		return null != orgInfo ? orgInfo.getString("name") : "";
	}

	/**
	 * 递归子类组织编号
	 * @param orgTree
	 * @return
	 */
	private List<String> getChildrenIds(JSONObject orgTree) {

		List<String> childIdList = Lists.newArrayList();

		// 获取子类数据
		JSONArray children = orgTree.getJSONArray("children");
		if (CollectionUtils.isEmpty(children)) {
			return childIdList;
		}

		// 递归遍历
		for (Object child : children) {
			JSONObject childObj = (JSONObject) child;
			List<String> childrenIds = getChildrenIds(childObj);

			childIdList.add(childObj.getString("id"));
			childIdList.addAll(childrenIds);
		}

		return childIdList;
	}
}
