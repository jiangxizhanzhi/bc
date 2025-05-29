package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefProbeDataMapper;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefProbeDataService;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评估变量裁判探针数据Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRefProbeDataServiceImpl` 的服务类，用于处理与评估变量相关的探针数据。以下是对该代码的简要分析： 
 * ### 类结构 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**: `EvalVariableRefProbeDataServiceImpl` 
 * - **继承**: `ServiceImpl<EvalVariableRefProbeDataMapper, EvalVariableRefProbeData>`，这是 MyBatis-Plus 提供的一个基础实现类。 
 * - **注解**: 
 * - `@Slf4j`: 使用 Lombok 自动生成日志对象。 
 * - `@Service`: Spring 框架中的注解，表示这是一个 Spring 服务类。 
 * ### 方法 
 * 1. **variableProbeDataResult** 
 * - **描述**: 获取指定变量ID的探针数据结果。 
 * - **参数**: 
 * - `String variableId`: 变量ID。 
 * - **返回值**: 
 * - `List<EvalVariableRefProbeDataContext>`: 探针数据结果列表。 
 * - **实现**: 调用 `getBaseMapper().selectVariableProbeDataResult(variableId)` 方法获取数据。 
 * 2. **variableProbeData** 
 * - **描述**: 获取分页后的探针数据。 
 * - **参数**: 
 * - `Page page`: 分页信息。 
 * - `String variableId`: 变量ID。 
 * - **返回值**: 
 * - `ListRsp<EvalVariableDataPageRsp>`: 包含分页数据的响应对象。 
 * - **实现**: 使用 `PageUtil.getListResult` 方法处理分页逻辑，并调用 `getBaseMapper().selectVariableProbeData(page, variableId)` 方法获取数据。 
 * ### 主要依赖 
 * - `EvalVariableRefProbeDataMapper`: 数据访问层接口，用于与数据库交互。 
 * - `EvalVariableRefProbeDataContext`: 探针数据上下文对象。 
 * - `EvalVariableDataPageRsp`: 分页数据响应对象。 
 * - `PageUtil`: 分页工具类，用于处理分页逻辑。 
 * ### 总结 
 * 这个类主要实现了两个核心功能：获取指定变量的探针数据结果和分页获取探针数据。通过继承 `ServiceImpl` 并使用 MyBatis-Plus 提供的功能，简化了数据访问层的操作。同时，通过 `PageUtil` 工具类处理分页逻辑，使得代码更加简洁易读。 
**/
@Slf4j
@Service
public class EvalVariableRefProbeDataServiceImpl extends ServiceImpl<EvalVariableRefProbeDataMapper, EvalVariableRefProbeData> implements EvalVariableRefProbeDataService {

    /**
     * 可变探测数据结果
     *
     * @param variableId 变量id
     * @return {@code List<EvalVariableRefProbeDataContext> }
     */
    @Override
    public List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId) {
        return this.getBaseMapper().selectVariableProbeDataResult(variableId);
    }

    /**
     * 可变探针数据
     *
     * @param page       分页
     * @param variableId 变量id
     * @return {@code ListRsp<EvalVariableDataPageRsp> }
     */
    @Override
    public ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId) {
        return PageUtil.getListResult(this.getBaseMapper().selectVariableProbeData(page, variableId));
    }
}

