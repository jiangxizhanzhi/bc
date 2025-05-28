/**
 * 这段代码定义了一个名为 `EvalRecordMapper` 的接口，它继承了 `BaseMapper<EvalRecord>`。这个接口用于数据库操作，并且使用了 MyBatis-Plus 框架来简化数据访问层的开发。以下是详细的分析： 
 * ### 1. 包声明和导入 
 * ```java 
 * package com.cyberpeace.cr.evaluation.dao.mapper; 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.baomidou.mybatisplus.extension.plugins.pagination.Page; 
 * import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordListRsp; 
 * import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import org.springframework.stereotype.Repository; 
 * ``` 
 * - 这段代码导入了必要的类和接口，包括 MyBatis-Plus 的 `BaseMapper` 和分页插件 `Page`，以及自定义的响应类 `EvalRecordListRsp` 和实体类 `EvalRecord`。 
 * ### 2. 接口声明 
 * ```java 
 * @Mapper 
 * @Repository 
 * public interface EvalRecordMapper extends BaseMapper<EvalRecord> { 
 * ``` 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 类。 
 * - `@Repository` 注解表明这是一个 Spring 的数据访问组件。 
 * - `EvalRecordMapper` 继承了 `BaseMapper<EvalRecord>`，这提供了基本的 CRUD（创建、读取、更新、删除）操作方法。 
 * ### 3. 方法定义 
 * ```java 
 * /** 
 * * 分页列表 
 * * 
 * * @param page    分页 
 * * @param task_id 任务id 
 * * @param status  状态 
 * * @param pattern 模式 
 * * @return {@code Page<EvalRecordListRsp> } 
 * *
 * Page<EvalRecordListRsp> pageList(@Param("page") Page page, 
 * @Param("task_id") String task_id, 
 * @Param("status") Integer status, 
 * @Param("pattern") String pattern); 
 * } 
 * ``` 
 * - `pageList` 方法用于根据分页参数、任务ID、状态和模式获取分页列表。 
 * - `@Param` 注解用于指定方法参数在 SQL 语句中的名称。 
 * - 返回值类型是 `Page<EvalRecordListRsp>`，表示一个分页结果对象，其中包含 `EvalRecordListRsp` 对象的列表。 
 * ### 总结 
 * 这段代码定义了一个用于管理评估记录的 Mapper 接口。它继承了 MyBatis-Plus 提供的基础映射器，并添加了一个自定义的方法 `pageList` 用于分页查询评估记录。通过这种方式，可以方便地进行数据库操作，并且代码结构清晰，易于维护。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 评估记录Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalRecordMapper extends BaseMapper<EvalRecord> {
    /**
     * 分页列表
     *
     * @param page    分页
     * @param task_id 任务id
     * @param status  状态
     * @param pattern 模式
     * @return {@code Page<EvalRecordListRsp> }
     */
    Page<EvalRecordListRsp> pageList(@Param("page") Page page,
                                     @Param("task_id") String task_id,
                                     @Param("status") Integer status,
                                     @Param("pattern") String pattern);
}
