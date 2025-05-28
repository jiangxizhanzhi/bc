/**
 * 这段代码定义了一个名为 `ToolMapper` 的接口，它继承自 `BaseMapper<Tool>`。这个接口使用了 MyBatis-Plus 框架来简化数据库操作，并且通过注解表明这是一个 MyBatis 的 Mapper 和 Spring 的 Repository。以下是代码的详细分析： 
 * ### 接口定义 
 * ```java 
 * @Mapper 
 * @Repository 
 * public interface ToolMapper extends BaseMapper<Tool> { 
 * ``` 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper。 
 * - `@Repository` 注解表明这是一个 Spring 的数据访问组件。 
 * - `extends BaseMapper<Tool>` 表示该接口继承了 MyBatis-Plus 提供的 `BaseMapper`，提供了基本的 CRUD 操作。 
 * ### 方法定义 
 * 1. **分页查询** 
 * ```java 
 * IPage<Tool> list(Page page, @Param("listQuery") ToolListQuery toolListQuery, @Param("isAdmin") Boolean isAdmin); 
 * ``` 
 * - 参数： 
 * - `page`：分页对象，用于实现分页功能。 
 * - `toolListQuery`：工具列表查询条件。 
 * - `isAdmin`：是否为管理员，可能会影响查询结果。 
 * - 返回值：分页后的工具列表。 
 * 2. **按名称查询** 
 * ```java 
 * Tool selectByName(@NotEmpty @Param("name") String name); 
 * ``` 
 * - 参数： 
 * - `name`：工具的名称。 
 * - 返回值：符合名称的工具实体对象。 
 * - `@NotEmpty` 注解表示该参数不能为空。 
 * 3. **按类别查询** 
 * ```java 
 * List<Tool> selectByCategory(@Param("resourceId") String resourceId); 
 * ``` 
 * - 参数： 
 * - `resourceId`：资源ID。 
 * - 返回值：符合类别条件的工具列表。 
 * 4. **按名称和资源ID查询** 
 * ```java 
 * Tool selectOtherByName(@Param("name") String name, @Param("resourceId") String resourceId); 
 * ``` 
 * - 参数： 
 * - `name`：工具的名称。 
 * - `resourceId`：资源ID。 
 * - 返回值：符合名称和资源ID条件的工具实体对象。 
 * 5. **统计信息** 
 * ```java 
 * List<ToolStatistics> statistics(); 
 * ``` 
 * - 返回值：工具的统计信息列表。 
 * 6. **列出类型和平台** 
 * ```java 
 * List<Tool> listTypeAndPlatform(); 
 * ``` 
 * - 返回值：包含工具类型和平台的工具列表。 
 * ### 总结 
 * 该接口主要实现了对工具数据的各种查询操作，包括分页查询、按名称查询、按类别查询、按名称和资源ID查询、统计信息以及列出类型和平台。这些方法覆盖了常见的数据查询需求，使得数据操作更加方便和高效。 
**/
package com.cyberpeace.cr.tool.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.context.ToolStatistics;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工具Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface ToolMapper extends BaseMapper<Tool> {

    /**
     * 列表
     *
     * @param page          分页
     * @param toolListQuery 工具列表查询
     * @param isAdmin       是管理员
     * @return {@code IPage<Tool> }
     */
    IPage<Tool> list(Page page, @Param("listQuery") ToolListQuery toolListQuery, @Param("isAdmin") Boolean isAdmin);

    /**
     * 查询由名称
     *
     * @param name 名称
     * @return {@code Tool }
     */
    Tool selectByName(@NotEmpty @Param("name") String name);

    /**
     * 查询由类别
     *
     * @param resourceId 资源id
     * @return {@code List<Tool> }
     */
    List<Tool> selectByCategory(@Param("resourceId") String resourceId);

    /**
     * 查询其他由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return {@code Tool }
     */
    Tool selectOtherByName(@Param("name") String name,@Param("resourceId") String resourceId);

    /**
     * 统计学
     *
     * @return {@code List<ToolStatistics> }
     */
    List<ToolStatistics> statistics();

    /**
     * 列表类型和平台
     *
     * @return {@code List<Tool> }
     */
    List<Tool> listTypeAndPlatform();

}
