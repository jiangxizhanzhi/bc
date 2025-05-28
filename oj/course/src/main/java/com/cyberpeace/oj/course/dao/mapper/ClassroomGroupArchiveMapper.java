/**
 * 这段代码定义了一个名为 `ClassroomGroupArchiveMapper` 的接口，它继承自 `BaseMapper<ClassroomGroupArchive>`。这个接口使用了 MyBatis-Plus 框架来简化数据库操作，并且被标注为一个 MyBatis 的 `@Mapper`。 
 * ### 主要组成部分： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.oj.course.dao.mapper; 
 * ``` 
 * 这行代码指定了该类所在的包路径。 
 * 2. **导入的类**： 
 * ```java 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.oj.course.dao.entity.ClassroomGroupArchive; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import java.util.List; 
 * ``` 
 * 这些是需要在当前文件中使用的类和注解。 
 * 3. **类和接口声明**： 
 * ```java 
 * @Mapper 
 * public interface ClassroomGroupArchiveMapper extends BaseMapper<ClassroomGroupArchive> { 
 * ``` 
 * - `@Mapper`：表示这是一个 MyBatis 的 Mapper。 
 * - `ClassroomGroupArchiveMapper` 接口继承了 `BaseMapper<ClassroomGroupArchive>`，这意味着它已经具备了一些基本的 CRUD 操作方法。 
 * 4. **方法声明**： 
 * ```java 
 * /** 
 * * 根据名称查询课堂分组模板 
 * * 
 * * @param classroomId 
 * * @param name 
 * * @return 
 * * @throws 
 * * @see String 
 * * @see String 
 * * @see ClassroomGroupArchive 
 * */ 
 * ClassroomGroupArchive selectByRoomAndName(@Param("roomId") String classroomId, @Param("name") String name); 
 * /** 
 * * 查询分组模板数据 
 * * 
 * * @param classroomId 
 * * @return 
 * * @throws 
 * * @see String 
 * * @see List<ClassroomGroupArchive> 
 * */ 
 * List<ClassroomGroupArchive> selectByRoom(@Param("roomId") String classroomId); 
 * ``` 
 * - `selectByRoomAndName` 方法根据 `classroomId` 和 `name` 查询一条 `ClassroomGroupArchive` 记录。 
 * - `selectByRoom` 方法根据 `classroomId` 查询所有 `ClassroomGroupArchive` 记录。 
 * ### 总结： 
 * - `ClassroomGroupArchiveMapper` 是一个 MyBatis 的 Mapper 接口，用于处理与 `ClassroomGroupArchive` 对象相关的数据库操作。 
 * - 它继承了 `BaseMapper<ClassroomGroupArchive>`，因此已经具备了基本的 CRUD 操作。 
 * - 接口中有两个自定义的方法，分别用于根据教室ID和名称查询单个记录以及根据教室ID查询多个记录。 
 * - 使用 `@Param` 注解来指定方法参数的名称，以便在 MyBatis 的 XML 配置文件或注解中引用这些参数。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupArchive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课堂群档案Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface ClassroomGroupArchiveMapper extends BaseMapper<ClassroomGroupArchive> {

	/**
	 * 根据名称查询课堂分组模板
	 *
	 * @param classroomId
	 * @param name
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see ClassroomGroupArchive
	 */
	ClassroomGroupArchive selectByRoomAndName(@Param("roomId") String classroomId,
			@Param("name") String name);

	/**
	 * 查询分组模板数据
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<ClassroomGroupArchive>
	 */
	List<ClassroomGroupArchive> selectByRoom(@Param("roomId") String classroomId);
}
