package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 工具壳代号Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolShellCodeMapper` 的接口，它继承了 `BaseMapper<ToolShellCode>`。这是一个使用 MyBatis-Plus 框架的 Mapper 接口，用于操作数据库中的 `ToolShellCode` 实体类。 
 * ### 主要组成部分： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.cr.tool.dao.mapper; 
 * ``` 
 * 这行代码指定了该文件属于 `com.cyberpeace.cr.tool.dao.mapper` 包。 
 * 2. **导入语句**： 
 * ```java 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.baomidou.mybatisplus.core.metadata.IPage; 
 * import com.baomidou.mybatisplus.extension.plugins.pagination.Page; 
 * import com.cyberpeace.cr.tool.dao.entity.ToolShellCode; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import org.springframework.stereotype.Repository; 
 * ``` 
 * 导入了所需的类和注解，包括 MyBatis-Plus 的 `BaseMapper`、分页相关的 `IPage` 和 `Page`，以及 Spring 的 `Repository` 注解等。 
 * 3. **接口定义**： 
 * ```java 
 * @Mapper 
 * @Repository 
 * public interface ToolShellCodeMapper extends BaseMapper<ToolShellCode> { 
 * ``` 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个数据访问层的组件。 
 * - `extends BaseMapper<ToolShellCode>`：继承自 `BaseMapper`，意味着这个接口可以自动拥有 CRUD 操作的功能。 
 * 4. **方法定义**： 
 * ```java 
 * IPage<ToolShellCode> list(Page page, @Param("author") String author, 
 * @Param("platform") String platform, @Param("edbVerified") Boolean edbVerified); 
 * ``` 
 * - 方法名：`list` 
 * - 参数： 
 * - `Page page`：用于分页查询。 
 * - `String author`：查询条件，表示作者。 
 * - `String platform`：查询条件，表示平台。 
 * - `Boolean edbVerified`：查询条件，表示是否经过 EDB 验证。 
 * - 返回值：`IPage<ToolShellCode>`，即分页结果。 
 * ### 总结： 
 * 这个接口主要用于从数据库中查询工具壳代码（`ToolShellCode`），支持根据作者、平台和 EDB 验证状态进行分页查询。通过继承 `BaseMapper`，它自动获得了基本的 CRUD 功能，并且可以通过 `list` 方法实现复杂的查询需求。 
**/
@Mapper
@Repository
public interface ToolShellCodeMapper extends BaseMapper<ToolShellCode> {

	/**
	 * 列表
	 *
	 * @param page        分页
	 * @param author      作者
	 * @param platform    平台
	 * @param edbVerified EDB 验证
	 * @return {@code IPage<ToolShellCode> }
	 */
	IPage<ToolShellCode> list(Page page, @Param("author") String author,
			@Param("platform") String platform, @Param("edbVerified") Boolean edbVerified);
}

