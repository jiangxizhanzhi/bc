/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceTacticDefenseSystemMapper`，用于操作数据库中与 `SpaceTacticDefenseSystem` 实体类相关的数据。以下是代码的简要分析： 
 * 1. **包名**：代码位于 `com.cyberpeace.space.dao.mapper` 包下，表明这是一个与数据访问层（DAO）相关的接口。 
 * 2. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceTacticDefenseSystem>`，这是 MyBatis-Plus 提供的一个基础接口，提供了常用的 CRUD 操作方法。 
 * - 使用 MyBatis-Plus 可以减少手动编写 SQL 的工作量，提高开发效率。 
 * 3. **注解**： 
 * - `@Mapper`：标注该接口为 MyBatis 的 Mapper，MyBatis-Plus 会自动扫描并注册这个接口。 
 * - `@Repository`：将该接口标记为 Spring 的 Bean，便于在 Spring 容器中管理。 
 * 4. **功能描述**： 
 * - 接口名称 `SpaceTacticDefenseSystemMapper` 表明它是一个针对 `SpaceTacticDefenseSystem` 实体类的数据库操作接口。 
 * - 文档注释明确说明了该接口的作用是处理“空间战法防御系统”的相关数据。 
 * 5. **技术栈**： 
 * - 使用了 MyBatis 和 MyBatis-Plus 进行数据库操作。 
 * - 结合 Spring 的依赖注入机制，通过 `@Repository` 注解将其纳入 Spring 容器管理。 
 * 总结：这是一段典型的基于 MyBatis-Plus 的数据访问层代码，提供了对 `SpaceTacticDefenseSystem` 实体类的增删改查功能，并通过注解与 Spring 集成，方便在项目中使用。 
**/

package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间战法防御系统Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTacticDefenseSystemMapper extends BaseMapper<SpaceTacticDefenseSystem> {
}
    