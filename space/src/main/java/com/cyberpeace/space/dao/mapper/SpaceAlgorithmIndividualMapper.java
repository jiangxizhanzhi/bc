/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceAlgorithmIndividualMapper`，用于操作数据库中与 `SpaceAlgorithmIndividual` 实体类相关的数据。以下是详细分析： 
 * ### 主要功能 
 * - **接口定义**：`SpaceAlgorithmIndividualMapper` 是一个继承自 `BaseMapper<SpaceAlgorithmIndividual>` 的接口。 
 * - **功能**：通过继承 `BaseMapper`，该接口已经提供了基本的 CRUD 操作（如增删改查）方法，无需手动编写 SQL。 
 * ### 关键注解 
 * 1. **`@Mapper`**： 
 * - 标识这是一个 MyBatis 的 Mapper 接口。 
 * - Spring 容器会自动扫描并注册该接口为 Mapper Bean。 
 * 2. **`@Repository`**： 
 * - 表明这是一个数据访问层（DAO）组件。 
 * - Spring 将其作为 Bean 注册到应用上下文中。 
 * ### 类结构 
 * - **父类继承**：`SpaceAlgorithmIndividualMapper` 继承了 `BaseMapper<SpaceAlgorithmIndividual>`，这是 MyBatis-Plus 提供的基础接口，包含常见的数据库操作方法。 
 * - **泛型参数**：`SpaceAlgorithmIndividual` 是实体类，表示该 Mapper 操作的数据表对应的实体对象。 
 * ### 注释说明 
 * - **类注释**：明确说明该接口的作用是操作“空间算法个人”的数据。 
 * - **作者与版本信息**：记录了代码的作者（SN）以及版本号（1.0.0），便于维护和追踪。 
 * - **引用关系**：提到依赖于 `BaseMapper` 和相关框架，方便开发者了解技术栈。 
 * ### 总结 
 * 这段代码是一个典型的基于 MyBatis-Plus 的数据访问层接口，通过继承 `BaseMapper` 提供了默认的 CRUD 方法，并通过注解（`@Mapper` 和 `@Repository`）与 Spring 集成。它简化了数据库操作，提高了开发效率。 
**/

package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间算法个人Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceAlgorithmIndividualMapper extends BaseMapper<SpaceAlgorithmIndividual> {
}
    