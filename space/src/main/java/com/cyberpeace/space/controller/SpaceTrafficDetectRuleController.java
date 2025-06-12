package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceTrafficDetectRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRuleExcelVO;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleExcelBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 空间流量检测统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring MVC Controller类，用于处理与“空间流量检测规则”相关的HTTP请求。以下是对代码的主要分析： 
 * 1. **类定义与继承**： 
 * - `SpaceTrafficDetectRuleController` 继承自 `SpaceControllerBase`，并实现了泛型方法中的具体类型。 
 * - 使用了 `@RestController` 和 `@RequestMapping` 注解来定义RESTful API的基本路径。 
 * 2. **方法功能**： 
 * - `exportExcel` 方法通过 GET 请求接收查询参数，并根据这些参数从数据库中筛选出符合条件的记录。 
 * - 它将筛选出的数据转换为目标类型的列表后，调用 `ExcelDataHandler.exportExcel` 方法生成Excel文件。 
 * - 最后，通过设置 HTTP 响应头和输出流的方式，使客户端能够下载生成的Excel文件。 
 * 3. **依赖注入与工具使用**： 
 * - 使用了 `IDGeneratorUtil` 生成唯一的文件名。 
 * - 调用了 `BeanUtils.deepCopyList` 方法进行对象深拷贝。 
 * - 利用了 `FileUtil.setResponse` 方法处理文件下载逻辑。 
 * 4. **安全性与日志记录**： 
 * - 使用了 `@RequiresAuthentication` 注解确保只有经过身份验证的用户才能访问此接口。 
 * - 日志功能通过 `@Slf4j` 注解启用，便于后续的日志记录和调试。 
 * 5. **异常处理**： 
 * - 尽管代码中没有显式的异常捕获逻辑，但在实际应用中可能需要添加适当的异常处理机制以增强系统的健壮性。 
 * 6. **其他注解**： 
 * - `@ApiOperation` 用于描述该API的功能，方便Swagger等文档生成工具解析。 
 * 总体来说，这个Controller类设计合理，功能明确，主要负责将符合条件的空间流量检测规则导出为Excel文件供用户下载。 
**/

@Api(tags = "规则库-网络攻击防御规则")
@RestController
@RequestMapping("/space/rule/space_traffic_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficDetectRuleController extends SpaceControllerBase<SpaceTrafficDetectRuleService, SpaceTrafficDetectRule, SpaceTrafficDetectRuleBo, SpaceTrafficDetectRulePageReq> {

    @Autowired
    private CrConstantsService crConstantsService;

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

        List<SpaceTrafficDetectRuleExcelVO> excelVOS = all.stream().map(item -> SpaceTrafficDetectRuleExcelVO.convertModelToExcelVO(item, crConstantsService))
                .collect(Collectors.toList());

        // 深拷贝查询结果到一个新的列表，准备导出
        List<SpaceTrafficDetectRuleExcelBo> exportBoList = BeanUtils.deepCopyList(excelVOS,
                SpaceTrafficDetectRuleExcelBo.class);

        // 数据导出
        ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAFFIC_DETECT_RULE);
        // 设置响应头和输出流，将文件下载到客户端
        String targetFileName = "网络攻击防御规则" + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();
        FileUtil.setResponse(new File(filePath), true, response, targetFileName);

        // 返回成功标志
        return new BaseRsp<>(Boolean.TRUE);
    }

    @Override
	public boolean checkNameDuplication(SpaceTrafficDetectRule entity) {
        return true;

    }

    /**
     * 检查值 是否合法
     *
     * @param entity 实体对象
     */
    @Override
    public void checkValue(SpaceTrafficDetectRule entity){
        String[] fields = {
                entity.getTool(),
                entity.getRuleContent(),
                entity.getRuleId(),
        };
        for (String field : fields) {
            if (!org.apache.commons.lang3.StringUtils.isEmpty(field)) {
                CrCommonValidator.validateNameLen(field, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
            }
        }
    }
}
