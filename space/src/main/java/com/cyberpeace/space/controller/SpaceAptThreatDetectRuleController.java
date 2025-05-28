
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
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceAptThreatDetectRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptThreatDetectRuleService;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleExcelBo;
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
 * 空间容易威胁检测统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "规则库-特定APT威胁检测规则")
@RestController
@RequestMapping("/space/rule/space_apt_threat_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceAptThreatDetectRuleController extends SpaceControllerBase<SpaceAptThreatDetectRuleService, SpaceAptThreatDetectRule, SpaceAptThreatDetectRuleBo, SpaceAptThreatDetectRulePageReq> {

	/**
	 * 导出数据为Excel表格格式
	 *
	 * @param response HTTP响应对象，用于输出Excel文件
	 * @param req 请求参数对象，包含查询条件
	 * @return 返回一个基础响应对象，表示导出操作是否成功
	 */
	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
	        SpaceAptThreatDetectRulePageReq req) {
	    // 生成Excel文件的路径和名称
	    String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
	            + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

	    // 创建查询条件包装器
	    LambdaQueryWrapper<SpaceAptThreatDetectRule> wrapper = new LambdaQueryWrapper<>();
	    // 根据请求参数设置查询条件
	    if (StringUtils.isNotBlank(req.getApt_code())) {
	        wrapper.like(SpaceAptThreatDetectRule::getAptCode, req.getApt_code());
	    }
	    if (StringUtils.isNotBlank(req.getTool())) {
	        wrapper.like(SpaceAptThreatDetectRule::getTool, req.getTool());
	    }
	    // 查询符合条件的所有记录
	    List<SpaceAptThreatDetectRule> all = baseService.list(wrapper);
	    // 将查询结果深拷贝到一个新的列表中，用于导出
	    List<SpaceAptThreatDetectRuleExcelBo> exportBoList = BeanUtils.deepCopyList(all,
	            SpaceAptThreatDetectRuleExcelBo.class);

	    // 数据导出
	    ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.APT_THREAT_DETECT_RULE);
	    // 设置HTTP响应，将Excel文件作为附件提供
	    FileUtil.setResponse(new File(filePath), true, response);

	    // 返回成功响应
	    return new BaseRsp<>(Boolean.TRUE);
	}

	@Override
	public boolean checkNameDuplication(SpaceAptThreatDetectRule entity) {
		return true;
	}
}