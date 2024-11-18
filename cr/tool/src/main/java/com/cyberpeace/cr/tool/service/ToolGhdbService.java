package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.controller.res.ToolGhdbRsp;

/**
 * 工具GHDB 数据库Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface ToolGhdbService extends IService<ToolGhdb> {

	/**
	 * 列出 GHDB
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param toolGhdbRsp 工具GHDB 数据库响应
	 * @return {@code ListRsp<ToolGhdb> }
	 * @throws
	 * @see PageReqBase
	 * @see ToolGhdbRsp
	 * @see ListRsp<ToolGhdb>
	 */
	ListRsp<ToolGhdb> listGhdb(PageReqBase pageReqBase, ToolGhdbRsp toolGhdbRsp);
}
