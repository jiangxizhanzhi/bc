package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceEventIntegrationRulePageReq;
import com.cyberpeace.space.controller.req.SpaceWarningDictionaryPageReq;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceWarningDictionaryService;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryExcelBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 空间警告字典Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 该代码定义了一个名为 `SpaceWarningDictionaryController` 的 Spring MVC 控制器类，用于处理与空间警告字典相关的请求。该类继承自 `SpaceControllerBase`，并实现了多个功能，包括导出 Excel 表格。 
 * --- 
 * #### 主要功能模块 
 * 1. **类定义与注解** 
 * - 使用了 `@RestController` 和 `@RequestMapping` 注解，指定了该类的请求路径为 `/space/rule/space_warning_dictionary`。 
 * - `@RequiresAuthentication` 表明此接口需要用户认证才能访问。 
 * - `@Slf4j` 提供日志功能。 
 * 2. **方法：`exportExcel`** 
 * - **功能描述**： 
 * 该方法接收 `HttpServletResponse` 和 `SpaceEventIntegrationRulePageReq` 参数，用于将符合条件的空间警告字典数据导出为 Excel 文件，并返回给客户端。 
 * - **逻辑流程**： 
 * 1. 生成唯一的 Excel 文件路径和名称。 
 * 2. 构造查询条件（`LambdaQueryWrapper`），支持按名称和工具类型模糊查询。 
 * 3. 查询数据库中的符合条件的数据。 
 * 4. 将查询结果通过工具类深拷贝为适合导出的格式（`SpaceWarningDictionaryExcelBo`）。 
 * 5. 调用 `ExcelDataHandler.exportExcel` 方法将数据写入 Excel 文件。 
 * 6. 设置 HTTP 响应头，将文件作为附件返回给客户端。 
 * - **技术点**： 
 * - 使用 `IDGeneratorUtil` 生成唯一文件名。 
 * - 利用 `BeanUtils.deepCopyList` 实现对象的深拷贝。 
 * - 通过 `FileUtil.setResponse` 设置文件下载响应。 
 * 3. **依赖注入** 
 * - 依赖于 `SpaceWarningDictionaryService` 提供的服务层功能。 
 * - 使用 `SpaceWarningDictionaryBo` 和 `SpaceWarningDictionaryExcelBo` 作为业务对象。 
 * --- 
 * #### 总结 
 * 该代码实现了一个功能完整的控制器类，主要负责空间警告字典数据的导出操作。它结合了 MyBatis-Plus 的查询功能、工具类的深拷贝与文件处理能力，以及 Spring MVC 的请求响应机制，最终实现了数据的导出与下载功能。代码结构清晰，注释详尽，便于维护和扩展。 
**/

@Api(tags = "知识库-规则库-攻击告警字典规则")
@RestController
@RequestMapping("/space/rule/space_warning_dictionary")
@RequiresAuthentication
@Slf4j
public class SpaceWarningDictionaryController extends SpaceControllerBase<SpaceWarningDictionaryService, SpaceWarningDictionary, SpaceWarningDictionaryBo, SpaceWarningDictionaryPageReq> {

	/**
	 * 导出数据为Excel表格格式
	 *
	 * @param response HTTP响应对象，用于输出导出的Excel文件
	 * @param req 请求参数对象，包含导出筛选条件
	 * @return 返回一个BaseRsp对象，表示导出操作是否成功
	 */
	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
	        SpaceEventIntegrationRulePageReq req) {
	    // 生成Excel文件的路径和名称
	    String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
	            + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

	    // 创建查询条件包装器
	    LambdaQueryWrapper<SpaceWarningDictionary> wrapper = new LambdaQueryWrapper<>();
	    // 根据名称模糊筛选
	    if (StringUtils.isNotBlank(req.getPattern())) {
	        wrapper.like(SpaceWarningDictionary::getName, req.getPattern());
	    }
	    // 根据工具类型模糊筛选
	    if (StringUtils.isNotBlank(req.getTool())) {
	        wrapper.like(SpaceWarningDictionary::getToolType, req.getTool());
	    }
	    // 执行查询，获取所有符合条件的记录
	    List<SpaceWarningDictionary> all = baseService.list(wrapper);
	    // 将查询结果深拷贝到一个新的列表中，准备导出
	    List<SpaceWarningDictionaryExcelBo> exportBoList = BeanUtils.deepCopyList(all,
	            SpaceWarningDictionaryExcelBo.class);

	    // 数据导出
	    ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.WARNING_DICTIONARY);
	    // 设置HTTP响应，将Excel文件作为附件提供下载
	    FileUtil.setResponse(new File(filePath), true, response);

	    // 返回导出成功的结果
	    return new BaseRsp<>(Boolean.TRUE);
	}
}
