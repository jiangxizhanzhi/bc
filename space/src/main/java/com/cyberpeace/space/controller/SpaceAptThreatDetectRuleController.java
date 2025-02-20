
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

@Api(tags = "知识库-规则库-特定APT威胁检测规则")
@RestController
@RequestMapping("/space/rule/space_apt_threat_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceAptThreatDetectRuleController extends SpaceControllerBase<SpaceAptThreatDetectRuleService, SpaceAptThreatDetectRule, SpaceAptThreatDetectRuleBo, SpaceAptThreatDetectRulePageReq> {

	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
			SpaceAptThreatDetectRulePageReq req) {
		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

		LambdaQueryWrapper<SpaceAptThreatDetectRule> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(req.getApt_code())) {
			wrapper.like(SpaceAptThreatDetectRule::getAptCode, req.getApt_code());
		}
		if (StringUtils.isNotBlank(req.getTool())) {
			wrapper.like(SpaceAptThreatDetectRule::getTool, req.getTool());
		}
		List<SpaceAptThreatDetectRule> all = baseService.list(wrapper);
		List<SpaceAptThreatDetectRuleExcelBo> exportBoList = BeanUtils.deepCopyList(all,
				SpaceAptThreatDetectRuleExcelBo.class);

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.APT_THREAT_DETECT_RULE);
		FileUtil.setResponse(new File(filePath), true, response);

		return new BaseRsp<>(Boolean.TRUE);
	}

	@Override
	public boolean checkNameDuplication(SpaceAptThreatDetectRule entity) {
		return true;
	}
}