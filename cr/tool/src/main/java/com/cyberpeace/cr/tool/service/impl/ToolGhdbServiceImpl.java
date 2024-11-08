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
