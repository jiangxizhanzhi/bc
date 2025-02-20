
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
import com.cyberpeace.space.controller.req.SpaceEventIntegrationRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleExcelBo;
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

@Api(tags = "知识库-规则库-事件综合规则")
@RestController
@RequestMapping("/space/rule/space_event_integration_rule")
@RequiresAuthentication
@Slf4j
public class SpaceEventIntegrationRuleController extends SpaceControllerBase<SpaceEventIntegrationRuleService, SpaceEventIntegrationRule, SpaceEventIntegrationRuleBo, SpaceEventIntegrationRulePageReq> {

    @ApiOperation("导出成excel表格")
    @GetMapping("/excel")
    public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
                                        SpaceEventIntegrationRulePageReq req) {
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

        LambdaQueryWrapper<SpaceEventIntegrationRule> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(req.getTool())) {
            wrapper.like(SpaceEventIntegrationRule::getTool, req.getTool());
        }
        if (StringUtils.isNotBlank(req.getType())) {
            wrapper.like(SpaceEventIntegrationRule::getType, req.getType());
        }
        List<SpaceEventIntegrationRule> all = baseService.list(wrapper);
        List<SpaceEventIntegrationRuleExcelBo> exportBoList = BeanUtils.deepCopyList(all,
                SpaceEventIntegrationRuleExcelBo.class);

        // 数据导出
        ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.EVENT_INTEGRATION_RULE);
        FileUtil.setResponse(new File(filePath), true, response);

        return new BaseRsp<>(Boolean.TRUE);
    }

    @Override
    public boolean checkNameDuplication(SpaceEventIntegrationRule entity) {
        return true;
    }
}