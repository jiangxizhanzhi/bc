package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.space.dao.entity.SpaceTacticAttackCategory;
import com.cyberpeace.space.service.context.tactic.TacticAttackCategoryBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 空间战法攻击类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTacticAttackCategoryMapper` 的 MyBatis Mapper 接口，用于操作数据库中的 `SpaceTacticAttackCategory` 实体类。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - `SpaceTacticAttackCategoryMapper` 继承了 `BaseMapper<SpaceTacticAttackCategory>`，这意味着它已经具备了基本的 CRUD 操作功能。 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * 2. **方法定义**： 
 * - 定义了一个自定义方法 `page`，该方法接收两个参数：`Page` 对象和 `String` 类型的 `pattern`。 
 * - 返回值是一个分页对象 `IPage<TacticAttackCategoryBo>`，其中 `TacticAttackCategoryBo` 是业务对象。 
 * 3. **注解与注释**： 
 * - 使用了 `@Repository` 注解将该接口标记为 Spring 的 Bean，便于依赖注入。 
 * - 接口上有详细的 Javadoc 注释，说明了接口的功能和用途。 
 * 4. **技术栈**： 
 * - 使用了 `MyBatis-Plus` 提供的 `BaseMapper` 和分页插件 `IPage`，简化了分页查询的实现。 
 * 总结：这个接口主要用于处理 `SpaceTacticAttackCategory` 数据表的分页查询操作，并返回业务对象 `TacticAttackCategoryBo` 的分页结果。 
**/
@Mapper
@Repository
public interface SpaceTacticAttackCategoryMapper extends BaseMapper<SpaceTacticAttackCategory> {
    IPage<TacticAttackCategoryBo> page(@Param("page") Page page, @Param("pattern") String pattern);
}

