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
}
