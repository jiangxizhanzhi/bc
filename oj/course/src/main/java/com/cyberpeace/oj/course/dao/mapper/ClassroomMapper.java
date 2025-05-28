/**
 * 这段代码定义了一个名为 `ClassroomMapper` 的接口，继承自 `BaseMapper<Classroom>`，并使用了 MyBatis 的注解 `@Mapper`。这个接口提供了多个方法来操作 `Classroom` 实体类的数据。以下是代码的简要分析： 
 * ### 主要功能 
 * - **数据访问**：提供了一系列方法来查询、插入、更新和删除 `Classroom` 实体对象。 
 * - **分页支持**：支持基于分页的查询。 
 * ### 方法详解 
 * 1. **selectByOrgAndLesson** 
 * - 根据组织ID和课时ID查询课堂信息。 
 * 2. **selectList** 
 * - 根据 `ClassRoomListQuery` 对象查询课堂列表。 
 * 3. **selectListByCourse** 
 * - 在指定的时间范围内，根据课程ID和 `ClassRoomListQuery` 对象查询课堂列表。 
 * 4. **selectByLesson** 
 * - 根据课时ID列表查询课堂列表。 
 * 5. **selectByOrgId** 
 * - 根据组织ID查询课堂列表。 
 * 6. **selectByPlan** 
 * - 根据计划ID查询课堂列表。 
 * 7. **deleteByOrgIds** 
 * - 根据组织ID列表删除课堂记录。 
 * 8. **page** 
 * - 支持分页查询课堂列表，同时考虑了用户的权限和课程ID等条件。 
 * 9. **selectIdsByPlanIds** 
 * - 根据计划ID列表查询课堂ID列表。 
 * 10. **selectByUserPlan** 
 * - 根据用户ID和计划ID查询课堂列表。 
 * 11. **selectTempTaskIdsByUser** 
 * - 根据用户ID和是否为超级用户查询临时任务ID列表。 
 * ### 特点 
 * - **扩展性**：通过继承 `BaseMapper`，默认包含了基本的 CRUD 操作。 
 * - **灵活性**：提供了多种查询方式，满足不同的业务需求。 
 * - **分页支持**：通过 `IPage` 接口支持分页查询，便于处理大数据量的场景。 
 * ### 总结 
 * `ClassroomMapper` 接口是一个功能丰富的数据访问层接口，它不仅提供了基本的 CRUD 操作，还提供了复杂的查询功能，如分页查询、多条件组合查询等。这些方法可以满足不同场景下的课堂管理需求。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;

/**
 * 课堂Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {

	/**
	 * 根据组织和课时查询课堂信息
	 *
	 * @param orgId
	 * @param lessonId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Classroom
	 */
	Classroom selectByOrgAndLesson(@Param("orgId") String orgId,
			@Param("lessonId") String lessonId);

	/**
	 * 查询课程表数据
	 *
	 * @param listQuery
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectList(@Param("listQuery") ClassRoomListQuery listQuery);

	/**
	 * 课程表数据查询 查询时间范围内 自己创建的 或者组织下的
	 *
	 * @param courseIds
	 * @param roomListQuery
	 * @return
	 * @throws
	 * @see List<String>
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectListByCourse(@Param("courseIds") List<String> courseIds,
			@Param("listQuery") ClassRoomListQuery roomListQuery);

	/**
	 * 课程表删除
	 *
	 * @param lessonIds 课id列表
	 * @return {@code List<Classroom> }
	 * @throws
	 * @see List<String>
	 * @see List<Classroom>
	 */
	List<Classroom> selectByLesson(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 查询该组织的课堂表
	 *
	 * @param orgId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByOrgId(@Param("orgId") String orgId);

	/**
	 * 根据计划查询房间
	 *
	 * @param planId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByPlan(@Param("planId") String planId);

	/**
	 * 根据ids删除房间
	 *
	 * @param orgIds 组织id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteByOrgIds(@Param("orgIds") List<String> orgIds);

	/**
	 * 分页
	 *
	 * @param listQuery   列表查询
	 * @param userId      用户id
	 * @param trainPlanId 训练计划id
	 * @param childrenOrg 子级组织
	 * @param isAdmin     是管理员
	 * @param courseIds   课程id列表
	 * @return {@code IPage<Classroom> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see String
	 * @see List<String>
	 * @see boolean
	 * @see List<String>
	 * @see IPage<Classroom>
	 */
	IPage<Classroom> page(Page listQuery, String userId, String trainPlanId, List<String> childrenOrg, boolean isAdmin, List<String> courseIds);

	/**
	 * 查询id列表由计划id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectIdsByPlanIds(List<String> ids);

	/**
	 * 查询由用户计划
	 *
	 * @param userId 用户id
	 * @param planId 计划id
	 * @return {@code List<Classroom> }
	 * @throws
	 * @see String
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByUserPlan(String userId, String planId);

	/**
	 * 查询临时任务id列表由用户
	 *
	 * @param userId      用户id
	 * @param isSuperUser 是超级用户
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see boolean
	 * @see List<String>
	 */
	List<String> selectTempTaskIdsByUser(String userId, boolean isSuperUser);
}
