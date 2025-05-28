/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceSourceAnalyzeRuleMapper`，用于操作数据库中与 `SpaceSourceAnalyzeRule` 实体类相关的数据。以下是代码的简要分析： 
 * 1. **包声明**： 
 * - 包名为 `com.cyberpeace.space.dao.mapper`，表明该接口位于项目中的 `mapper` 层，负责数据访问逻辑。 
 * 2. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并将其注册为一个 Bean。 
 * - `@Repository`：表明这是一个 Spring 容器管理的组件，通常用于持久化层。 
 * 3. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceSourceAnalyzeRule>`，`BaseMapper` 是 MyBatis-Plus 提供的一个通用 CRUD 操作接口，提供了常用的增删改查方法（如 `selectById`, `insert`, `updateById`, `deleteById` 等），无需手动编写 SQL。 
 * 4. **功能描述**： 
 * - 该接口的主要作用是提供对 `SpaceSourceAnalyzeRule` 实体类相关数据的操作支持。 
 * 5. **文档注释**： 
 * - 注释清晰地描述了接口的功能，即“空间源分析规则Mapper”，并标注了作者、版本、日期等信息。 
 * 总结：这是一个基于 MyBatis 和 MyBatis-Plus 的通用 Mapper 接口，通过继承 `BaseMapper` 提供了对 `SpaceSourceAnalyzeRule` 数据表的通用操作，减少了手动编写 SQL 的工作量。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;

/**
 * 空间源分析统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceSourceAnalyzeRuleMapper extends BaseMapper<SpaceSourceAnalyzeRule>

{}