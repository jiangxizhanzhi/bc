
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