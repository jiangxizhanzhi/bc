package com.cyberpeace.bs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorAddBo;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorBo;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorDetail;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorModifyBo;
import com.cyberpeace.common.model.ListRsp;

import java.util.List;

public interface UserBehaviorService {

	/**
	 * 获取用户行为列表
	 * @param page 分页
	 * @param userType 用户类型
	 * @param pattern 用户名模糊匹配
	 * @return 用户行为分页列表
	 */
	ListRsp<UserBehaviorBo> listUserBehavior(Page page, Integer userType, String pattern);

	/**
	 * 获取用户行为模拟详情
	 * @param resourceId 编号
	 * @return 用户行为模拟详情
	 */
	UserBehaviorDetail getDetail(String resourceId);

	/**
	 * 新增用户行为模拟
	 * @param addBo 前端输入
	 */
	void create(UserBehaviorAddBo addBo);

    /**
     * 更新用户行为模拟详情
     * @param modifyBo 前端输入编辑
     */
    void update(UserBehaviorModifyBo modifyBo);

	/**
	 * 删除用户应为模拟
	 * @param resourceIds 要删除的id
	 */
	void delete(List<String> resourceIds);
}
