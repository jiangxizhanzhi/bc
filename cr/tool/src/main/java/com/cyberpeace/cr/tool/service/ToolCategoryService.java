package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;

public interface ToolCategoryService extends IService<ToolCategory> {

	ListRsp<ToolCategory> selectPage(PageReqBase listReq);

	ToolCategory getOrCreateByName(String name);
}
