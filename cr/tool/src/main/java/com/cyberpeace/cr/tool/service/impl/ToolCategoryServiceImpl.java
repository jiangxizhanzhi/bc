package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.req.CategoryListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import com.cyberpeace.cr.tool.service.bo.ToolCategoryBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory>
		implements ToolCategoryService {


	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	private ToolCategoryBo toBo(ToolCategory toolCategory) {
		ToolCategoryBo toolCategoryBo = new ToolCategoryBo();
		BeanUtils.copyProperties(toolCategory, toolCategoryBo);
		return toolCategoryBo;
	}

	@Override
	public ListRsp<ToolCategoryBo> selectPage(CategoryListReq listReq) {
		Page page = listReq.toPageParam();
		IPage<ToolCategory> result = toolCategoryMapper.list(page, listReq.getPattern());
		result.convert(this::toBo);
		return PageUtil.getListResult(result);
	}
}
