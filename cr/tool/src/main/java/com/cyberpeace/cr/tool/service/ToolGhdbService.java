package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.service.bo.GhdbQueryContext;

public interface ToolGhdbService extends IService<ToolGhdb> {

	ListRsp<ToolGhdb> listGhdb(PageReqBase pageReqBase, GhdbQueryContext ghdbQueryContext);
}
