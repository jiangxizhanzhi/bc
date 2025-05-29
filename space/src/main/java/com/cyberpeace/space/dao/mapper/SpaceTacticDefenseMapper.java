package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 空间战法防御Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceTacticDefenseMapper` 的接口，它继承了 `BaseMapper<SpaceTacticDefense>`，用于操作与 `SpaceTacticDefense` 实体类相关的数据库操作。以下是代码的简要分析： 
 * 1. **包名**：`com.cyberpeace.space.dao.mapper` 表示这个接口位于 `space` 模块下的 `dao.mapper` 包中。 
 * 2. **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：将该接口标识为一个 Spring 的数据访问组件（repository），便于 Spring 容器管理。 
 * 3. **继承关系**：`SpaceTacticDefenseMapper` 继承自 `BaseMapper<SpaceTacticDefense>`，这意味着它自动获得了 MyBatis-Plus 提供的基本 CRUD 操作功能。 
 * 4. **方法定义**：除了继承的方法外，还定义了一个自定义方法 `idsByCategoryids(List<String> ids)`，用于根据一组类别 ID 查询对应的 ID 列表。 
 * 5. **文档说明**：通过 JavaDoc 注释描述了该接口的功能、作者、版本、日期以及依赖的基础类 `BaseMapper`。 
 * 总体而言，这段代码是一个典型的基于 MyBatis 和 MyBatis-Plus 的数据访问层接口，提供了基础的 CRUD 功能以及一个自定义查询方法。 
**/
@Mapper
@Repository
public interface SpaceTacticDefenseMapper extends BaseMapper<SpaceTacticDefense> {
    List<String> idsByCategoryids(List<String> ids);
}

