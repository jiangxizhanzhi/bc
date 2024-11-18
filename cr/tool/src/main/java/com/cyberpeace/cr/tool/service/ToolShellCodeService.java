package com.cyberpeace.cr.tool.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.controller.res.ToolShellCodeRsp;

/**
 * 工具壳代号Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface ToolShellCodeService extends IService<ToolShellCode> {

	/**
	 * 查询分页
	 *
	 * @param pageReqBase      分页请求体基础
	 * @param toolShellCodeRsp 工具壳代号响应
	 * @return {@code ListRsp<ToolShellCode> }
	 * @throws
	 * @see PageReqBase
	 * @see ToolShellCodeRsp
	 * @see ListRsp<ToolShellCode>
	 */
	ListRsp<ToolShellCode> selectPage(PageReqBase pageReqBase,
			ToolShellCodeRsp toolShellCodeRsp);
}
