/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceTrafficDetectRuleMapper`，用于操作数据库中与 `SpaceTrafficDetectRule` 实体类相关的数据。以下是代码的详细分析： 
 * ### 主要功能 
 * - **接口定义**：该接口继承了 `BaseMapper<SpaceTrafficDetectRule>`，利用 MyBatis-Plus 提供的基础 CRUD 操作能力。 
 * - **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表示这是一个 Spring 中的数据访问组件。 
 * ### 技术要点 
 * 1. **MyBatis-Plus 支持**： 
 * - 使用了 `BaseMapper` 提供的通用方法（如 `selectById`, `insert`, `updateById`, `deleteById` 等），无需手动编写 SQL。 
 * 2. **Spring 集成**： 
 * - `@Repository` 注解将该接口注册为 Spring 容器中的 Bean，便于依赖注入和管理。 
 * 3. **命名规范**： 
 * - 类名和方法命名符合 Java Bean 和 MyBatis 的命名规范，便于后续扩展和维护。 
 * ### 总结 
 * 这段代码是一个典型的 MyBatis-Plus Mapper 接口实现，通过继承 `BaseMapper` 提供了基本的增删改查功能，并结合 Spring 的注解实现了容器管理。适用于快速开发基于数据库的操作场景。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;

/**
 * 空间流量检测统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTrafficDetectRuleMapper extends BaseMapper<SpaceTrafficDetectRule>

{}