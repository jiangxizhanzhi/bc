
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
import com.cyberpeace.space.controller.req.SpaceTrafficDetectRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleExcelBo;
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
 * 空间流量检测统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "规则库-网络攻击防御规则")
@RestController
@RequestMapping("/space/rule/space_traffic_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficDetectRuleController extends SpaceControllerBase<SpaceTrafficDetectRuleService, SpaceTrafficDetectRule, SpaceTrafficDetectRuleBo, SpaceTrafficDetectRulePageReq> {

    /**
     * 导出数据为Excel表格格式
     * 此方法响应一个GET请求，根据查询参数过滤数据，并将其导出到Excel表格中
     *
     * @param response HttpServletResponse对象，用于设置响应头和输出流
     * @param req 包含查询条件的请求对象，用于过滤要导出的数据
     * @return 返回一个BaseRsp对象，表示导出操作是否成功
     */
    @ApiOperation("导出成excel表格")
    @GetMapping("/excel")
    public BaseRsp<Boolean> exportExcel(HttpServletResponse response,
                                        SpaceTrafficDetectRulePageReq req) {
        // 生成文件路径和名称
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

        // 创建查询条件包装器
        LambdaQueryWrapper<SpaceTrafficDetectRule> wrapper = new LambdaQueryWrapper<>();
        // 根据请求参数设置查询条件
        if (StringUtils.isNotBlank(req.getTool())) {
            wrapper.like(SpaceTrafficDetectRule::getTool, req.getTool());
        }
        // 查询所有符合条件的记录
        List<SpaceTrafficDetectRule> all = baseService.list(wrapper);
        // 深拷贝查询结果到一个新的列表，准备导出
        List<SpaceTrafficDetectRuleExcelBo> exportBoList = BeanUtils.deepCopyList(all,
                SpaceTrafficDetectRuleExcelBo.class);

        // 数据导出
        ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAFFIC_DETECT_RULE);
        // 设置响应头和输出流，将文件下载到客户端
        FileUtil.setResponse(new File(filePath), true, response);

        // 返回成功标志
        return new BaseRsp<>(Boolean.TRUE);
    }

    @Override
	public boolean checkNameDuplication(SpaceTrafficDetectRule entity) {
        return true;

    }
}