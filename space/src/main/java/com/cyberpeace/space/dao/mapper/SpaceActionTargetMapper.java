/**
 * 这段代码定义了一个基于 MyBatis-Plus 的 `Mapper` 接口，用于操作数据库中与 `SpaceActionTarget` 实体类相关的数据。 
 * ### 分析： 
 * 1. **包名**：`com.cyberpeace.space.dao.mapper` 表明这是一个数据访问层（DAO）中的 `Mapper` 文件。 
 * 2. **继承关系**：`SpaceActionTargetMapper` 继承自 `BaseMapper<SpaceActionTarget>`。`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，提供了常见的 CRUD 操作方法，因此无需手动编写 SQL 即可实现基本的数据操作。 
 * 3. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，MyBatis-Plus 会自动扫描并注册该接口。 
 * - `@Repository`：表示这是一个数据访问组件，通常用于 Spring 容器管理。 
 * 4. **功能描述**：根据注释，该接口用于操作 `SpaceActionTarget` 对象，提供增删改查等基础功能。 
 * 5. **泛型参数**：`SpaceActionTarget` 是与数据库表对应的实体类，`BaseMapper` 会根据该实体类生成对应的 SQL。 
 * 总结：这是一个标准的 MyBatis-Plus Mapper 接口，通过继承 `BaseMapper` 提供了默认的 CRUD 功能，无需额外配置即可使用。 
**/
package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间行动目标Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceActionTargetMapper extends BaseMapper<SpaceActionTarget>{
}