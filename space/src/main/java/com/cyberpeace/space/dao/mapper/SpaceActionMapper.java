/**
 * 这段代码定义了一个名为 `SpaceActionMapper` 的接口，用于操作数据库中与 `SpaceAction` 实体相关的数据。以下是代码的简要分析： 
 * 1. **包名**：代码位于 `com.cyberpeace.space.dao.mapper` 包下，表明这是一个数据访问层（DAO）中的 mapper 文件。 
 * 2. **继承关系**：`SpaceActionMapper` 继承了 `BaseMapper<SpaceAction>`，这是 MyBatis-Plus 提供的一个基础接口，通常用于简化 CRUD 操作，无需手动编写 SQL。 
 * 3. **注解**： 
 * - `@Mapper`：标明该接口是一个 MyBatis 的 Mapper，MyBatis-Plus 会自动扫描并注册。 
 * - `@Repository`：标明这是一个 Spring 的数据访问组件，便于 Spring 容器管理。 
 * 4. **功能**：通过继承 `BaseMapper`，`SpaceActionMapper` 可以直接使用 MyBatis-Plus 提供的默认 CRUD 方法，如 `selectById`, `insert`, `updateById`, `deleteById` 等，而无需额外编写 XML 或方法实现。 
 * 总结：这是一个基于 MyBatis-Plus 的数据访问接口，专门用于操作 `SpaceAction` 实体类的数据，具有自动生成的 CRUD 功能。 
**/
package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间行动Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceActionMapper extends BaseMapper<SpaceAction>{
}