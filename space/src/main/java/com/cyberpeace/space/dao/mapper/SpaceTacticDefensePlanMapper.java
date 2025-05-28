/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceTacticDefensePlanMapper`，用于操作数据库中的 `SpaceTacticDefensePlan` 实体类。以下是详细分析： 
 * --- 
 * ### **1. 包结构** 
 * - 包名：`com.cyberpeace.space.dao.mapper` 
 * - 表明这是一个与数据访问相关的包，专门用于存放数据映射器（Mapper）。 
 * --- 
 * ### **2. 导入的依赖** 
 * - 导入了以下类： 
 * - `BaseMapper`：MyBatis-Plus 提供的一个基础接口，包含了常用的 CRUD 操作方法。 
 * - `SpaceTacticDefensePlan`：实体类，表示与数据库表对应的 Java 对象。 
 * - `Mapper` 和 `Repository` 注解： 
 * - `@Mapper`：标注这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：Spring 中的标准注解，表明这是一个数据访问层组件。 
 * --- 
 * ### **3. 类定义** 
 * - 类名：`SpaceTacticDefensePlanMapper` 
 * - 继承自 `BaseMapper<SpaceTacticDefensePlan>`： 
 * - 自动提供了基本的增删改查功能（如 `selectById`, `insert`, `updateById`, `deleteById` 等）。 
 * - 不需要手动编写 SQL，极大简化了开发工作量。 
 * - 使用了 `@Mapper` 和 `@Repository` 注解： 
 * - `@Mapper`：将该接口注册为 MyBatis 的 Mapper。 
 * - `@Repository`：将其标识为 Spring 容器中的一个 Bean。 
 * --- 
 * ### **4. 文档注释** 
 * - 注释清晰地描述了该类的作用： 
 * - 功能：空间战法防御计划的数据库操作。 
 * - 作者、版本、日期等信息便于团队协作和维护。 
 * - 引用了 `BaseMapper`，说明该类基于 MyBatis-Plus 开发。 
 * --- 
 * ### **5. 总结** 
 * - **功能**：这是一个 MyBatis-Plus 的 Mapper 接口，用于操作 `SpaceTacticDefensePlan` 实体类对应的数据表。 
 * - **优点**：利用了 MyBatis-Plus 的基础实现，无需手动编写复杂的 SQL，提升了开发效率。 
 * - **扩展性**：如果需要额外的自定义查询，可以通过重写或添加方法实现。 
 * 如果有进一步的需求或问题，可以在此基础上扩展功能！ 
**/

package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间战法防御计划Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTacticDefensePlanMapper extends BaseMapper<SpaceTacticDefensePlan> {
}
    