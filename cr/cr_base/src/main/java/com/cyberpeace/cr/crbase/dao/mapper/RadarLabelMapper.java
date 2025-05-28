/**
 * 这段代码定义了一个名为 `RadarLabelMapper` 的接口，用于操作数据库中与雷达标签（RadarLabel）相关的数据。以下是代码的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.cr.crbase.dao.mapper` 包下，表明这是一个数据访问层（DAO）中的 Mapper 接口。 
 * 2. **接口继承**：`RadarLabelMapper` 继承了 `BaseMapper<RadarLabel>`，这是 MyBatis-Plus 提供的一个通用 Mapper 接口，提供了常见的 CRUD 操作方法，无需手动编写 SQL。 
 * 3. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表示这是一个 Spring 中的仓库组件，便于 Spring 容器管理。 
 * 4. **方法定义**： 
 * - `selectList()`：自定义方法，用于查询所有雷达标签数据并返回一个 `List<RadarLabel>`。 
 * 总结：这是一个基于 MyBatis-Plus 的 Mapper 接口，主要负责雷达标签数据的增删改查操作，其中 `selectList()` 是一个自定义查询方法。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 雷达标签Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface RadarLabelMapper extends BaseMapper<RadarLabel> {


	/**
	 * 查询列表
	 *
	 * @return {@code List<RadarLabel> }
	 */
	List<RadarLabel> selectList();
}
