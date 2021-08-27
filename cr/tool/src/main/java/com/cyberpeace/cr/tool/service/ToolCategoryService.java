package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.tool.controller.req.CategoryListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.service.bo.ToolCategoryBo;

public interface ToolCategoryService extends IService<ToolCategory> {

	ListRsp<ToolCategoryBo> selectPage(CategoryListReq listReq);
}
