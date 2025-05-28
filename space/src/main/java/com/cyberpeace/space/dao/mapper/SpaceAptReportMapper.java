/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceAptReportMapper`，用于操作数据库中与 `SpaceAptReport` 实体类相关的数据。以下是代码的简要分析： 
 * 1. **包名与导入**： 
 * - 包名为 `com.cyberpeace.space.dao.mapper`，表明该类属于项目的 DAO（Data Access Object）层。 
 * - 导入了必要的类，包括 MyBatis 的 `@Mapper` 注解、Spring 的 `@Repository` 注解，以及 MyBatis-Plus 提供的 `BaseMapper`。 
 * 2. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口，MyBatis-Spring 模块会自动扫描并注册这个接口为 Spring Bean。 
 * - `@Repository`：将该接口标记为 Spring 的数据访问组件，便于依赖注入和管理。 
 * 3. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceAptReport>`，`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，提供了常用的 CRUD 操作方法（如 `selectById`、`insert`、`updateById`、`deleteById` 等），无需手动编写 SQL。 
 * 4. **功能描述**： 
 * - 该接口主要用于操作 `SpaceAptReport` 实体类对应的数据表，提供增删改查等基本操作。 
 * - 注释明确指出这是用于“空间容易报告”的 Mapper，且由开发者 SN 编写，版本为 1.0.0。 
 * 5. **设计优点**： 
 * - 使用了 MyBatis-Plus 的 `BaseMapper`，减少了手动编写 SQL 的工作量，提高了开发效率。 
 * - 结合了 Spring 的注解（`@Mapper` 和 `@Repository`），便于在 Spring 应用中管理和使用。 
 * 总结：这是一段简洁高效的 MyBatis Mapper 接口代码，通过继承 `BaseMapper` 提供了常见的数据库操作能力，并通过注解实现了与 Spring 的集成。 
**/
package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAptReport;

/**
 * 空间容易报告Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceAptReportMapper extends BaseMapper<SpaceAptReport> {}
