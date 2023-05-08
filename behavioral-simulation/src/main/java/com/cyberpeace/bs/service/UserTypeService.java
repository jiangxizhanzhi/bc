package com.cyberpeace.bs.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.bo.user.UserTypeAddBo;
import com.cyberpeace.bs.service.bo.user.UserTypeBo;
import com.cyberpeace.bs.service.bo.user.UserTypeModifyBo;
import com.cyberpeace.common.model.ListRsp;

/**
 * 用户类型Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface UserTypeService {

	/**
	 * 获取用户类型分页列表
	 * @param page 分页参数
	 * @param search 模糊搜索
	 * @return 用户类型分页列表
	 */
	ListRsp<UserTypeBo> pageTypes(Page page, String search);

	/**
	 * 创建用户类型
	 * @param addBo 用户类型
	 */
	void create(UserTypeAddBo addBo);

	/**
	 * 更新用户类型
	 * @param modifyBo 更新数据
	 */
	void update(UserTypeModifyBo modifyBo);

	/**
	 * 批量删除用户类型
	 * @param ids 待删除的id
	 */
	void batchDelete(List<String> ids);

	/**
	 * 获取id-name的映射
	 * @param ids id集合
	 * @return id-name的映射
	 */
	Map<String, String> getIdNameMap(Collection<String> ids);

	/**
	 * 从id获取用户类型名称
	 * @param resourceId id
	 * @return 用户类型名称
	 */
	String getNameById(String resourceId);
}
