/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceActionFrameReleaseMapper`，用于操作数据库中的 `SpaceActionFrameRelease` 实体类。以下是简要分析： 
 * 1. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：将该接口标记为 Spring 的 Bean，便于依赖注入。 
 * 2. **继承关系**： 
 * - 继承自 `BaseMapper<SpaceActionFrameRelease>`：`BaseMapper` 提供了常用的 CRUD 操作方法，无需手动编写 SQL。 
 * 3. **功能描述**： 
 * - 该接口主要用于操作 `SpaceActionFrameRelease` 表的数据，提供增删改查等基础功能。 
 * 4. **作者与版本信息**： 
 * - 注释中指明了作者（SN）和版本号（1.0.0），便于维护和追踪。 
 * 总结：这是一个基于 MyBatis 和 MyBatis-Plus 的数据访问层接口，简化了对 `SpaceActionFrameRelease` 数据表的操作。 
**/
package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;

/**
 * 空间行动框架下发Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceActionFrameReleaseMapper extends BaseMapper<SpaceActionFrameRelease> {

}
