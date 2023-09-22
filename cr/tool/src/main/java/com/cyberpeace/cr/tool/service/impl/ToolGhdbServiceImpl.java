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
 * @author yinqingrong
 */
@Service
public class ToolGhdbServiceImpl extends ServiceImpl<ToolGhdbMapper, ToolGhdb> implements ToolGhdbService {

	@Autowired
	private ToolGhdbMapper toolGhdbMapper;

	@Override
	public ListRsp<ToolGhdb> listGhdb(PageReqBase pageReqBase, ToolGhdbRsp toolGhdbRsp) {
		Page<ToolGhdb> page = pageReqBase.toPageParam();

		IPage<ToolGhdb> result = toolGhdbMapper.list(page, toolGhdbRsp.getAuthor(),
				toolGhdbRsp.getTitle());

		return PageUtil.getListResult(result);
	}
}
