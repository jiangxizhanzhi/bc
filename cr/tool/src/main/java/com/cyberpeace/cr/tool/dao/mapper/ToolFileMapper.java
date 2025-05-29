package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工具文件Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolFileMapper` 的接口，它继承了 `BaseMapper<ToolFile>`，用于操作 `ToolFile` 实体类。以下是对其主要部分的分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.cr.tool.dao.mapper;` 
 * - 这个接口位于 `com.cyberpeace.cr.tool.dao.mapper` 包下。 
 * 2. **导入语句**： 
 * - 导入了一些常用的 MyBatis 和 Spring 相关的注解和类，例如 `@Mapper`、`@Repository`、`List` 和 `@Param`。 
 * 3. **接口声明**： 
 * - `public interface ToolFileMapper extends BaseMapper<ToolFile>` 
 * - `ToolFileMapper` 继承了 `BaseMapper<ToolFile>`，这意味着它可以使用 MyBatis-Plus 提供的默认 CRUD 操作方法。 
 * 4. **注解**： 
 * - `@Mapper`：表示这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个数据访问层（DAO）组件。 
 * 5. **方法**： 
 * - `List<ToolFile> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);` 
 * - 这个方法用于根据一组 ID 列表查询 `ToolFile` 对象列表。 
 * - `@NotEmpty` 注解确保传入的 `ids` 列表不为空。 
 * - `@Param("ids")` 用于指定参数名称为 `ids`，这在 SQL 映射文件中会用到。 
 * ### 总结 
 * - `ToolFileMapper` 是一个继承自 `BaseMapper<ToolFile>` 的接口。 
 * - 它定义了一个方法 `selectByIds`，用于通过 ID 列表查询 `ToolFile` 实体对象。 
 * - 该接口使用了 MyBatis 和 Spring 提供的注解来简化开发和增强功能。 
 * - 这段代码展示了如何使用 MyBatis-Plus 来简化数据库操作。 
**/
@Mapper
@Repository
public interface ToolFileMapper extends BaseMapper<ToolFile> {

	/**
	 * 查询由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolFile> }
	 */
	List<ToolFile> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);
}

