/**
 * 这段代码定义了一个名为 `EvalIndexRefMapper` 的接口，该接口继承自 `BaseMapper<EvalIndexRef>`。这个接口用于与数据库进行交互，特别是针对 `EvalIndexRef` 实体对象的操作。以下是对代码的详细分析： 
 * ### 包声明和导入 
 * - **包声明**：代码位于 `com.cyberpeace.cr.evaluation.dao.mapper` 包中。 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`：MyBatis Plus 提供的基类映射器，简化了基本的 CRUD 操作。 
 * - `com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp`：响应模型，用于封装查询结果。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef`：实体类，表示数据库中的 `EvalIndexRef` 表。 
 * - `org.apache.ibatis.annotations.Mapper`：MyBatis 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - `org.apache.ibatis.annotations.Param`：MyBatis 注解，用于指定方法参数的别名。 
 * - `org.springframework.stereotype.Repository`：Spring 注解，将这个接口标记为一个存储库。 
 * ### 类声明 
 * - **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个 Spring 的数据访问组件（DAO）。 
 * - **继承**：`EvalIndexRefMapper` 继承自 `BaseMapper<EvalIndexRef>`，这意味着它自动获得了许多基础的 CRUD 方法。 
 * ### 方法声明 
 * - **方法**：`selectResultIndexes` 
 * - **描述**：查询结果指标。 
 * - **参数**： 
 * - `@Param("record_id") String recordId`：记录 ID。 
 * - `@Param("level") Integer level`：水平。 
 * - **返回值**：`List<EvalRecordResultIndexRsp>`：包含查询结果的列表。 
 * ### 总结 
 * - 这个接口主要负责从数据库中查询与 `EvalIndexRef` 实体相关的数据，并将其转换为 `EvalRecordResultIndexRsp` 对象列表。 
 * - 它通过继承 `BaseMapper<EvalIndexRef>` 来获得基本的 CRUD 功能，并通过自定义方法 `selectResultIndexes` 提供特定的查询功能。 
 * - 使用 MyBatis 和 Spring 的注解来确保该接口可以被正确地识别和管理。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估指数裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalIndexRefMapper extends BaseMapper<EvalIndexRef> {
    /**
     * 查询结果指标
     *
     * @param recordId 记录id
     * @param level    水平
     * @return {@code List<EvalRecordResultIndexRsp> }
     */
    List<EvalRecordResultIndexRsp> selectResultIndexes(@Param("record_id")String recordId, @Param("level") Integer level);
}
