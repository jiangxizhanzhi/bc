/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口，用于操作数据库中的 `SpaceLoadedFileThreatRule` 实体类。以下是简要分析： 
 * 1. **包与导入**： 
 * - 包名：`com.cyberpeace.space.dao.mapper` 表示该类属于 `space` 模块下的 `mapper` 子包。 
 * - 导入了必要的注解和类：`@Mapper`、`@Repository`、`BaseMapper` 和实体类 `SpaceLoadedFileThreatRule`。 
 * 2. **注解**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并注册它。 
 * - `@Repository`：表示这是一个 Spring 中的持久层组件，便于 Spring 容器管理。 
 * 3. **接口继承**： 
 * - 继承自 `BaseMapper<SpaceLoadedFileThreatRule>`：`BaseMapper` 提供了基本的 CRUD 操作，无需手动编写 SQL。 
 * 4. **功能描述**： 
 * - 该接口用于操作 `SpaceLoadedFileThreatRule` 实体类对应的数据表，提供增删改查等基础功能。 
 * 总结：这是一个基于 MyBatis 和 MyBatis-Plus 的数据访问接口，用于处理 `SpaceLoadedFileThreatRule` 实体类的数据库操作。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;

/**
 * 空间加载文件威胁统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceLoadedFileThreatRuleMapper extends BaseMapper<SpaceLoadedFileThreatRule>

{}