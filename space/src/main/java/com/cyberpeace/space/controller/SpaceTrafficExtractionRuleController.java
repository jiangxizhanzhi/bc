package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceTrafficExtractionRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceTrafficExtractionRuleService;
import com.cyberpeace.space.service.context.rule.SpaceTrafficExtractionExcelBo;
import com.cyberpeace.space.service.context.rule.SpaceTrafficExtractionRuleBo;
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

/**
 * 空间流量萃取统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring Boot控制器类`SpaceTrafficExtractionRuleController`，主要负责处理与“空间流量提取规则”相关的业务逻辑。以下是代码的简要分析： 
 * ### 类的功能： 
 * 1. **控制器功能**：该类继承自`SpaceControllerBase`，并实现了与`SpaceTrafficExtractionRule`实体相关的CRUD操作。 
 * 2. **导出功能**：提供了一个API接口`/excel`，用于将符合条件的空间流量提取规则导出为Excel文件。 
 * 3. **唯一性校验**：重写了`checkNameDuplication`方法，用于确保规则名称的唯一性。 
 * ### 主要方法分析： 
 * #### 1. `exportExcel` 
 * - **功能**：根据请求参数导出符合条件的空间流量提取规则到Excel文件，并通过HTTP响应返回给客户端。 
 * - **步骤**： 
 * - 构建查询条件（如按名称或工具模糊查询）。 
 * - 查询数据库获取符合条件的数据。 
 * - 将查询结果转换为适合Excel导出的BO对象。 
 * - 使用`ExcelDataHandler`将数据写入Excel文件。 
 * - 设置HTTP响应头，使浏览器下载生成的Excel文件。 
 * #### 2. `checkNameDuplication` 
 * - **功能**：验证指定的规则名称是否重复。 
 * - **逻辑**： 
 * - 首先验证名称长度是否符合要求。 
 * - 查询数据库中是否存在与当前规则名称和内容完全相同的记录。 
 * - 如果发现重复记录且不是同一资源ID，则抛出业务异常。 
 * - 若无重复记录或为更新操作且ID匹配，则返回`true`。 
 * ### 技术亮点： 
 * - **MyBatis-Plus**：使用了`LambdaQueryWrapper`进行动态SQL构建，提高了代码的可读性和安全性。 
 * - **工具类**：利用了多个工具类（如`BeanUtils`、`FileUtil`、`IDGeneratorUtil`等），简化了业务逻辑实现。 
 * - **Swagger注解**：通过`@Api`和`@ApiOperation`描述接口用途，便于文档生成和API管理。 
 * - **日志记录**：使用`slf4j`记录日志，方便调试和监控。 
 * ### 总结： 
 * 此控制器类专注于处理空间流量提取规则的管理和导出功能，提供了清晰的业务逻辑分层和良好的扩展性。 
**/

@Api(tags = "知识库-规则库-网络流量提取规则")
@RestController
@RequestMapping("/space/rule/space_traffic_extraction_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficExtractionRuleController extends SpaceControllerBase<SpaceTrafficExtractionRuleService, SpaceTrafficExtractionRule, SpaceTrafficExtractionRuleBo, SpaceTrafficExtractionRulePageReq> {

	/**
	 * 导出空间流量提取规则到Excel表格
	 *
	 * @param response HTTP响应对象，用于导出文件
	 * @param req 导出请求参数，包含查询条件
	 * @return 返回导出操作的结果封装对象
	 */
	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
	        SpaceTrafficExtractionRulePageReq req) {
	    // 生成Excel文件路径和名称
	    String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
	            + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

	    // 创建查询条件包装器
	    LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
	    // 根据名称模糊查询
	    if (StringUtils.isNotBlank(req.getPattern())) {
	        wrapper.like(SpaceTrafficExtractionRule::getName, req.getPattern());
	    }
	    // 根据工具名称模糊查询
	    if (StringUtils.isNotBlank(req.getTool())) {
	        wrapper.like(SpaceTrafficExtractionRule::getTool, req.getTool());
	    }
	    // 执行查询，获取所有符合条件的记录
	    List<SpaceTrafficExtractionRule> all = baseService.list(wrapper);

	    // 将查询结果深拷贝到Excel导出所需的BO列表
	    List<SpaceTrafficExtractionExcelBo> exportBoList = BeanUtils.deepCopyList(all,
	            SpaceTrafficExtractionExcelBo.class);

	    // 数据导出
	    ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAFFIC_EXTRACTION);
	    // 设置HTTP响应，以附件形式返回Excel文件
	    FileUtil.setResponse(new File(filePath), true, response);

	    // 返回导出成功的结果
	    return new BaseRsp<>(Boolean.TRUE);
	}

	/**
	 * 检查空间交通提取规则的名称是否重复
	 * 此方法用于确保空间交通提取规则的名称在系统中是唯一的
	 * 它通过查询数据库中是否已存在相同名称和内容的规则来实现
	 * 如果存在名称重复且资源ID不同的规则，则抛出异常
	 *
	 * @param entity 空间交通提取规则对象，包含要检查的规则信息
	 * @return 如果名称不重复或为新规则，则返回true；否则抛出异常
	 * @throws BusinessException 当名称和内容重复且资源ID不匹配时，抛出此异常
	 */
	@Override
	public boolean checkNameDuplication(SpaceTrafficExtractionRule entity) {
	    // 验证规则名称长度是否合法
	    CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

	    // 创建查询条件，用于检查名称和内容是否重复
	    LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
	    wrapper.eq(SpaceTrafficExtractionRule::getName, entity.getName());
	    wrapper.eq(SpaceTrafficExtractionRule::getContent, entity.getContent());

	    // 执行查询，获取可能重复的规则
	    SpaceTrafficExtractionRule one = baseService.getOne(wrapper);

	    // 检查查询结果，如果找到名称和内容相同的规则，且传入规则为新规则或与数据库中的规则资源ID不相同，则抛出异常
	    if (null != one && (StringUtils.isBlank(entity.getResourceId())
	            || !one.getResourceId().equals(entity.getResourceId()))) {
	        throw new BusinessException(ErrorCode.NAME_CONTENT_DUPLICATE);
	    }

	    // 如果名称和内容不重复，或为更新操作且规则ID匹配，则返回true
	    return true;
	}
}
