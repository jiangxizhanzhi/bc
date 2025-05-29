package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceLoadedFileThreatRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleExcelBo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 空间加载文件威胁统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析： 
 * #### 1. **功能概述** 
 * 该代码实现了一个基于Spring MVC框架的RESTful接口，主要用于处理“空间加载文件威胁规则”的相关业务逻辑。具体功能包括： 
 * - 根据请求参数动态构建查询条件。 
 * - 查询符合条件的数据，并将其导出为Excel文件。 
 * - 提供了简单的重复名称检查方法。 
 * --- 
 * #### 2. **关键类与组件** 
 * - **`SpaceLoadedFileThreatRuleController`**：继承自`SpaceControllerBase`，负责处理与“空间加载文件威胁规则”相关的HTTP请求。 
 * - **`SpaceLoadedFileThreatRuleService`**：提供核心业务逻辑的服务层。 
 * - **`SpaceLoadedFileThreatRule`**：数据库实体类，表示“空间加载文件威胁规则”的数据模型。 
 * - **`SpaceLoadedFileThreatRuleExcelBo`**：用于Excel导出的业务对象，封装了需要导出的数据。 
 * - **`ExcelDataHandler`、`FileUtil`、`IDGeneratorUtil`**：工具类，分别用于Excel文件的生成、文件操作以及ID生成。 
 * - **`LambdaQueryWrapper`**：MyBatis-Plus提供的查询构造器，用于动态构建SQL查询条件。 
 * --- 
 * #### 3. **主要方法分析** 
 * - **`exportExcel` 方法** 
 * - 功能：将符合条件的“空间加载文件威胁规则”数据导出为Excel文件。 
 * - 流程： 
 * 1. 根据请求参数`SpaceLoadedFileThreatRulePageReq`动态构造查询条件（如名称、工具、类型）。 
 * 2. 调用`baseService.list(wrapper)`执行查询，获取所有符合条件的记录。 
 * 3. 使用`StreamApiUtils.map`将查询结果转换为适合Excel导出的格式（`SpaceLoadedFileThreatRuleExcelBo`），并补充额外字段（如执行类型）。 
 * 4. 调用`ExcelDataHandler.exportExcel`生成Excel文件，并通过`FileUtil.setResponse`设置HTTP响应头，使浏览器下载文件。 
 * - 技术点： 
 * - 使用了`Maps.newHashMap`静态初始化`EXECUTE_TYPE_MAP`，方便后续数据映射。 
 * - `BeanUtils.deepCopy`实现了对象的深拷贝，避免直接引用导致的问题。 
 * - 高效利用Java 8流式操作简化了数据转换过程。 
 * - **`checkNameDuplication` 方法** 
 * - 功能：检查指定实体的名称是否重复。 
 * - 实现：当前方法总是返回`true`，可能需要进一步完善逻辑以实际校验名称唯一性。 
 * --- 
 * #### 4. **技术亮点** 
 * - **模块化设计**：通过继承`SpaceControllerBase`复用了通用的CRUD逻辑，专注于特定业务场景。 
 * - **动态查询**：使用`LambdaQueryWrapper`动态构建SQL查询条件，灵活适配多种过滤需求。 
 * - **Excel导出**：结合工具类实现了一键导出功能，提升了用户体验。 
 * - **日志记录**：通过`@Slf4j`注解引入日志功能，便于排查问题。 
 * --- 
 * #### 5. **潜在改进点** 
 * - **异常处理**：当前代码未处理可能的异常情况（如数据库查询失败、文件生成失败等），建议添加异常捕获机制。 
 * - **性能优化**：若数据量较大，可考虑分页查询或异步导出。 
 * - **安全性**：`@RequiresAuthentication`确保接口仅限认证用户访问，但还需验证用户的权限范围，防止越权操作。 
 * - **命名规范**：部分变量名（如`req`、`wrapper`）较短，虽简洁但不够直观，建议优化。 
 * --- 
 * #### 6. **总结** 
 * 该代码实现了“空间加载文件威胁规则”的导出功能，设计合理、结构清晰，充分利用了Spring MVC、MyBatis-Plus及工具类的优势。但在异常处理、性能优化和安全细节上还有提升空间。 
**/

@Api(tags = "规则库-病毒威胁防御规则")
@RestController
@RequestMapping("/space/rule/space_loaded_file_threat_rule")
@RequiresAuthentication
@Slf4j
public class SpaceLoadedFileThreatRuleController extends SpaceControllerBase<SpaceLoadedFileThreatRuleService, SpaceLoadedFileThreatRule, SpaceLoadedFileThreatRuleBo, SpaceLoadedFileThreatRulePageReq> {

	private static final Map<Integer, String> EXECUTE_TYPE_MAP = Maps.newHashMap();

	static {
		EXECUTE_TYPE_MAP.put(1, "手动");
		EXECUTE_TYPE_MAP.put(2, "自动");
	}
	/**
	 * 导出数据为Excel表格格式
	 *
	 * @param response HTTP响应对象，用于输出生成的Excel文件
	 * @param req 请求参数对象，包含导出筛选条件
	 * @return 返回一个基础响应对象，表示导出操作是否成功
	 */
	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
	                                    SpaceLoadedFileThreatRulePageReq req) {
	    // 生成Excel文件的路径和名称
	    String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
	            + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

	    // 创建查询条件构造器
	    LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();
	    // 根据名称模式筛选
	    if (StringUtils.isNotBlank(req.getPattern())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getName, req.getPattern());
	    }
	    // 根据工具名称筛选
	    if (StringUtils.isNotBlank(req.getTool())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getTool, req.getTool());
	    }
	    // 根据类型筛选
	    if (StringUtils.isNotBlank(req.getType())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getType, req.getType());
	    }
	    // 执行查询，获取所有符合筛选条件的记录
	    List<SpaceLoadedFileThreatRule> all = baseService.list(wrapper);

	    // 将查询结果转换为Excel表格所需的格式
	    List<SpaceLoadedFileThreatRuleExcelBo> exportBoList = StreamApiUtils.map(all, e->{
	        SpaceLoadedFileThreatRuleExcelBo bo = BeanUtils.deepCopy(e, SpaceLoadedFileThreatRuleExcelBo.class);
	        bo.setExecuteType(EXECUTE_TYPE_MAP.get(e.getExecuteType()));
	        return bo;
	    });

	    // 数据导出
	    ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.LOADED_FILE_THREAT_RULE);
	    // 设置HTTP响应，以附件形式返回Excel文件
	    FileUtil.setResponse(new File(filePath), true, response);

	    // 返回成功响应
	    return new BaseRsp<>(Boolean.TRUE);
	}

	@Override
	public boolean checkNameDuplication(SpaceLoadedFileThreatRule entity) {
		return true;
	}

}
