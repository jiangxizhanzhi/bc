package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评估系统Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemMapper` 的接口，继承自 `BaseMapper<EvalSystem>`。`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，通常用于处理常见的 CRUD 操作。以下是代码的具体分析： 
 * ### 包和导入声明 
 * - `com.cyberpeace.cr.evaluation.dao.mapper`: 这个包包含了与数据访问对象（DAO）相关的映射器（Mapper）。 
 * - 导入了必要的类和接口，包括 `BaseMapper`, `IPage`, `Page`, `EvalSystem`, `EvalListQuery` 等。 
 * ### 类和接口定义 
 * - `@Mapper`: 这个注解表明该接口是一个 MyBatis 映射器，MyBatis 会自动为这个接口生成实现类。 
 * - `EvalSystemMapper` 接口继承了 `BaseMapper<EvalSystem>`，因此它已经具备了基本的 CRUD 操作方法。 
 * ### 方法定义 
 * 1. **`listEvalSystems(Page page, EvalListQuery listQuery)`** 
 * - **描述**: 获取分页后的评估系统列表。 
 * - **参数**: 
 * - `page`: 分页信息对象，用于指定分页参数。 
 * - `listQuery`: 查询条件对象，包含查询所需的各种参数。 
 * - **返回值**: `IPage<EvalSystem>`，表示分页后的评估系统列表。 
 * 2. **`existByName(String name, String resourceId)`** 
 * - **描述**: 检查是否存在具有特定名称的评估系统。 
 * - **参数**: 
 * - `name`: 评估系统的名称。 
 * - `resourceId`: 资源 ID，可能是用于唯一性检查的附加条件。 
 * - **返回值**: `boolean`，表示是否找到具有相同名称和资源 ID 的评估系统。 
 * ### 总结 
 * - `EvalSystemMapper` 主要提供了两个方法：一个用于分页查询评估系统列表，另一个用于检查具有特定名称的评估系统是否存在。 
 * - 通过继承 `BaseMapper<EvalSystem>`，该接口已经具备了基本的 CRUD 操作功能。 
 * - 使用 MyBatis-Plus 的 `Page` 和 `IPage` 进行分页查询，使得数据库操作更加方便和高效。 
 * 这段代码展示了如何使用 MyBatis-Plus 和自定义方法来处理数据访问需求，特别是对于分页查询和条件检查的需求。 
**/
@Mapper
public interface EvalSystemMapper extends BaseMapper<EvalSystem> {

    /**
     * 列出 EVAL Systems
     *
     * @param page      分页
     * @param listQuery 列表查询
     * @return {@code IPage<EvalSystem> }
     */
    IPage<EvalSystem> listEvalSystems(Page page, EvalListQuery listQuery);

    /**
     * 存在由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return boolean
     */
    boolean existByName(String name, String resourceId);
}





