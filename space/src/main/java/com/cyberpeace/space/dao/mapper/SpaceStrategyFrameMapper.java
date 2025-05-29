package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceStrategyFrame;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 空间策略框架Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个基于 MyBatis-Plus 的 Mapper 接口 `SpaceStrategyFrameMapper`，用于操作数据库中与 `SpaceStrategyFrame` 实体类相关的数据。以下是代码的简要分析： 
 * ### 1. **接口定义** 
 * - **继承关系**：`SpaceStrategyFrameMapper` 继承了 `BaseMapper<SpaceStrategyFrame>`，这是 MyBatis-Plus 提供的一个基础接口，包含了常见的 CRUD 操作方法（如 `selectById`, `insert`, `updateById`, `deleteById` 等），无需手动编写 SQL。 
 * - **自定义方法**：除了继承的方法外，还定义了一个自定义方法 `getAll()`，用于查询所有 `SpaceStrategyFrame` 数据。 
 * --- 
 * ### 2. **注解说明** 
 * - **`@Mapper`**：表明这是一个 MyBatis 的 Mapper 接口，用于与数据库交互。 
 * - **`@Repository`**：标注该接口为 Spring 的 Bean，方便在 Spring 容器中管理。 
 * --- 
 * ### 3. **功能描述** 
 * - **继承的功能**：通过继承 `BaseMapper`，可以使用默认的 CRUD 方法，无需额外配置。 
 * - **自定义方法**：`getAll()` 方法用于查询所有 `SpaceStrategyFrame` 数据，返回值类型是 `List<SpaceStrategyFrame>`。 
 * --- 
 * ### 4. **依赖的类** 
 * - **`SpaceStrategyFrame`**：实体类，表示数据库表的映射对象。 
 * - **`BaseMapper<T>`**：MyBatis-Plus 提供的基础接口，提供通用的增删改查功能。 
 * - **`List<SpaceStrategyFrame>`**：方法返回值类型，表示查询结果集。 
 * --- 
 * ### 总结 
 * 这段代码是一个典型的 MyBatis-Plus Mapper 接口，提供了基础的 CRUD 功能以及一个自定义查询方法。它通过继承 `BaseMapper` 和注解的方式简化了数据库操作，同时结合 Spring 容器管理，便于在项目中直接注入和使用。 
**/
@Mapper
@Repository
public interface SpaceStrategyFrameMapper extends BaseMapper<SpaceStrategyFrame> {
    List<SpaceStrategyFrame> getAll();
}

