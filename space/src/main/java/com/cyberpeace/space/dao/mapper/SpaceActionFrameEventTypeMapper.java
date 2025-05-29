package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间行动框架事件类型Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口，用于操作数据库中的 `SpaceActionFrameEventType` 实体类。以下是对代码的简要分析： 
 * 1. **包名与导入**： 
 * - 代码位于 `com.cyberpeace.space.dao.mapper` 包中。 
 * - 使用了 `BaseMapper` 和 `SpaceActionFrameEventType` 类，表明这是一个基于 MyBatis-Plus 的数据访问层。 
 * 2. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：将该接口标记为 Spring 的 Bean，便于依赖注入。 
 * 3. **接口继承**： 
 * - 继承自 `BaseMapper<SpaceActionFrameEventType>`，这意味着该接口自动获得了 MyBatis-Plus 提供的 CRUD 操作功能。 
 * 4. **功能描述**： 
 * - 该接口专门用于操作 `SpaceActionFrameEventType` 实体类的数据，提供增删改查等基础功能。 
 * 总结：这是一段标准的 MyBatis-Plus Mapper 接口代码，实现了对 `SpaceActionFrameEventType` 实体类的数据库操作。 
**/

@Mapper
@Repository
public interface SpaceActionFrameEventTypeMapper extends BaseMapper<SpaceActionFrameEventType> {
}
    
