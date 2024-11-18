package com.cyberpeace.cr.tool.service;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;

/**
 * 工具Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface ToolService extends IService<Tool> {

	/**
	 * 工具查询分页
	 *
	 * @param page          分页数据
	 * @param toolListQuery 工具查询条件
	 * @param isAdmin       是否为后台访问
	 * @return 分页工具数据
	 * @throws
	 * @see Page
	 * @see ToolListQuery
	 * @see Boolean
	 * @see IPage<ToolRsp>
	 */
	IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin);

	/**
	 * 根据工具ID查询工具数据
	 *
	 * @param resourceId 工具ID
	 * @return 工具数据对象
	 * @throws
	 * @see String
	 * @see ToolRsp
	 */
	ToolRsp selectById(@Param("resourceId") String resourceId);

	/**
	 * 根据工具名称查询工具数据
	 *
	 * @param name 工具名称
	 * @return 工具数据对象
	 * @throws
	 * @see String
	 * @see Tool
	 */
	Tool getByName(String name);

	/**
	 * 验证工具名称是否重复
	 *
	 * @param tool 工具对象
	 * @throws
	 * @see Tool
	 */
	void validateToolNameExist(Tool tool);

	/**
	 * 验证修改时工具名称是否重复
	 *
	 * @param tool 工具对象
	 * @throws
	 * @see Tool
	 */
	void validateEditToolNameExist(Tool tool);
}
