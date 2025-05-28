/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceTrafficExtractionRuleMapper`，用于操作数据库中的 `SpaceTrafficExtractionRule` 实体类。以下是代码的详细分析： 
 * ### 1. **包结构** 
 * - 包名：`com.cyberpeace.space.dao.mapper` 
 * - 表明该文件属于 `space` 模块下的 `dao.mapper` 包，通常用于存放与数据库交互的 Mapper 接口。 
 * ### 2. **注解说明** 
 * - `@Mapper`: 
 * - 表明这是一个 MyBatis 的 Mapper 接口，MyBatis-Spring Boot 会自动扫描带有此注解的接口，并将其注册为 Spring 容器中的 Bean。 
 * - `@Repository`: 
 * - 标识这是一个数据访问层组件，将其注册为 Spring 容器中的 Bean，方便依赖注入。 
 * ### 3. **继承关系** 
 * - `extends BaseMapper<SpaceTrafficExtractionRule>`: 
 * - 继承自 MyBatis-Plus 提供的 `BaseMapper`，`BaseMapper` 已经实现了常见的 CRUD 操作（如 `selectById`, `insert`, `updateById`, `deleteById` 等），无需手动编写 SQL。 
 * - 泛型参数 `SpaceTrafficExtractionRule` 表示该 Mapper 操作的实体类是 `SpaceTrafficExtractionRule`。 
 * ### 4. **接口功能** 
 * - 该接口专门用于操作 `SpaceTrafficExtractionRule` 数据表。 
 * - 默认支持以下功能： 
 * - 增删改查（CRUD）：通过继承 `BaseMapper`，无需额外实现方法即可完成基本操作。 
 * - 自定义查询可以通过 XML 文件或注解方式扩展。 
 * ### 5. **类文档** 
 * - 类注释清晰地描述了该接口的功能：**空间流量萃取规则的 Mapper**。 
 * - 注释中提到了作者、版本号、日期以及相关的依赖类（`BaseMapper`），便于维护和理解。 
 * ### 总结 
 * 该代码是一个典型的 MyBatis-Plus Mapper 接口，利用了框架的默认功能减少了代码量。通过继承 `BaseMapper` 和注解 `@Mapper` 和 `@Repository`，实现了与数据库的交互能力。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;

/**
 * 空间流量萃取统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTrafficExtractionRuleMapper extends BaseMapper<SpaceTrafficExtractionRule>

{}