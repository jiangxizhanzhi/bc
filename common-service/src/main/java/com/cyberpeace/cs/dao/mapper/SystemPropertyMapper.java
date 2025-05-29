package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个基于 MyBatis-Plus 的 `SystemPropertyMapper` 接口，用于操作数据库中的 `SystemProperty` 实体类。以下是简要分析： 
 * 1. **继承关系**： 
 * - `SystemPropertyMapper` 继承自 `BaseMapper<SystemProperty>`，这是 MyBatis-Plus 提供的基础接口，提供了通用的 CRUD 操作方法。 
 * 2. **注解说明**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口，用于与数据库交互。 
 * 3. **自定义方法**： 
 * - 定义了一个自定义方法 `selectByName`，用于根据 `name` 字段查询对应的 `SystemProperty` 对象。 
 * 4. **功能描述**： 
 * - 该接口主要用于管理系统配置相关的数据操作，包括基础的增删改查以及通过名称查询特定配置的功能。 
 * 总结：这是一个典型的 MyBatis-Plus Mapper 接口，结合了通用的 CRUD 方法和自定义查询方法，便于高效管理系统配置数据。 
**/
@Mapper
public interface SystemPropertyMapper extends BaseMapper<SystemProperty> {

	/**
	 * 查询由名称
	 *
	 * @param name 名称
	 * @return {@code SystemProperty }
	 */
	SystemProperty selectByName(String name);

}

