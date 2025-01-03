
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

@Api(tags = "知识库-规则库-负载文件威胁检测规则")
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
	@ApiOperation("导出成excel表格")
	@GetMapping("/excel")
	public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
										SpaceLoadedFileThreatRulePageReq req) {
		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

		LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(SpaceLoadedFileThreatRule::getName, req.getPattern());
		}
		if (StringUtils.isNotBlank(req.getTool())) {
			wrapper.like(SpaceLoadedFileThreatRule::getTool, req.getTool());
		}
		if (StringUtils.isNotBlank(req.getType())) {
			wrapper.like(SpaceLoadedFileThreatRule::getType, req.getType());
		}
		List<SpaceLoadedFileThreatRule> all = baseService.list(wrapper);

		List<SpaceLoadedFileThreatRuleExcelBo> exportBoList = StreamApiUtils.map(all, e->{
			SpaceLoadedFileThreatRuleExcelBo bo = BeanUtils.deepCopy(e, SpaceLoadedFileThreatRuleExcelBo.class);
			bo.setExecuteType(EXECUTE_TYPE_MAP.get(e.getExecuteType()));
			return bo;
		});

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.LOADED_FILE_THREAT_RULE);
		FileUtil.setResponse(new File(filePath), true, response);

		return new BaseRsp<>(Boolean.TRUE);
	}

	@Override
	public void checkNameDuplication(SpaceLoadedFileThreatRule entity) {}

}