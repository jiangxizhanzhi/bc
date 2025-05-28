/**
 * 这段代码定义了一个名为 `ToolGhdbMapper` 的接口，该接口继承自 `BaseMapper<ToolGhdb>`。这个接口用于操作与 `ToolGhdb` 实体类相关的数据库操作。下面是详细的分析： 
 * ### 1. 包声明 
 * ```java 
 * package com.cyberpeace.cr.tool.dao.mapper; 
 * ``` 
 * - 这行代码指定了该文件属于 `com.cyberpeace.cr.tool.dao.mapper` 包。 
 * ### 2. 导入的包 
 * ```java 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import org.springframework.stereotype.Repository; 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.baomidou.mybatisplus.core.metadata.IPage; 
 * import com.baomidou.mybatisplus.extension.plugins.pagination.Page; 
 * import com.cyberpeace.cr.tool.dao.entity.ToolGhdb; 
 * ``` 
 * - `org.apache.ibatis.annotations.Mapper`: 表示这是一个 MyBatis 的 Mapper 接口。 
 * - `org.apache.ibatis.annotations.Param`: 用于指定方法参数的别名。 
 * - `org.springframework.stereotype.Repository`: 表示这是一个 Spring 管理的仓库层组件。 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`: MyBatis-Plus 提供的基类，用于简化 CRUD 操作。 
 * - `com.baomidou.mybatisplus.core.metadata.IPage`: MyBatis-Plus 分页插件的分页对象。 
 * - `com.baomidou.mybatisplus.extension.plugins.pagination.Page`: MyBatis-Plus 分页插件的分页实现类。 
 * - `com.cyberpeace.cr.tool.dao.entity.ToolGhdb`: 定义的实体类。 
 * ### 3. 接口声明 
 * ```java 
 * @Mapper 
 * @Repository 
 * public interface ToolGhdbMapper extends BaseMapper<ToolGhdb> { 
 * ``` 
 * - `@Mapper`: 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`: 注解表明这是一个 Spring 管理的仓库层组件。 
 * - `interface ToolGhdbMapper`: 声明一个接口 `ToolGhdbMapper`，它继承了 `BaseMapper<ToolGhdb>`，从而具备了基本的 CRUD 操作。 
 * ### 4. 方法声明 
 * ```java 
 * /** 
 * * 列表 
 * * 
 * * @param page   分页 
 * * @param author 作者 
 * * @param title  标题 
 * * @return {@code IPage<ToolGhdb> } 
 * *
 * IPage<ToolGhdb> list(Page page, @Param("author") String author, @Param("title") String title); 
 * ``` 
 * - `@param page`: 参数 `page` 是一个 `Page` 对象，用于分页查询。 
 * - `@param author`: 参数 `author` 是一个 `String` 类型的变量，表示作者名称。 
 * - `@param title`: 参数 `title` 是一个 `String` 类型的变量，表示标题。 
 * - 返回值是一个 `IPage<ToolGhdb>` 对象，表示分页后的结果集。 
 * ### 总结 
 * - `ToolGhdbMapper` 是一个用于操作 `ToolGhdb` 实体类的 MyBatis Mapper 接口。 
 * - 继承了 `BaseMapper<ToolGhdb>`，从而具备了基本的 CRUD 操作。 
 * - 提供了一个自定义的方法 `list`，可以根据分页、作者和标题进行查询，并返回分页结果。 
**/
package com.cyberpeace.cr.tool.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;

/**
 * 工具GHDB 数据库Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface ToolGhdbMapper extends BaseMapper<ToolGhdb> {

	/**
	 * 列表
	 *
	 * @param page   分页
	 * @param author 作者
	 * @param title  标题
	 * @return {@code IPage<ToolGhdb> }
	 */
	IPage<ToolGhdb> list(Page page, @Param("author") String author, @Param("title") String title);
}
