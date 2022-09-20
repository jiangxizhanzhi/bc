package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;

import java.util.List;

public interface ToolCategoryService extends IService<ToolCategory> {

	ListRsp<ToolCategory> selectPage(PageReqBase listReq);

	List<ToolCategory> selectAll();

	List<WebToolCategoryRsp> webSelectAll();

	ToolCategory getByName(String name);

	ToolCategory saveCategory(String name);

	void batchDelete(List<String> resourceIds);

	void validateToolCategoryNameExist(ToolCategory toolCategory);

	/**
	 * 新增工具分类
	 * @param toolCategory 工具分类数据对象
	 */
	void saveToolCategory(ToolCategory toolCategory);

	/**
	 * 更新工具分类
	 * @param toolCategory 工具分类数据对象
	 */
	void updateToolCategoryById(ToolCategory toolCategory);
}
