package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.service.context.frame.ActionStatisticsBo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 空间行动框架Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceActionFrameMapper`，用于操作数据库中的 `SpaceActionFrame` 实体类。以下是代码的简要分析： 
 * 1. **包结构**： 
 * - 代码位于 `com.cyberpeace.space.dao.mapper` 包中，表明这是一个数据访问层（DAO）的 Mapper 文件。 
 * 2. **继承关系**： 
 * - `SpaceActionFrameMapper` 继承自 `BaseMapper<SpaceActionFrame>`，这是 MyBatis-Plus 提供的一个通用 Mapper 接口，提供了常见的 CRUD 操作方法。 
 * 3. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：标注该接口为 Spring 容器中的一个 Bean，便于依赖注入。 
 * 4. **自定义方法**： 
 * - 定义了 `statistics()` 方法，返回一个 `List<ActionStatisticsBo>`，用于统计相关的业务逻辑。 
 * 5. **依赖类**： 
 * - `SpaceActionFrame`：实体类，与数据库表映射。 
 * - `ActionStatisticsBo`：业务对象，用于封装统计结果。 
 * 总结：这是一个基于 MyBatis-Plus 的 Mapper 接口，提供基本的 CRUD 操作以及自定义的统计功能。 
**/
@Mapper
@Repository
public interface SpaceActionFrameMapper extends BaseMapper<SpaceActionFrame>{

	List<ActionStatisticsBo> statistics();
}
