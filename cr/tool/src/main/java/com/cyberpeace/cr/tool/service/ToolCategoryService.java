package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;

import java.util.List;

/**
 * 工具类别Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface ToolCategoryService extends IService<ToolCategory> {

	/**
	 * 工具类型分页
	 *
	 * @param listReq 分页参数
	 * @return 分页后工具分类
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<ToolCategory>
	 */
	ListRsp<ToolCategory> selectPage(PageReqBase listReq);

	/**
	 * 查询所有工具分类
	 *
	 * @return 所有工具分类信息列表
	 * @throws
	 * @see List<ToolCategory>
	 */
	List<ToolCategory> selectAll();

	/**
	 * 前台查询所有工具分类（区分用户设置语言, 进行国际化处理）
	 *
	 * @return 所有工具分类列表
	 * @throws
	 * @see List<WebToolCategoryRsp>
	 */
	List<WebToolCategoryRsp> webSelectAll();

	/**
	 * 根据工具分类名称获取工具分类信息
	 *
	 * @param name 工具分类名称
	 * @return 工具分类数据对象
	 * @throws
	 * @see String
	 * @see ToolCategory
	 */
	ToolCategory getByName(String name);

	/**
	 * 创建工具分类
	 *
	 * @param name 工具分类名称
	 * @return 工具分类数据对象
	 * @throws
	 * @see String
	 * @see ToolCategory
	 */
	ToolCategory saveCategory(String name);

	/**
	 * 批量删除工具分类
	 *
	 * @param resourceIds 工具分类ID列表
	 * @throws
	 * @see List<String>
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 验证工具分类名称是否重复
	 *
	 * @param toolCategory 工具分类数据对象
	 * @throws
	 * @see ToolCategory
	 */
	void validateToolCategoryNameExist(ToolCategory toolCategory);

	/**
	 * 新增工具分类
	 *
	 * @param toolCategory 工具分类数据对象
	 * @throws
	 * @see ToolCategory
	 */
	void saveToolCategory(ToolCategory toolCategory);

	/**
	 * 更新工具分类
	 *
	 * @param toolCategory 工具分类数据对象
	 * @throws
	 * @see ToolCategory
	 */
	void updateToolCategoryById(ToolCategory toolCategory);
}
