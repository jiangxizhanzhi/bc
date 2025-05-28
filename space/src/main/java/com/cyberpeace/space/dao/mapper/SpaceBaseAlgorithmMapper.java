/**
 * 这段代码定义了一个接口 `SpaceBaseAlgorithmMapper`，它是基于 MyBatis-Plus 框架的自定义 Mapper 接口。以下是代码的简要分析： 
 * 1. **包名与导入**： 
 * - 包名是 `com.cyberpeace.space.dao.mapper`，表明该类属于 `space` 模块下的 `mapper` 包。 
 * - 导入了必要的类，包括 MyBatis-Plus 的 `BaseMapper` 和 Spring 的注解 `@Repository`。 
 * 2. **注解说明**： 
 * - `@Mapper`：标记该接口为 MyBatis 的 Mapper，用于自动扫描和注册到 Spring 容器中。 
 * - `@Repository`：将该接口标识为一个 Spring 的数据访问组件，便于依赖注入。 
 * 3. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceBaseAlgorithm>`，这意味着它自动拥有了 MyBatis-Plus 提供的基本 CRUD 操作功能，无需手动编写 SQL。 
 * 4. **功能描述**： 
 * - 注释明确指出该接口用于操作 `SpaceBaseAlgorithm` 实体类，提供数据库相关的增删改查功能。 
 * 总结：这是一个基于 MyBatis-Plus 的通用 Mapper 接口，用于管理 `SpaceBaseAlgorithm` 数据实体，具有开箱即用的 CRUD 功能，并通过 Spring 容器进行管理。 
**/

package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间基础算法Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceBaseAlgorithmMapper extends BaseMapper<SpaceBaseAlgorithm>
    
{}