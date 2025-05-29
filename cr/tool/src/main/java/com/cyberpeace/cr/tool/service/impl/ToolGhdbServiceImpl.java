package com.cyberpeace.cr.tool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.dao.mapper.ToolGhdbMapper;
import com.cyberpeace.cr.tool.service.ToolGhdbService;
import com.cyberpeace.cr.tool.controller.res.ToolGhdbRsp;

/**
 * 工具GHDB 数据库Service实现
 *
 * @author yinqingrong@date 2024/11/08
 */
/**
 * 这段代码是一个 Spring Boot 服务类的实现，用于处理工具数据库中 GHDB（可能是 "Good Hunting DataBase"）的数据操作。以下是对代码的简要分析： 
 * ### 包和导入 
 * - 代码位于 `com.cyberpeace.cr.tool.service.impl` 包中。 
 * - 导入了多个依赖包，包括 MyBatis-Plus 相关的类、自定义的模型类以及分页工具类。 
 * ### 类定义 
 * - `ToolGhdbServiceImpl` 类继承了 `ServiceImpl<ToolGhdbMapper, ToolGhdb>`，并实现了 `ToolGhdbService` 接口。 
 * - 使用 `@Service` 注解表明这是一个 Spring 管理的服务类。 
 * ### 成员变量 
 * - `private ToolGhdbMapper toolGhdbMapper;`：通过 `@Autowired` 注入了 `ToolGhdbMapper`，用于执行数据库操作。 
 * ### 方法 
 * - `listGhdb(PageReqBase pageReqBase, ToolGhdbRsp toolGhdbRsp)` 方法： 
 * - 参数 `pageReqBase` 是分页请求的基本信息。 
 * - 参数 `toolGhdbRsp` 是一个响应对象，可能包含查询条件（如作者和标题）。 
 * - 将 `pageReqBase` 转换为分页参数 `Page` 对象。 
 * - 调用 `toolGhdbMapper.list()` 方法进行数据查询，并传入分页参数和响应对象中的查询条件。 
 * - 使用 `PageUtil.getListResult()` 方法将查询结果转换为 `ListRsp<ToolGhdb>` 返回。 
 * ### 总结 
 * - 这段代码主要负责处理 GHDB 的数据列表查询操作。 
 * - 使用了 MyBatis-Plus 进行数据库操作，并通过分页插件实现了分页功能。 
 * - 方法中通过注入的 `ToolGhdbMapper` 执行具体的数据库查询，并返回带分页信息的结果。 
**/
@Service
public class ToolGhdbServiceImpl extends ServiceImpl<ToolGhdbMapper, ToolGhdb> implements ToolGhdbService {

	/**
	 * 工具GHDB 数据库Mapper
	 */
	@Autowired
	private ToolGhdbMapper toolGhdbMapper;

	/**
	 * 列出 GHDB
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param toolGhdbRsp 工具GHDB 数据库响应
	 * @return {@code ListRsp<ToolGhdb> }
	 */
	@Override
	public ListRsp<ToolGhdb> listGhdb(PageReqBase pageReqBase, ToolGhdbRsp toolGhdbRsp) {
		Page page = pageReqBase.toPageParam();

		IPage<ToolGhdb> result = toolGhdbMapper.list(page, toolGhdbRsp.getAuthor(),
				toolGhdbRsp.getTitle());

		return PageUtil.getListResult(result);
	}
}

