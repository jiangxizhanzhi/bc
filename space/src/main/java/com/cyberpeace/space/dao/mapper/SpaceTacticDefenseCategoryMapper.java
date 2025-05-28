/**
 * 这段代码定义了一个基于 MyBatis-Plus 的 `Mapper` 接口，用于操作数据库中的 `SpaceTacticDefenseCategory` 实体类及其相关的业务逻辑。以下是简要分析： 
 * 1. **接口定义**： 
 * - `SpaceTacticDefenseCategoryMapper` 继承了 `BaseMapper<SpaceTacticDefenseCategory>`，这意味着它已经具备了基本的 CRUD 操作功能。 
 * - 使用了 `@Mapper` 和 `@Repository` 注解，表明这是一个 MyBatis 的 Mapper 接口，并且是一个 Spring 的数据访问组件。 
 * 2. **方法说明**： 
 * - `page(Page page, String pattern)`：根据分页参数和模式字符串查询并返回分页结果，返回值是 `IPage<TacticDefenseCategoryBo>`，表示分页后的业务对象列表。 
 * - `listAllInOrder()`：查询所有记录并按某种顺序返回，返回值是 `List<TacticDefenseCategoryBo>`，表示无分页的业务对象列表。 
 * 3. **依赖与上下文**： 
 * - 依赖了 `com.baomidou.mybatisplus` 提供的分页插件和基础 Mapper 功能。 
 * - 使用了自定义的业务对象 `TacticDefenseCategoryBo` 来封装查询结果，而不是直接返回实体类。 
 * 总结：该接口主要用于空间战法防御类别的数据库操作，提供了分页查询和全量有序查询的功能，适用于需要灵活查询和业务对象封装的场景。 
**/
package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.space.service.context.tactic.TacticDefenseCategoryBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseCategory;

import java.util.List;

/**
 * 空间战法防御类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTacticDefenseCategoryMapper extends BaseMapper<SpaceTacticDefenseCategory> {

    IPage<TacticDefenseCategoryBo> page(@Param("page") Page page, @Param("pattern") String pattern);

    List<TacticDefenseCategoryBo> listAllInOrder();
}
