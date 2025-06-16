package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.space.controller.req.SpaceEventIntegrationRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceCallCountService;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleExcelBo;
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

/**
 * 空间事件集成统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码定义了一个 `SpaceEventIntegrationRuleController` 类，用于处理与空间事件集成规则相关的业务逻辑。主要功能包括： 
 * 1. **导出数据为 Excel 表格**：根据请求参数生成 Excel 文件并提供给客户端下载。 
 * 2. **校验规则名称重复性**：通过重写的 `checkNameDuplication` 方法实现。 
 * --- 
 * #### 主要类和方法解析 
 * ##### 1. 类结构 
 * - **继承关系**： 
 * - `SpaceEventIntegrationRuleController` 继承自 `SpaceControllerBase`，并提供了泛型参数： 
 * - `SpaceEventIntegrationRuleService`：服务层接口。 
 * - `SpaceEventIntegrationRule`：实体类。 
 * - `SpaceEventIntegrationRuleBo`：业务对象。 
 * - `SpaceEventIntegrationRulePageReq`：分页请求参数。 
 * - **注解**： 
 * - `@Api`：用于 Swagger 文档生成，标注模块为“规则库-主机攻击防御规则”。 
 * - `@RestController` 和 `@RequestMapping`：定义了控制器的基本信息和访问路径。 
 * - `@RequiresAuthentication`：需要用户认证才能访问。 
 * - `@Slf4j`：日志支持。 
 * ##### 2. 导出 Excel 方法 (`exportExcel`) 
 * - **功能**： 
 * - 根据请求参数生成 Excel 文件，并返回成功标志。 
 * - **步骤**： 
 * 1. **生成文件路径**： 
 * - 使用 `AttachmentPath.OTHER.getDesc()` 和随机生成的 ID 拼接文件名。 
 * 2. **构建查询条件**： 
 * - 使用 `LambdaQueryWrapper` 构建查询条件，支持模糊匹配工具名称 (`tool`) 和类型 (`type`)。 
 * 3. **执行查询**： 
 * - 调用 `baseService.list(wrapper)` 获取符合条件的数据列表。 
 * 4. **数据转换**： 
 * - 使用 `BeanUtils.deepCopyList` 将查询结果转换为目标对象 `SpaceEventIntegrationRuleExcelBo`。 
 * 5. **导出 Excel**： 
 * - 调用 `ExcelDataHandler.exportExcel` 将数据写入 Excel 文件。 
 * 6. **设置响应头**： 
 * - 使用 `FileUtil.setResponse` 配置 HTTP 响应，使客户端能够下载文件。 
 * 7. **返回结果**： 
 * - 返回一个 `BaseRsp<Boolean>` 对象，表示导出成功。 
 * ##### 3. 校验规则名称重复性 (`checkNameDuplication`) 
 * - **功能**： 
 * - 简单地返回 `true`，表明当前实现不支持重复名称检查。 
 * --- 
 * #### 技术亮点 
 * 1. **MyBatis-Plus 支持**： 
 * - 使用 `LambdaQueryWrapper` 提供类型安全的查询构造。 
 * 2. **工具类封装**： 
 * - `IDGeneratorUtil`、`BeanUtils`、`FileUtil` 等工具类简化了开发工作。 
 * 3. **Swagger 集成**： 
 * - 通过 `@Api` 注解，自动生成 API 文档。 
 * 4. **Excel 导出**： 
 * - 使用 `ExcelDataHandler` 和 `FileUtil` 快速完成 Excel 文件的生成与下载。 
 * --- 
 * #### 总结 
 * 该代码实现了导出空间事件集成规则为 Excel 文件的核心功能，同时提供了基础的重复名称校验方法。整体结构清晰，依赖的工具类和框架（如 MyBatis-Plus、Swagger）提升了开发效率，适合在企业级项目中使用。 
**/

@Api(tags = "规则库-主机攻击防御规则")
@RestController
@RequestMapping("/space/rule/space_event_integration_rule")
@RequiresAuthentication
@Slf4j
public class SpaceEventIntegrationRuleController extends SpaceControllerBase<SpaceEventIntegrationRuleService, SpaceEventIntegrationRule, SpaceEventIntegrationRuleBo, SpaceEventIntegrationRulePageReq> {


    @Autowired
    private SpaceCallCountService spaceCallCountService;

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
        String targetFileName = "主机攻击防御规则" + CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();

        FileUtil.setResponse(new File(filePath), true, response, targetFileName);

        // 返回导出成功的信息
        return new BaseRsp<>(Boolean.TRUE);
    }

    @Override
    public boolean checkNameDuplication(SpaceEventIntegrationRule entity) {
        return true;
    }

    /**
     * 检查值 是否合法
     *
     * @param entity 实体对象
     */
    @Override
    public void checkValue(SpaceEventIntegrationRule entity){
        String[] fields = {
                entity.getTool(),
                entity.getType(),
                entity.getDescription(),
        };
        for (String field : fields) {
            if (!org.apache.commons.lang3.StringUtils.isEmpty(field)) {
                CrCommonValidator.validateNameLen(field, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
            }
        }
    }

    @Override
    public void detailAddCallTypeNum(){
        spaceCallCountService.addCount(CallType.RULE);
    }
}
