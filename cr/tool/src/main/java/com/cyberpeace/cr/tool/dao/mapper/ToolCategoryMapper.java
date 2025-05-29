package com.cyberpeace.cr.tool.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工具类别Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolCategoryMapper` 的接口，继承自 `BaseMapper<ToolCategory>`。该接口用于与数据库交互，执行与工具类别（`ToolCategory`）相关的操作。以下是对代码的简要分析： 
 * ### 主要内容 
 * 1. **注解** 
 * - `@Mapper`：表示这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表示这是一个 Spring 的数据访问组件。 
 * 2. **泛型** 
 * - `ToolCategoryMapper` 继承了 `BaseMapper<ToolCategory>`，其中 `ToolCategory` 是实体类。 
 * 3. **方法** 
 * - `list` 方法：分页查询工具类别列表，支持按模式（`pattern`）和类型（`type`）过滤。 
 * - `selectByResourceIds` 方法：根据资源 ID 列表查询工具类别列表。 
 * - `selectByIds` 方法：根据 ID 列表查询工具类别列表。 
 * - `getByCnName` 方法：根据中文名称和资源 ID 查询工具类别。 
 * - `getByCnNameType` 方法：根据中文名称、资源 ID 和类型查询工具类别。 
 * - `getByType` 方法：根据类型查询工具类别列表。 
 * - `getByEnName` 方法：根据英文名称和资源 ID 查询工具类别。 
 * ### 方法参数和返回值 
 * - **参数** 
 * - `page`：分页对象。 
 * - `pattern` 和 `type`：过滤条件。 
 * - `ids`：ID 列表。 
 * - `name` 和 `resourceId`：中文名称和资源 ID。 
 * - `enName`：英文名称。 
 * - **返回值** 
 * - `IPage<ToolCategory>`：分页结果。 
 * - `List<ToolCategory>`：工具类别列表。 
 * - `ToolCategory`：单个工具类别对象。 
 * ### 验证 
 * - 使用 `@NotEmpty` 注解确保某些参数不为空。 
 * ### 总结 
 * 这个接口提供了多种查询工具类别的方法，包括分页查询、根据不同条件查询等。通过继承 `BaseMapper`，它利用了 MyBatis-Plus 提供的功能，简化了 CRUD 操作。 
**/
@Mapper
@Repository
public interface ToolCategoryMapper extends BaseMapper<ToolCategory> {

	/**
	 * 列表
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @param type    类型
	 * @return {@code IPage<ToolCategory> }
	 */
	IPage<ToolCategory> list(@Param("page") Page page, @Param("pattern") String pattern, @Param("type") String type);

	/**
	 * 查询由资源id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolCategory> }
	 */
	List<ToolCategory> selectByResourceIds(@NotEmpty @Param("ids") List<String> ids);

	/**
	 * 查询由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolCategory> }
	 */
	List<ToolCategory> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);

	/**
	 * 获取由中文名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return {@code ToolCategory }
	 */
	ToolCategory getByCnName(@NotEmpty @Param("name") String name, @Param("resourceId") String resourceId);

	ToolCategory getByCnNameType(@NotEmpty @Param("name") String name, @Param("resourceId") String resourceId, @Param("pattern") String type);


	List<ToolCategory> getByType(@NotEmpty @Param("type") String type);

	/**
	 * 获取由中文名称
	 *
	 * @param enName     中文名称
	 * @param resourceId 资源id
	 * @return {@code ToolCategory }
	 */
	ToolCategory getByEnName(@Param("enName") String enName, @Param("resourceId") String resourceId);
}

