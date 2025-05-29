package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 远程组织Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RemoteOrganizationService` 的接口，该接口主要用于处理远程组织相关的操作。以下是对其主要功能和方法的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.integratation.service` 
 * - **接口名**：`RemoteOrganizationService` 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * ### 方法分析 
 * 1. **childrenList** 
 * - **功能**：根据给定的组织编号 `orgId` 获取其所有子组织的编号列表。 
 * - **参数**： 
 * - `orgId`：父组织的编号。 
 * - `self`：布尔值，表示是否包含自身组织。 
 * - **返回值**：包含子组织编号的列表。 
 * 2. **getOrgPath** 
 * - **功能**：根据组织编号获取对应的组织路径（例如：计算机学院/大一/大一三班）。 
 * - **参数**： 
 * - `orgId`：组织的编号。 
 * - **返回值**：组织路径字符串。 
 * 3. **orgNameById** 
 * - **功能**：根据组织编号获取组织名称。 
 * - **参数**： 
 * - `orgId`：组织的编号。 
 * - **返回值**：组织名称字符串。 
 * 4. **childrenMap** 
 * - **功能**：根据组织编号获取其所有子组织的编号与名称映射关系。 
 * - **参数**： 
 * - `orgId`：父组织的编号。 
 * - `self`：布尔值，表示是否包含自身组织。 
 * - **返回值**：包含子组织编号与名称映射关系的 `Map` 对象。 
 * 5. **getOrgIdByTree** 
 * - **功能**：根据组织机构字符串获取组织编号。 
 * - **参数**： 
 * - `orgTree`：组织机构字符串。 
 * - **返回值**：组织编号字符串，如果查询不到则返回 `null` 或空字符串。 
 * 6. **classList** 
 * - **功能**：获取当前组织的所有子级对象集合，并以 JSON 数组形式返回。 
 * - **参数**： 
 * - `orgId`：组织的编号。 
 * - **返回值**：包含子组织信息的 JSON 数组。 
 * 7. **getParentIds** 
 * - **功能**：获取给定组织编号的所有父组织编号列表。 
 * - **参数**： 
 * - `orgId`：组织的编号。 
 * - **返回值**：包含父组织编号的列表。 
 * 8. **getById** 
 * - **功能**：根据组织编号获取完整的组织信息。 
 * - **参数**： 
 * - `orgId`：组织的编号。 
 * - **返回值**：包含组织信息的 JSON 对象。 
 * ### 总结 
 * `RemoteOrganizationService` 接口提供了处理组织层级结构的一系列方法，包括获取子组织、父组织、组织路径、组织名称、组织编号与名称映射等。这些方法通过不同的参数和返回值类型，满足了不同场景下的需求。 
**/
public interface RemoteOrganizationService {

	/**
	 * 获取子类组织编号
	 *
	 * @param orgId 组织编号
	 * @param self  是否包含自身
	 * @return
	 */
	List<String> childrenList(String orgId, boolean self);

	/**
	 * 根据组织编号获取对应组织树形结构 示例：计算机学院/大一/大一三班
	 *
	 * @param orgId 组织id
	 * @return
	 */
	String getOrgPath(String orgId);

	/**
	 * 获取组织名称
	 *
	 * @param orgId
	 * @return
	 */
	String orgNameById(String orgId);

	/**
	 * 获取子类组织信息 编号与名称映射关系
	 *
	 * @param orgId
	 * @param self
	 * @return key=orgId value=org_name
	 */
	Map<String, String> childrenMap(String orgId, boolean self);

	/**
	 * 根据组织机构获取组织编号 查询到了就正常显示 查询不到就返回 null or 空字符串 自己进行判断
	 *
	 * @param orgTree
	 * @return
	 */
	String getOrgIdByTree(String orgTree);

	/**
	 * 获取当前组织的所有子级对象集合
	 *
	 * @param orgId 组织id
	 * @return
	 */
	JSONArray classList(String orgId);

	/**
	 * 获取父组织编号
	 *
	 * @param orgId
	 * @return
	 */
	List<String> getParentIds(String orgId);

	/**
	 * 获取组织信息
	 *
	 * @param orgId 组织id
	 * @return
	 */
	JSONObject getById(String orgId);

}

