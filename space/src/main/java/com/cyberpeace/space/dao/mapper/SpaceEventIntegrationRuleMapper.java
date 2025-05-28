/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口，用于操作 `SpaceEventIntegrationRule` 实体类。以下是代码的简要分析： 
 * 1. **包名与导入**： 
 * - 包名为 `com.cyberpeace.space.dao.mapper`，表明这是一个数据访问层（DAO）的 Mapper 文件。 
 * - 导入了必要的类，包括 MyBatis 的 `@Mapper` 注解、Spring 的 `@Repository` 注解以及 MyBatis-Plus 的 `BaseMapper`。 
 * 2. **注解**： 
 * - `@Mapper`：标记该接口为 MyBatis 的 Mapper，便于 MyBatis 自动扫描和注册。 
 * - `@Repository`：标记该接口为 Spring 的数据访问组件，便于 Spring 容器管理。 
 * 3. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceEventIntegrationRule>`，`BaseMapper` 提供了常见的 CRUD 操作方法，无需手动实现即可使用。 
 * 4. **功能描述**： 
 * - 该 Mapper 对应的实体类是 `SpaceEventIntegrationRule`，主要用于操作与“空间事件集成规则”相关的数据库表。 
 * 5. **作者与版本信息**： 
 * - 注释中提供了作者、版本号和日期等信息，便于维护和追溯。 
 * 总结：这是一个基于 MyBatis-Plus 的通用 Mapper 接口，通过继承 `BaseMapper` 提供了对 `SpaceEventIntegrationRule` 实体的操作能力，并通过 Spring 和 MyBatis 的注解进行管理和映射。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;

/**
 * 空间事件集成统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceEventIntegrationRuleMapper extends BaseMapper<SpaceEventIntegrationRule>

{}