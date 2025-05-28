
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
     * 导出数据为Excel表格格式
     * 该接口处理导出操作，根据请求参数生成Excel文件，并提供给客户端下载
     *
     * @param response HttpServletResponse对象，用于设置响应头，以便客户端可以下载Excel文件
     * @param req 导出请求参数，包含需要查询的工具名称和类型信息
     * @return 返回一个BaseRsp对象，表示导出操作是否成功
     */
    @ApiOperation("导出成excel表格")
    @GetMapping("/excel")
    public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
                                        SpaceEventIntegrationRulePageReq req) {
        // 生成Excel文件的路径和名称
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

        // 创建查询条件
        LambdaQueryWrapper<SpaceEventIntegrationRule> wrapper = new LambdaQueryWrapper<>();
        // 根据请求参数中的工具名称进行模糊查询
        if (StringUtils.isNotBlank(req.getTool())) {
            wrapper.like(SpaceEventIntegrationRule::getTool, req.getTool());
        }
        // 根据请求参数中的类型进行模糊查询
        if (StringUtils.isNotBlank(req.getType())) {
            wrapper.like(SpaceEventIntegrationRule::getType, req.getType());
        }
        // 执行查询，获取所有符合条件的记录
        List<SpaceEventIntegrationRule> all = baseService.list(wrapper);
        // 将查询结果深拷贝到一个新的列表中，用于导出
        List<SpaceEventIntegrationRuleExcelBo> exportBoList = BeanUtils.deepCopyList(all,
                SpaceEventIntegrationRuleExcelBo.class);

        // 数据导出
        ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.EVENT_INTEGRATION_RULE);
        // 设置响应头，使客户端可以下载生成的Excel文件
        FileUtil.setResponse(new File(filePath), true, response);

        // 返回导出成功的信息
        return new BaseRsp<>(Boolean.TRUE);
    }

    @Override
    public boolean checkNameDuplication(SpaceEventIntegrationRule entity) {
        return true;
    }
}