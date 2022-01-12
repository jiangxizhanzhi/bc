package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import org.apache.ibatis.annotations.Param;

public interface ToolService extends IService<Tool> {

    IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin);

    ToolRsp selectById(@Param("resourceId") String resourceId);

    Tool getByName(String name);

    void validateToolNameExist(Tool tool);
}
