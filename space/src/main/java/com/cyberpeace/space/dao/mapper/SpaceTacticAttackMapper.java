package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 空间战法攻击Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceTacticAttackMapper`，用于操作数据库中与 `SpaceTacticAttack` 实体相关的数据。以下是代码的简要分析： 
 * 1. **包名**： 
 * - `com.cyberpeace.space.dao.mapper`：表明该类位于项目的 `dao.mapper` 包下，通常用于存放数据访问层的接口。 
 * 2. **继承关系**： 
 * - `extends BaseMapper<SpaceTacticAttack>`：表明该接口继承了 MyBatis-Plus 提供的 `BaseMapper` 类，自动实现了基本的 CRUD 操作，无需手动编写 SQL。 
 * 3. **注解**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：将该接口标记为 Spring 容器中的一个 Bean，便于依赖注入。 
 * 4. **自定义方法**： 
 * - `List<String> idsByCategoryids(@Param("ids") List<String> ids)`：定义了一个自定义查询方法，根据传入的 `ids` 列表参数，返回对应的字符串列表。此方法需要在 XML 文件或注解中实现具体的 SQL 查询逻辑。 
 * 总结：这是一个基于 MyBatis-Plus 的数据访问层接口，提供了基础的 CRUD 功能以及一个自定义查询方法，用于处理 `SpaceTacticAttack` 实体的相关业务需求。 
**/
@Mapper
@Repository
public interface SpaceTacticAttackMapper extends BaseMapper<SpaceTacticAttack> {

     List<String> idsByCategoryids(@Param("ids") List<String> ids) ;
}

