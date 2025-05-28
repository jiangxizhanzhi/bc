/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceThreatImageRuleMapper`，用于操作数据库中的 `SpaceThreatImageRule` 实体类。以下是代码的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.space.dao.mapper` 包中，表明这是一个数据访问层（DAO）的组件。 
 * 2. **注解说明**： 
 * - `@Mapper`：标记该接口为 MyBatis 的 Mapper，MyBatis-Spring 会自动扫描并注册这个接口。 
 * - `@Repository`：表明这是一个 Spring 的数据访问组件，便于 Spring 容器管理。 
 * 3. **继承关系**：`SpaceThreatImageRuleMapper` 继承了 `BaseMapper<SpaceThreatImageRule>`，这是 MyBatis-Plus 提供的一个基础接口，提供了常用的 CRUD 操作方法。 
 * 4. **功能描述**：该接口主要用于操作与 `SpaceThreatImageRule` 实体相关的数据库表，例如增删改查等。 
 * 总结：这是一个基于 MyBatis 和 MyBatis-Plus 的数据访问层接口，专门用于处理 `SpaceThreatImageRule` 实体的数据库操作。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;

/**
 * 空间威胁图像统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceThreatImageRuleMapper extends BaseMapper<SpaceThreatImageRule>

{}