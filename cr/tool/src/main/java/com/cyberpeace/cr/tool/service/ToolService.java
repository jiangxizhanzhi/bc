package com.cyberpeace.cr.tool.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.bo.ToolBo;

public interface ToolService extends IService<Tool> {

	ListRsp<ToolBo> selectPage(PageReqBase toolListReq);
}
