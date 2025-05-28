/**
 * 这段代码是一个Spring服务类 `RemoteOrganizationServiceImpl`，实现了 `RemoteOrganizationService` 接口。该类主要用于从远程系统获取组织结构相关的信息。以下是对代码的简要分析： 
 * ### 类定义和注解 
 * - **类名**：`RemoteOrganizationServiceImpl` 
 * - **注解**： 
 * - `@Service`：标记这是一个Spring管理的服务类。 
 * - `@Autowired`：用于自动装配依赖的 `MpEnvService`。 
 * ### 成员变量 
 * - `MP_ORG_TREE_URL` 和 `MP_ORG_INFO`：存储中台组织树和组织信息的URL，初始化时通过 `@PostConstruct` 注解的方法 `init()` 设置。 
 * ### 方法 
 * #### 初始化方法 
 * - **`init()`**：在构造完成后调用，设置 `MP_ORG_TREE_URL` 和 `MP_ORG_INFO` 的值。 
 * #### 组织信息获取方法 
 * - **`childrenList(String orgId, boolean self)`**：获取指定组织ID的子级组织列表。 
 * - **`getOrgPath(String orgId)`**：获取组织路径。 
 * - **`orgNameById(String orgId)`**：根据组织ID获取组织名称。 
 * - **`childrenMap(String orgId, boolean self)`**：获取指定组织ID的子级组织映射（ID到名称）。 
 * - **`getOrgIdByTree(String orgTree)`**：根据组织路径字符串获取组织ID。 
 * - **`getChildrenIds(JSONObject orgTree)`**：递归获取组织树中的所有子级组织。 
 * #### 其他方法 
 * - **`classList(String orgId)`**：获取指定组织ID的所有子级组织对象集合。 
 * - **`getParentIds(String orgId)`**：获取指定组织ID的所有父级组织ID列表。 
 * - **`getById(String orgId)`**：根据组织ID获取组织信息。 
 * ### 主要技术点 
 * - **依赖注入**：使用 `@Autowired` 注入 `MpEnvService`。 
 * - **HTTP请求**：使用 `HttpClientUtil.rawGet` 发送HTTP GET请求以获取JSON数据。 
 * - **数据处理**：使用 `JSONArray` 和 `JSONObject` 处理JSON数据，并使用 `StreamApiUtils` 进行流式操作。 
 * - **异常处理**：通过检查 `JSONArray` 和 `JSONObject` 是否为空来处理潜在的空数据情况。 
 * ### 总结 
 * 该类主要负责与远程系统交互以获取组织结构信息，并提供了一系列方法来处理这些信息。代码结构清晰，逻辑明确，使用了Spring框架的特性来管理依赖和服务。 
**/
package com.cyberpeace.integratation.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

/**
 * 远程组织Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class RemoteOrganizationServiceImpl implements RemoteOrganizationService {

	/**
	 * 中台组织树网址
	 */
	private String MP_ORG_TREE_URL;

	/**
	 * 中台组织信息
	 */
	private String MP_ORG_INFO;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String mpHost = mpEnvService.getMpHost();
		MP_ORG_TREE_URL = mpHost + ConfigUtil.getContextProperty("mp.org.tree");
		MP_ORG_INFO = mpHost + ConfigUtil.getContextProperty("mp.org.info");
	}

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 子级列表
	 *
	 * @param orgId 组织id
	 * @param self  自我
	 * @return {@code List<String> }
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
	 */
	@Override
	public String orgNameById(String orgId) {
		JSONObject orgInfo = HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);
		return null != orgInfo ? orgInfo.getString("name") : "";
	}

	/**
	 * 子级地图
	 *
	 * @param orgId 组织id
	 * @param self  自我
	 * @return {@code Map<String, String> }
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
	 */
	@Override
	public JSONObject getById(String orgId) {
		return HttpClientUtil.rawGet(MP_ORG_INFO + orgId, JSONObject.class);
	}
}
