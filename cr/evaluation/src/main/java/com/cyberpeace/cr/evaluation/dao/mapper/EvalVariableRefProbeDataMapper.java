/**
 * 这段代码定义了一个名为 `EvalVariableRefProbeDataMapper` 的接口，该接口继承了 `BaseMapper<EvalVariableRefProbeData>`。这个接口使用了 MyBatis-Plus 框架来简化数据库操作。以下是对代码的简要分析： 
 * ### 接口定义 
 * ```java 
 * public interface EvalVariableRefProbeDataMapper extends BaseMapper<EvalVariableRefProbeData> { 
 * ``` 
 * - **继承自 `BaseMapper`**：`BaseMapper` 提供了一些基本的 CRUD 操作方法，如 `selectById`, `insert`, `updateById`, `deleteById` 等。 
 * ### 方法定义 
 * #### 1. `selectVariableProbeDataResult` 
 * ```java 
 * List<EvalVariableRefProbeDataContext> selectVariableProbeDataResult(@Param("variable_id") String variableId); 
 * ``` 
 * - **功能**：根据给定的 `variableId` 查询相关的变量探针数据结果。 
 * - **返回值**：一个包含 `EvalVariableRefProbeDataContext` 对象的列表。 
 * - **参数**： 
 * - `@Param("variable_id") String variableId`：变量的 ID。 
 * #### 2. `selectVariableProbeData` 
 * ```java 
 * Page<EvalVariableDataPageRsp> selectVariableProbeData(@Param("page") Page page, @Param("variable_id") String variableId); 
 * ``` 
 * - **功能**：根据给定的 `variableId` 和分页信息查询变量探针数据。 
 * - **返回值**：一个分页对象 `Page<EvalVariableDataPageRsp>`，其中包含了分页的数据。 
 * - **参数**： 
 * - `@Param("page") Page page`：分页对象，用于控制分页信息（如当前页码、每页条数等）。 
 * - `@Param("variable_id") String variableId`：变量的 ID。 
 * ### 总结 
 * - 这个接口主要用于处理与评估变量探针数据相关的数据库操作。 
 * - 它提供了两个主要的方法，一个用于查询所有相关数据，另一个用于分页查询数据。 
 * - 使用了 MyBatis-Plus 提供的 `BaseMapper` 来简化基本的 CRUD 操作，并通过自定义方法扩展了功能。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估变量裁判探针数据Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalVariableRefProbeDataMapper extends BaseMapper<EvalVariableRefProbeData> {
    /**
     * 查询变量探针数据结果
     *
     * @param variableId 变量id
     * @return {@code List<EvalVariableRefProbeDataContext> }
     */
    List<EvalVariableRefProbeDataContext> selectVariableProbeDataResult(@Param("variable_id") String variableId);

    /**
     * 查询变量探针数据
     *
     * @param page       分页
     * @param variableId 变量id
     * @return {@code Page<EvalVariableDataPageRsp> }
     */
    Page<EvalVariableDataPageRsp> selectVariableProbeData(@Param("page") Page page, @Param("variable_id") String variableId);
}
