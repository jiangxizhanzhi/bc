/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `SpaceActionMethodMapper`，用于操作数据库中的 `SpaceActionMethod` 实体类。以下是代码的简要分析： 
 * 1. **包名与命名**： 
 * - 包名是 `com.cyberpeace.space.dao.mapper`，表明这是项目中用于数据访问层（DAO）的 Mapper 文件所在的位置。 
 * - 类名是 `SpaceActionMethodMapper`，根据注释可以推测该接口用于操作 `SpaceActionMethod` 实体。 
 * 2. **继承关系**： 
 * - 该接口继承了 `BaseMapper<SpaceActionMethod>`，`BaseMapper` 是 MyBatis-Plus 提供的一个基础类，提供了常用的 CRUD 操作方法，无需手动编写 SQL。 
 * 3. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口，Spring Boot 会自动扫描并注册为一个 Bean。 
 * - `@Repository`：表示这是一个数据访问层的组件，便于 Spring 容器管理。 
 * 4. **功能描述**： 
 * - 根据注释，该接口的主要作用是操作 `SpaceActionMethod` 表的数据。 
 * - 因为继承了 `BaseMapper`，所以默认支持增删改查等基本操作，无需额外实现。 
 * 总结：这是一段典型的 MyBatis-Plus Mapper 接口代码，通过继承 `BaseMapper` 提供了基础的数据库操作能力，并通过注解与 Spring 集成。 
**/
package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间行动方法Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceActionMethodMapper extends BaseMapper<SpaceActionMethod>{
}