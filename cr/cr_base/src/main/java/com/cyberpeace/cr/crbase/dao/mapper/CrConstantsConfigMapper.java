/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `CrConstantsConfigMapper`，用于操作数据库中的 `CrConstantsConfig` 表或数据。以下是简要分析： 
 * 1. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个 Spring 的数据访问组件，便于 Spring 容器管理。 
 * 2. **接口方法**： 
 * - 方法 `getAll()`：返回一个 `List<CrConstantsConfig>`，表示从数据库中获取所有 `CrConstantsConfig` 数据。 
 * 3. **功能描述**： 
 * - 这个接口主要用于操作与靶场常数配置相关的数据，提供了一种通过 MyBatis 操作数据库的方式。 
 * 总结：这是一个典型的 MyBatis Mapper 接口，用于获取靶场常数配置的所有数据。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * 靶场常数配置Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrConstantsConfigMapper {

	/**
	 * 获取都
	 *
	 * @return {@code List<CrConstantsConfig> }
	 */
	List<CrConstantsConfig> getAll();
}
