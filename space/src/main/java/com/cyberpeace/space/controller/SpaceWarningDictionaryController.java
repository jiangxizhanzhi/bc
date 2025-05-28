
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