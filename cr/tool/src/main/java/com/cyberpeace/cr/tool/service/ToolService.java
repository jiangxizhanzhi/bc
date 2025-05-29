package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.context.TacticTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工具Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolService` 的接口，该接口继承了 `IService<Tool>` 接口。`ToolService` 提供了一系列用于管理和查询工具（`Tool`）的方法。以下是对其主要功能的简要分析： 
 * 1. **接口继承**: 
 * - `ToolService` 接口继承了 `IService<Tool>` 接口，这表明它可能使用了 MyBatis-Plus 框架来简化数据库操作。 
 * 2. **方法定义**: 
 * - `selectPage`: 根据分页数据、查询条件和是否为后台访问状态进行工具数据的分页查询。 
 * - `selectById`: 根据工具ID查询工具数据并返回 `ToolRsp` 对象。 
 * - `getByName`: 根据工具名称查询工具数据。 
 * - `validateToolNameExist`: 验证工具名称是否存在。 
 * - `validateEditToolNameExist`: 在编辑工具时验证工具名称是否重复。 
 * - `statistics`: 统计工具的数量并返回 `List<ToolTypeCount>` 对象。 
 * - `listByIdsInOrder`: 根据给定的ID列表按顺序列出工具数据。 
 * - `listTacticTool`: 根据工具ID列表返回 `List<TacticTool>` 对象。 
 * - `getIdNameMap`: 返回一个包含工具ID与名称映射的 `Map<String, String>`。 
 * - `checkBuiltin`: 检查内置工具。 
 * 3. **依赖类和接口**: 
 * - `IPage<ToolRsp>` 和 `Page` 用于分页处理。 
 * - `ToolListQuery` 用于封装查询条件。 
 * - `Tool`, `ToolRsp`, `ToolTypeCount`, `TacticTool` 是工具相关的实体或响应对象。 
 * 总结来说，`ToolService` 接口提供了全面的工具管理功能，包括分页查询、详细信息获取、名称验证以及统计和映射等操作。这些方法使得对工具数据的操作更加方便和高效。 
**/
public interface ToolService extends IService<Tool> {

	/**
	 * 工具查询分页
	 *
	 * @param page          分页数据
	 * @param toolListQuery 工具查询条件
	 * @param isAdmin       是否为后台访问
	 * @return 分页工具数据
	 */
	IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin);

	/**
	 * 根据工具ID查询工具数据
	 *
	 * @param resourceId 工具ID
	 * @return 工具数据对象
	 */
	ToolRsp selectById(@Param("resourceId") String resourceId);

	/**
	 * 根据工具名称查询工具数据
	 *
	 * @param name 工具名称
	 * @return 工具数据对象
	 */
	Tool getByName(String name);

	/**
	 * 验证工具名称是否重复
	 *
	 * @param tool 工具对象
	 */
	void validateToolNameExist(Tool tool);

	/**
	 * 验证修改时工具名称是否重复
	 *
	 * @param tool 工具对象
	 */
	void validateEditToolNameExist(Tool tool);

	/**
	 * 统计工具数量
	 *
	 * @return {@code List<ToolTypeCount> }
	 */
	List<ToolTypeCount> statistics();

	/**
	 * 列表由id列表在次序
	 *
	 * @param ids id列表
	 * @return {@code List<Tool> }
	 */
	List<Tool> listByIdsInOrder(List<String> ids);

	/**
	 * 列出策略工具
	 *
	 * @param toolIds 工具id列表
	 * @return {@code List<TacticTool> }
	 */
	List<TacticTool> listTacticTool(List<String> toolIds);

	/**
	 * 获取id名称映射
	 *
	 * @param toolIds 工具id列表
	 * @return {@code Map<String, String> }
	 */
	Map<String, String> getIdNameMap(List<String> toolIds);

	void checkBuiltin(String resourceId);
}

