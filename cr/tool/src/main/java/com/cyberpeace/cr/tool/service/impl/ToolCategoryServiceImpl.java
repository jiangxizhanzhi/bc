package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory>
		implements ToolCategoryService {


	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	@Override
	public ListRsp<ToolCategory> selectPage(PageReqBase listReq) {
		Page page = listReq.toPageParam();
		IPage<ToolCategory> result = toolCategoryMapper.list(page, listReq.getPattern());
		return PageUtil.getListResult(result);
	}
}
