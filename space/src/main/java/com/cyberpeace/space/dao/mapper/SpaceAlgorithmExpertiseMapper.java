/**
 * 这段代码定义了一个名为 `SpaceAlgorithmExpertiseMapper` 的接口，用于操作数据库中的 `SpaceAlgorithmExpertise` 实体类。以下是代码的简要分析： 
 * 1. **包名与导入**： 
 * - 代码位于 `com.cyberpeace.space.dao.mapper` 包中。 
 * - 导入了 MyBatis-Plus 提供的 `BaseMapper` 和其他必要的类。 
 * 2. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，MyBatis-Plus 会自动为其实现方法。 
 * - `@Repository`：将该接口标记为 Spring 容器中的一个 Bean，便于依赖注入。 
 * 3. **继承关系**： 
 * - `SpaceAlgorithmExpertiseMapper` 继承自 `BaseMapper<SpaceAlgorithmExpertise>`，这意味着它可以直接使用 MyBatis-Plus 提供的 CRUD 操作，而无需手动编写 SQL。 
 * 4. **功能描述**： 
 * - 该接口主要用于操作 `SpaceAlgorithmExpertise` 实体类对应的数据表，提供增删改查等基础数据库操作。 
 * 总结：这是一个基于 MyBatis-Plus 的数据访问层接口，用于管理 `SpaceAlgorithmExpertise` 数据，具备自动化的 CRUD 功能。 
**/

package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmExpertise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间算法专门知识Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceAlgorithmExpertiseMapper extends BaseMapper<SpaceAlgorithmExpertise> {
}
    