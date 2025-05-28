
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