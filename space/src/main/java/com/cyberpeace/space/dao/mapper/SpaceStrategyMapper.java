/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceStrategyMapper`，用于操作 `SpaceStrategy` 实体类相关的数据库操作。以下是代码的简要分析： 
 * 1. **注解与继承**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - 继承自 `BaseMapper<SpaceStrategy>`，这意味着该接口已经具备了基本的 CRUD 操作功能。 
 * 2. **接口方法**： 
 * - 定义了一个自定义查询方法 `selectIdAndNameByFrame`，用于根据 `frameId` 和 `name` 查询 `SpaceStrategySimple` 对象列表。 
 * - 方法参数通过 `@Param` 注解明确指定，便于在 SQL 映射文件或动态 SQL 中使用。 
 * 3. **泛型与依赖**： 
 * - 泛型 `<SpaceStrategy>` 表明该 Mapper 主要操作 `SpaceStrategy` 实体类。 
 * - 返回值类型为 `List<SpaceStrategySimple>`，表示查询结果将被映射为 `SpaceStrategySimple` 对象列表。 
 * 4. **注释与文档**： 
 * - 类上有详细的 Javadoc 注释，说明了接口的作用、作者、版本、依赖等信息。 
 * 总结：`SpaceStrategyMapper` 是一个基于 MyBatis 的数据访问层接口，提供了基础 CRUD 功能以及自定义查询方法，用于处理与 `SpaceStrategy` 相关的数据操作。 
**/
package com.cyberpeace.space.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.context.strategy.SpaceStrategySimple;

/**
 * 空间策略Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceStrategyMapper extends BaseMapper<SpaceStrategy> {

	List<SpaceStrategySimple> selectIdAndNameByFrame(@Param("frameId") String frameId,
			@Param("name") String name);
}
