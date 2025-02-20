
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

@Api(tags = "知识库-规则库-网络流量提取规则")
@RestController
@RequestMapping("/space/rule/space_traffic_extraction_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficExtractionRuleController extends SpaceControllerBase<SpaceTrafficExtractionRuleService, SpaceTrafficExtractionRule, SpaceTrafficExtractionRuleBo, SpaceTrafficExtractionRulePageReq> {

	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
			SpaceTrafficExtractionRulePageReq req) {
		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

		LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(SpaceTrafficExtractionRule::getName, req.getPattern());
		}
		if (StringUtils.isNotBlank(req.getTool())) {
			wrapper.like(SpaceTrafficExtractionRule::getTool, req.getTool());
		}
		List<SpaceTrafficExtractionRule> all = baseService.list(wrapper);

		List<SpaceTrafficExtractionExcelBo> exportBoList = BeanUtils.deepCopyList(all,
				SpaceTrafficExtractionExcelBo.class);

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAFFIC_EXTRACTION);
		FileUtil.setResponse(new File(filePath), true, response);

		return new BaseRsp<>(Boolean.TRUE);
	}

	@Override
	public boolean checkNameDuplication(SpaceTrafficExtractionRule entity) {
		CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceTrafficExtractionRule::getName, entity.getName());
		wrapper.eq(SpaceTrafficExtractionRule::getContent, entity.getContent());
		SpaceTrafficExtractionRule one = baseService.getOne(wrapper);
		if (null != one && (StringUtils.isBlank(entity.getResourceId())
				|| !one.getResourceId().equals(entity.getResourceId()))) {
			throw new BusinessException(ErrorCode.NAME_CONTENT_DUPLICATE);
		}
		return true;
	}
}