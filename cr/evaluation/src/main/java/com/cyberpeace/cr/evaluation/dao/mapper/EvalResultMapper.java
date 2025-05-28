/**
 * 这段代码定义了一个名为 `EvalResultMapper` 的接口，它继承了 `BaseMapper<EvalResult>`。以下是对此代码的简要分析： 
 * 1. **包声明**： 
 * - 该类位于 `com.cyberpeace.cr.evaluation.dao.mapper` 包中。 
 * 2. **导入的库**： 
 * - 导入了 MyBatis-Plus 的 `BaseMapper` 接口。 
 * - 导入了自定义实体类 `EvalResult`。 
 * - 使用了 MyBatis 的 `@Mapper` 注解来标记这是一个 MyBatis 的 Mapper 接口。 
 * - 使用了 Spring 的 `@Repository` 注解来标记这是一个数据访问层的组件。 
 * 3. **接口定义**： 
 * - `EvalResultMapper` 接口继承了 `BaseMapper<EvalResult>`，这意味着它自动获得了 CRUD（创建、读取、更新、删除）操作的方法。 
 * - 该接口没有额外的方法声明，所有方法都是从 `BaseMapper` 继承来的。 
 * 4. **注释**： 
 * - 类上有 Javadoc 注释，描述了该接口的作用是用于“评估结果”的操作。 
 * - 作者和日期也被记录在注释中。 
 * 总结来说，这段代码定义了一个 MyBatis-Plus 的 Mapper 接口 `EvalResultMapper`，用于管理 `EvalResult` 实体类的数据操作。通过继承 `BaseMapper`，它提供了基本的 CRUD 操作，并且通过 Spring 的 `@Repository` 注解将其注册为一个数据访问组件。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 评估结果Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalResultMapper extends BaseMapper<EvalResult> {
}




