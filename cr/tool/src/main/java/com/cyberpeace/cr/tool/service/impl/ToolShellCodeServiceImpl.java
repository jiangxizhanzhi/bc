package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.dao.mapper.ToolShellCodeMapper;
import com.cyberpeace.cr.tool.service.ToolShellCodeService;
import com.cyberpeace.cr.tool.controller.res.ToolShellCodeRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工具壳代号Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class ToolShellCodeServiceImpl extends ServiceImpl<ToolShellCodeMapper, ToolShellCode>
		implements ToolShellCodeService {

	/**
	 * 工具壳代号Mapper
	 */
	@Autowired
	private ToolShellCodeMapper toolShellCodeMapper;

	/**
	 * 查询分页
	 *
	 * @param pageReqBase      分页请求体基础
	 * @param toolShellCodeRsp 工具壳代号响应
	 * @return {@code ListRsp<ToolShellCode> }
	 */
	@Override
	public ListRsp<ToolShellCode> selectPage(PageReqBase pageReqBase,
			ToolShellCodeRsp toolShellCodeRsp) {
		Page page = pageReqBase.toPageParam();
		IPage<ToolShellCode> result = toolShellCodeMapper
				.list(page, toolShellCodeRsp.getAuthor(), toolShellCodeRsp.getPlatform(),
						toolShellCodeRsp.getEdbVerified());
		result.convert(this::removeContent);
		return PageUtil.getListResult(result);
	}

	/**
	 * 删除内容
	 *
	 * @param toolShellCode 工具壳代号
	 * @return {@code ToolShellCode }
	 */
	public ToolShellCode removeContent(ToolShellCode toolShellCode) {
		toolShellCode.setContent("");
		return toolShellCode;
	}

}
