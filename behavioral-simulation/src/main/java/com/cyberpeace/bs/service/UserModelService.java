package com.cyberpeace.bs.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.bo.model.*;
import com.cyberpeace.common.model.ListRsp;

public interface UserModelService {

	/**
	 * 获取用户行为列表
	 * @param page 分页
	 * @param userType 用户类型
	 * @param pattern 用户名模糊匹配
	 * @return 用户行为分页列表
	 */
	ListRsp<UserModelBo> listUserModel(Page page, Integer userType, String pattern);

	/**
	 * 获取用户行为模拟详情
	 * @param resourceId 编号
	 * @return 用户行为模拟详情
	 */
	UserModelDetail getDetail(String resourceId);

	/**
	 * 新增用户行为模拟
	 * @param addBo 前端输入
	 */
	void create(UserModelAddBo addBo);

    /**
     * 更新用户行为模拟详情
     * @param modifyBo 前端输入编辑
     */
	void update(UserModelModifyBo modifyBo);

	/**
	 * 删除用户应为模拟
	 * @param resourceIds 要删除的id
	 */
	void delete(List<String> resourceIds);

	/**
	 * 生成行为的时间、以及图形
	 * @param chartInput 输入的行为和配置
	 * @return 时间和图形
	 */
	ChartResult createChart(ChartInput chartInput);
}
