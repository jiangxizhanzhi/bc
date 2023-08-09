package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import org.apache.ibatis.annotations.Param;

public interface ToolService extends IService<Tool> {

	/**
	 * 工具查询分页
	 * @param page 分页数据
	 * @param toolListQuery 工具查询条件
	 * @param isAdmin 是否为后台访问
	 * @return 分页工具数据
	 */
	IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin, AuthContext authContext);

	IPage<ToolRsp> selectNameSortPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin);

	/**
	 * 根据工具ID查询工具数据
	 * @param resourceId 工具ID
	 * @return 工具数据对象
	 */
	ToolRsp selectById(@Param("resourceId") String resourceId);

	/**
	 * 根据工具名称查询工具数据
	 * @param name 工具名称
	 * @return 工具数据对象
	 */
	Tool getByName(String name);

	/**
	 * 验证工具名称是否重复
	 * @param tool 工具对象
	 */
	void validateToolNameExist(Tool tool);

	/**
	 * 验证修改时工具名称是否重复
	 * @param tool 工具对象
	 */
	void validateEditToolNameExist(Tool tool);


	/**
	 * 新增
	 */
	void insert(ToolAddReq req);


	/**
	 * 修改
	 */
	void update(ToolUpdateReq req);


	/**
	 * 批量修改多个训练模板的权限
	 *
	 * @param shareContext 授权信息
	 */
	void batchSetShares(ShareContext shareContext);

}
