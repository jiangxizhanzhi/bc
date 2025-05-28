
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

/**
 * 空间事件集成统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "规则库-主机攻击防御规则")
@RestController
@RequestMapping("/space/rule/space_event_integration_rule")
@RequiresAuthentication
@Slf4j
public class SpaceEventIntegrationRuleController extends SpaceControllerBase<SpaceEventIntegrationRuleService, SpaceEventIntegrationRule, SpaceEventIntegrationRuleBo, SpaceEventIntegrationRulePageReq> {

    /**
     * 导出胜过
     *
     * @param response 响应
     * @param req      请求体
     * @return base rsp< 布尔值>
     * @throws
     * @see HttpServletResponse
     * @see SpaceEventIntegrationRulePageReq
     * @see BaseRsp<Boolean>
     */
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

    /**
     * 检查名称重复
     *
     * @param entity 实体
     * @return boolean
     * @throws
     * @see SpaceEventIntegrationRule
     * @see boolean
     */
    @Override
    public boolean checkNameDuplication(SpaceEventIntegrationRule entity) {
        return true;
    }
}