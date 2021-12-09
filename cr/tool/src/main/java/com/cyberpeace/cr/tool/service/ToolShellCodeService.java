package com.cyberpeace.cr.tool.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.controller.res.ToolShellCodeRsp;

public interface ToolShellCodeService extends IService<ToolShellCode> {

	ListRsp<ToolShellCode> selectPage(PageReqBase pageReqBase,
			ToolShellCodeRsp toolShellCodeRsp);
}
