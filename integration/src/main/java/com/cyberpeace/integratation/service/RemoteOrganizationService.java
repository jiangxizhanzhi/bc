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
