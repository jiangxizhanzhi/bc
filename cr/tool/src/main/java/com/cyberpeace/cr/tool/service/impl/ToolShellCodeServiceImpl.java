/**
 * 这段代码定义了一个名为 `ToolShellCodeServiceImpl` 的服务实现类，该类实现了 `ToolShellCodeService` 接口。以下是代码的主要组成部分和功能分析： 
 * ### 类定义 
 * - **类名**: `ToolShellCodeServiceImpl` 
 * - **继承**: `ServiceImpl<ToolShellCodeMapper, ToolShellCode>`：继承了 MyBatis-Plus 提供的 `ServiceImpl` 类，用于简化数据库操作。 
 * - **注解**: `@Service`：表明这是一个 Spring 管理的服务类。 
 * ### 成员变量 
 * - **toolShellCodeMapper**: 通过 `@Autowired` 注入的 `ToolShellCodeMapper` 对象，用于与数据库进行交互。 
 * ### 方法 
 * 1. **selectPage** 
 * - **功能**: 根据分页请求参数查询工具壳代号数据，并返回分页结果。 
 * - **参数**: 
 * - `pageReqBase`: 分页请求体基础，包含分页信息。 
 * - `toolShellCodeRsp`: 包含查询条件（如作者、平台、验证状态）的对象。 
 * - **返回值**: `ListRsp<ToolShellCode>`：分页结果列表。 
 * - **过程**: 
 * - 将 `pageReqBase` 转换为 `Page` 对象。 
 * - 调用 `toolShellCodeMapper.list` 方法查询数据。 
 * - 对查询结果进行转换处理，调用 `removeContent` 方法移除内容。 
 * - 使用 `PageUtil.getListResult` 方法将结果封装为 `ListRsp` 对象并返回。 
 * 2. **removeContent** 
 * - **功能**: 移除工具壳代号的内容。 
 * - **参数**: `ToolShellCode` 对象。 
 * - **返回值**: 处理后的 `ToolShellCode` 对象。 
 * - **过程**: 将传入对象的 `content` 字段设置为空字符串。 
 * ### 总结 
 * - 该类主要负责工具壳代号的分页查询和内容处理。 
 * - 利用了 MyBatis-Plus 的 `ServiceImpl` 类来简化数据库操作。 
 * - 数据查询和处理逻辑清晰，易于维护和扩展。 
**/
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
 * @date 2024/11/08
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
