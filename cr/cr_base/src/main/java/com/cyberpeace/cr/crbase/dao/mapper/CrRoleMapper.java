/**
 * ### 分析与总结 
 * #### 1. **代码概述** 
 * 该代码定义了一个名为 `CrRoleMapper` 的接口，继承自 MyBatis 的 `BaseMapper` 和 Spring 的 `Repository`。它主要用于操作数据库中的 `CrRole` 实体类及相关数据，提供了一系列针对靶场岗位（`CrRole`）的查询、插入、更新和删除功能。 
 * --- 
 * #### 2. **主要功能模块** 
 * - **基础 CRUD 功能**： 
 * - 继承了 `BaseMapper<CrRole>`，因此默认实现了基本的增删改查功能（如 `insert`、`delete`、`update` 等）。 
 * - **扩展查询方法**： 
 * - 提供了多个自定义方法，用于根据特定条件查询 `CrRole` 数据，例如： 
 * - 根据房间 ID 查询岗位：`selectCrRoleByRoomId` 
 * - 根据用户 ID 查询房间 ID 列表：`getRoomIdsByUserId` 
 * - 根据房间 ID 和用户 ID 查询岗位：`getCrRoleByRoomIdAndUserId` 
 * - 批量删除：`batchDeleteByRoomId` 
 * - 根据用户 ID 和类型查询房间 ID：`getRoomIdsByUserIdAndType` 
 * - **复杂逻辑查询**： 
 * - 提供了一些复杂的组合查询，例如： 
 * - 根据用户 ID 和类型查询房间和角色分组：`getRoomIdsAndRoleGroupByUserIdAndType` 
 * - 根据房间 ID 和业务类型查询角色：`getRolesByRoom` 
 * - 根据房间 ID 和任务 ID 查询岗位：`selectByRoomIdAndMissionId` 
 * - 根据房间 ID 和组查询角色：`selectByRoomIdAndGroups` 
 * - **特殊操作**： 
 * - 恢复节点：`recoverNodesByRoomId`，可能用于某种状态恢复操作。 
 * - 批量查询：`selectBatchByRoomIds`，用于根据多个房间 ID 查询岗位。 
 * --- 
 * #### 3. **技术特点** 
 * - **MyBatis 注解**： 
 * - 使用了 `@Mapper` 注解标记为 MyBatis 的 Mapper 接口。 
 * - 方法参数中使用了 `@Param` 注解，明确指定参数名称。 
 * - **Spring 支持**： 
 * - 使用了 `@Repository` 注解，表明这是一个数据访问层组件。 
 * - **泛型与集合支持**： 
 * - 支持返回值为集合类型（如 `List<String>` 或 `List<CrRole>`），也支持单个对象（如 `CrRole` 或 `Boolean`）。 
 * --- 
 * #### 4. **适用场景** 
 * 该代码适用于需要对靶场岗位（`CrRole`）进行精细化管理和查询的场景，例如： 
 * - 靶场系统中基于房间 ID 和用户 ID 的权限控制。 
 * - 复杂的多条件查询需求，如根据用户 ID、类型或任务 ID 查询相关岗位信息。 
 * - 需要批量操作或恢复某些节点的状态。 
 * --- 
 * #### 5. **优点** 
 * - **清晰的功能划分**：每个方法都有明确的注释说明其用途，便于理解。 
 * - **灵活性高**：支持多种查询方式，满足不同业务需求。 
 * - **扩展性强**：继承了 `BaseMapper`，可以轻松扩展基础功能。 
 * --- 
 * #### 6. **潜在改进点** 
 * - **异常处理**：部分方法未明确说明异常处理机制，建议在方法注释中补充说明可能抛出的异常。 
 * - **性能优化**：对于大规模数据查询，需注意 SQL 性能优化，避免全表扫描。 
 * - **文档完善**：可进一步完善注释，补充更多上下文信息（如参数来源、返回值含义等）。 
 * --- 
 * #### 7. **总结** 
 * 该代码是一个典型的 MyBatis Mapper 接口，提供了丰富的查询和操作方法，适合用于靶场系统的岗位管理场景。整体设计清晰、结构合理，具有较高的实用性和扩展性。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;

/**
 * 靶场岗位Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

	/**
	 * 查询靶场岗位由房间id
	 *
	 * @param roleQuery 岗位查询
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/**
	 * 获取房间id列表由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<String> }
	 */
	List<String> getRoomIdsByUserId(String userId);

	/**
	 * 获取靶场岗位由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrRole }
	 */
	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 批量删除由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean }
	 */
	Boolean batchDeleteByRoomId(@Param("roomId") String roomId);

	/**
	 * 获取房间id列表由用户id和类型
	 *
	 * @param userId 用户id
	 * @param code   代号
	 * @return {@code List<String> }
	 */
	List<String> getRoomIdsByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 根据user_id查询某类型训练的房间和角色
	 *
	 * @param userId
	 * @param code
	 * @return
	 */
	List<RoomRoleContext> getRoomIdsAndRoleGroupByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 获取角色由房间
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**
	 * 根据红方或蓝方自己的角色已经管理组角色
	 *
	 * @param roomId
	 * @param roleId
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	/**
	 * 查询由房间id和任务id
	 *
	 * @param roomId    房间id
	 * @param missionId 任务id
	 * @return {@code CrRole }
	 */
	CrRole selectByRoomIdAndMissionId(@Param("roomId") String roomId,
			@Param("missionId") String missionId);

	/**
	 * 通过房间id和组查询
	 *
	 * @param roomId 房间id
	 * @param groups 组
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> selectByRoomIdAndGroups(@Param("roomId") String roomId,
			@Param("groups") Collection<Integer> groups);

	/**
	 * 获取角色由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> getRolesByRoomId(@Param("roomId") String roomId);

	/**
	 * 恢复节点由房间id
	 *
	 * @param roomId 房间id
	 */
	void recoverNodesByRoomId(@Param("roomId") String roomId);

	/**
	 * 查询批量由房间id列表
	 *
	 * @param roomIdList 房间id列表
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> selectBatchByRoomIds(@Param("roomIdList") List<String> roomIdList);
}
