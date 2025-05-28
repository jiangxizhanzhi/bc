/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceIocThreatMapper`，用于操作数据库中与 `SpaceIocThreat` 实体类相关的数据。以下是代码的简要分析： 
 * 1. **包名**：`com.cyberpeace.space.dao.mapper` 表明该类属于 `space` 模块下的 `dao.mapper` 包，通常用于存放数据访问层的接口。 
 * 2. **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并注册这个接口。 
 * - `@Repository`：表明这是一个 Spring 中的持久化层组件，便于 Spring 容器管理。 
 * 3. **继承关系**：`SpaceIocThreatMapper` 继承了 `BaseMapper<SpaceIocThreat>`，`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，提供了常见的增删改查方法，无需手动编写 SQL。 
 * 4. **功能描述**：通过注释 `@author SN` 和 `@date 2025/05/28` 可知，该接口由 SN 创建，并于 2025 年 5 月 28 日发布，主要负责处理 `SpaceIocThreat` 实体类的数据操作。 
 * 总结：这是一个基于 MyBatis 和 MyBatis-Plus 的数据访问层接口，用于操作 `SpaceIocThreat` 实体类的数据，无需手动编写 SQL，直接继承自 `BaseMapper` 即可使用通用的 CRUD 方法。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;

/**
 * 空间国际 奥委会威胁Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceIocThreatMapper extends BaseMapper<SpaceIocThreat>

{}