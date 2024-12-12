package com.cyberpeace.integratation.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 远程组织Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see RemoteOrganizationService
 * @since 1.0.0
 */
@Service
public class RemoteOrganizationServiceImpl implements RemoteOrganizationService {

	/**
	 * 中台组织树网址
	 *
	 * @see String
	 */
	private String MP_ORG_TREE_URL;

	/**
	 * 中台组织信息
	 *
	 * @see String
	 */
	private String MP_ORG_INFO;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		String mpHost = mpEnvService.getMpHost();
		MP_ORG_TREE_URL = mpHost + ConfigUtil.getContextProperty("mp.org.tree");
		MP_ORG_INFO = mpHost + ConfigUtil.getContextProperty("mp.org.info");
	}

	/**
	 * 中台环境Service
	 *
	 * @see MpEnvService
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 子级列表
	 *
	 * @param orgId 组织id
	 * @param self  自我
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see boolean
	 * @see List<String>
	 */
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

		List<JSONObject> children = getChildrenIds(realOrgTree);
		List<String> childrenIds = StreamApiUtils.map(children, org -> org.getString("id"));
		if (self) {
			childrenIds.add(CommonConstant.ZERO, realOrgTree.getString("id"));
		}

		return childrenIds;
	}

	/**
	 * 获取组织路径
	 *
	 * @param orgId 组织id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getOrgPath(String orgId) {

		JSONObject orgInfo = HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);

		if (null == orgInfo) {
			return CommonConstant.EMPTY_STRING;
		}
		return orgInfo.getString(JsonKeyConstant.PATH);
	}

	/**
	 * 组织名称由id
	 *
	 * @param orgId 组织id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String orgNameById(String orgId) {
		JSONObject orgInfo = HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);
		return null != orgInfo ? orgInfo.getString("name") : "";
	}

	/**
	 * 子级映射
	 *
	 * @param orgId 组织id
	 * @param self  自我
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see String
	 * @see boolean
	 * @see Map<String, String>
	 */
	@Override
	public Map<String, String> childrenMap(String orgId, boolean self) {

		JSONArray orgs = HttpClientUtil.rawGet(MP_ORG_TREE_URL + orgId, JSONArray.class);

		// 空数组校验
		if (CollectionUtils.isEmpty(orgs)) {
			return Maps.newHashMap();
		}

		// 空对象校验
		JSONObject realOrgTree = orgs.getJSONObject(CommonConstant.ZERO);
		if (realOrgTree.isEmpty()) {
			return Maps.newHashMap();
		}

		List<JSONObject> children = getChildrenIds(realOrgTree);

		Map<String, String> orgMap = StreamApiUtils.toMap(children,
				org -> org.getString(JsonKeyConstant.ID),
				org -> org.getString(JsonKeyConstant.NAME));
		if (self) {
			orgMap.put(realOrgTree.getString(JsonKeyConstant.ID),
					realOrgTree.getString(JsonKeyConstant.NAME));
		}

		return orgMap;
	}

	/**
	 * 获取组织id由树
	 *
	 * @param orgTree 组织树
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getOrgIdByTree(String orgTree) {
		if (StringUtils.isBlank(orgTree)) {
			return CommonConstant.EMPTY_STRING;
		}

		List<String> orgNames = Arrays.asList(orgTree.split(CommonConstant.LEFT_OBLIQUE_LINE));
		JSONArray orgTreeArray = HttpClientUtil.rawGet(MP_ORG_TREE_URL, JSONArray.class);

		JSONObject org = getOrgIdByTree(orgNames, orgTreeArray);
		return null != org ? org.getString(JsonKeyConstant.ID) : CommonConstant.EMPTY_STRING;
	}

	/**
	 * 会返回空值 请自己判断
	 *
	 * @param orgStr
	 * @param orgTree
	 * @return
	 * @throws
	 * @see List<String>
	 * @see JSONArray
	 * @see JSONObject
	 */
	private JSONObject getOrgIdByTree(List<String> orgStr, JSONArray orgTree) {

		List<String> currentOrgList = Lists.newArrayList(orgStr);
		String matchOrg = currentOrgList.remove(CommonConstant.ZERO.intValue());

		// 数据判断
		if (CollectionUtils.isEmpty(orgTree)) {
			return null;
		}

		// 遍历进行匹配
		for (Object child : orgTree) {
			JSONObject childObj = (JSONObject) child;
			String orgName = childObj.getString(JsonKeyConstant.NAME);

			// 组织不为空 且是最后一级 且等于要匹配的数据
			if (StringUtils.isNotBlank(orgName) && orgName.equals(matchOrg)
					&& currentOrgList.size() == CommonConstant.ZERO.intValue()) {
				return childObj;
			}

			// 非最后一级 递归匹配
			if (StringUtils.isNotBlank(orgName) && orgName.equals(matchOrg)) {
				JSONArray children = childObj.getJSONArray(JsonKeyConstant.CHILDREN);
				if (CollectionUtils.isEmpty(children)) {
					return null;
				} else {
					return getOrgIdByTree(currentOrgList, children);
				}
			}

		}

		return null;
	}

	/**
	 * 递归子类组织编号
	 *
	 * @param orgTree
	 * @return
	 * @throws
	 * @see JSONObject
	 * @see List<JSONObject>
	 */
	private List<JSONObject> getChildrenIds(JSONObject orgTree) {

		List<JSONObject> childIdList = Lists.newArrayList();

		// 获取子类数据
		JSONArray children = orgTree.getJSONArray(JsonKeyConstant.CHILDREN);
		if (CollectionUtils.isEmpty(children)) {
			return childIdList;
		}

		// 递归遍历
		for (Object child : children) {
			JSONObject childObj = (JSONObject) child;
			List<JSONObject> childrenIds = getChildrenIds(childObj);

			childIdList.add(childObj);
			childIdList.addAll(childrenIds);
		}

		return childIdList;
	}

	/**
	 * 获取当前组织下的所有子级组织对象集合
	 *
	 * @param orgId 指定组织id
	 * @return 子级对象的集合JSONArray
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	@Override
	public JSONArray classList(String orgId) {
		String url = MP_ORG_TREE_URL;
		if (null != orgId && !orgId.isEmpty()) {
			url += orgId;
		}

		JSONArray orgs = HttpClientUtil.rawGet(url, JSONArray.class);

		// 空数组校验
		if (CollectionUtils.isEmpty(orgs)) {
			return new JSONArray();
		}

		// 空对象校验
		JSONObject realOrgTree = orgs.getJSONObject(CommonConstant.ZERO);
		if (realOrgTree.isEmpty()) {
			return new JSONArray();
		}

		return orgs;
	}

	/**
	 * 获取父母id列表
	 *
	 * @param orgId 组织id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	@Override
	public List<String> getParentIds(String orgId) {

		if (StringUtils.isBlank(orgId)) {
			return Lists.newArrayList();
		}

		List<String> orgIds = Lists.newArrayList(orgId);
		JSONObject orgInfo = getById(orgId);

		if (StringUtils.isBlank(orgInfo.getString(JsonKeyConstant.PARENT))) {
			return orgIds;
		}

		List<String> parentIds = getParentIds(orgInfo.getString(JsonKeyConstant.PARENT));
		parentIds.addAll(orgIds);
		return parentIds;
	}

	/**
	 * 获取由id
	 *
	 * @param orgId 组织id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getById(String orgId) {
		return HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);
	}

	@Override
	public Set<String> getAllOrgIds() {
		Set<String> ids = Sets.newHashSet();
		JSONArray orgList = HttpClientUtil.rawGet(MP_ORG_TREE_URL, JSONArray.class);

		// 空数组校验
		if (CollectionUtils.isEmpty(orgList)) {
			return ids;
		}

		// 空对象校验
		JSONObject realOrgTree = orgList.getJSONObject(CommonConstant.ZERO);
		if (realOrgTree.isEmpty()) {
			return ids;
		}

		for (int i = 0; i < orgList.size(); i++) {
			JSONObject org = orgList.getJSONObject(i);
			List<JSONObject> children = getChildrenIds(org);
			List<String> childrenIds = StreamApiUtils.map(children, o -> o.getString("id"));
			ids.addAll(childrenIds);
			ids.add(org.getString(JsonKeyConstant.ID));
		}

		return ids;
	}
}
