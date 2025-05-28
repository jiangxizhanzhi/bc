/**
 * 这段代码定义了一个名为 `ClassroomService` 的接口，该接口继承自 `IService<Classroom>`。这个接口包含多个方法，用于处理与“课堂”相关的各种操作。以下是对这些方法的简要分析： 
 * ### 接口定义 
 * - **接口名称**：`ClassroomService` 
 * - **继承接口**：`IService<Classroom>` 
 * - **包名**：`com.cyberpeace.oj.course.service` 
 * ### 方法摘要 
 * 1. **`selectList`** 
 * - **描述**：根据提供的查询条件和认证上下文返回一个班级列表。 
 * - **参数**：`ClassRoomListQuery roomListQuery`, `AuthContext authContext` 
 * - **返回值**：`List<ClassroomBo>`，其中 `ClassroomBo` 是业务对象。 
 * 2. **`selectSignList`** 
 * - **描述**：获取指定班级的签到信息列表。 
 * - **参数**：`Page page`, `String classroomId` 
 * - **返回值**：`ListRsp<ClassroomMonitorBo>`，其中 `ClassroomMonitorBo` 包含监控信息。 
 * 3. **`classroomStatistic`** 
 * - **描述**：获取指定班级的学习统计信息。 
 * - **参数**：`String classroomId`, `AuthContext authContext` 
 * - **返回值**：`ClassroomStatisticBo`，统计信息。 
 * 4. **`practiceStatistic`** 
 * - **描述**：获取指定班级的练习统计信息。 
 * - **参数**：`ClassroomPracticeContext classroomPracticeContext`, `Page page` 
 * - **返回值**：`ClassroomPracticeBo`，练习统计信息。 
 * 5. **`practiceDetail`** 
 * - **描述**：获取指定班级、课程和用户的练习详情。 
 * - **参数**：`String classroomId`, `String lessonId`, `String userId` 
 * - **返回值**：`ClassroomPracticeDetailBo`，练习详情。 
 * 6. **`deleteClassroom`** 
 * - **描述**：删除指定班级的数据。 
 * - **参数**：`String classroomId`, `AuthContext authContext` 
 * - **返回值**：`Integer`，表示删除结果。 
 * 7. **`getChatUsers`** 
 * - **描述**：获取指定房间的聊天用户信息。 
 * - **参数**：`String roomId`, `UserInfo userContext` 
 * - **返回值**：`DashboardInfo`，包含用户信息和聊天信息。 
 * 8. **`importTrainPlan`** 
 * - **描述**：导入训练计划。 
 * - **参数**：`String realPath`, `UserInfo userInfo`, `boolean cover` 
 * - **返回值**：`List<Classroom>`，导入后的班级列表。 
 * 9. **`exportTrainPlan`** 
 * - **描述**：导出训练计划。 
 * - **参数**：`UserInfo userInfo`, `String filePath` 
 * - **返回值**：无 
 * 10. **`getChatGroupUsers`** 
 * - **描述**：获取指定房间和组的聊天用户信息。 
 * - **参数**：`String roomId`, `String groupId` 
 * - **返回值**：`List<DashboardUser>`，包含用户信息。 
 * 11. **`removeClassrooms`** 
 * - **描述**：删除与特定组织关联的班级记录。 
 * - **参数**：`List<String> orgs` 
 * - **返回值**：无 
 * 12. **`selectPage`** 
 * - **描述**：分页查询班级信息。 
 * - **参数**：`ClassRoomPageQuery roomListQuery`, `AuthContext authContext` 
 * - **返回值**：`IPage<ClassroomBo>`，分页结果。 
 * 13. **`selectIdsByPlanIds`** 
 * - **描述**：根据计划ID列表查询对应的班级ID列表。 
 * - **参数**：`List<String> ids` 
 * - **返回值**：`List<String>`，班级ID列表。 
 * 14. **`selectTempTaskIdsByUser`** 
 * - **描述**：根据用户上下文查询临时任务ID列表。 
 * - **参数**：`UserInfo userContext` 
 * - **返回值**：`List<String>`，临时任务ID列表。 
 * ### 总结 
 * `ClassroomService` 接口提供了丰富的功能来管理课堂相关的信息，包括查询、统计、导入、导出等操作。每个方法都有明确的功能描述和参数，确保了清晰的接口使用方式。 
**/
package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.*;

import java.util.List;

/**
 * 课堂Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface ClassroomService extends IService<Classroom> {

	/**
	 * 分页查询课时表信息
	 *
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see AuthContext
	 * @see List<ClassroomBo>
	 */
	List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 获取用户签到信息列表
	 *
	 * @param page
	 * @param classroomId
	 * @return
	 * @throws
	 * @see Page
	 * @see String
	 * @see ListRsp<ClassroomMonitorBo>
	 */
	ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId);

	/**
	 * 课堂学习统计
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see ClassroomStatisticBo
	 */
	ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomPracticeContext
	 * @param page                     分页
	 * @return
	 * @throws
	 * @see ClassroomPracticeContext
	 * @see Page
	 * @see ClassroomPracticeBo
	 */
	ClassroomPracticeBo practiceStatistic(ClassroomPracticeContext classroomPracticeContext,
			Page page);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomId
	 * @param lessonId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see ClassroomPracticeDetailBo
	 */
	ClassroomPracticeDetailBo practiceDetail(String classroomId, String lessonId, String userId);

	/**
	 * 课堂数据删除 课堂数据 场景数据 分组数据
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Integer
	 */
	Integer deleteClassroom(String classroomId, AuthContext authContext);

	/**
	 * 获取课堂聊天用户信息
	 *
	 * @param roomId
	 * @param userContext
	 * @return
	 * @throws
	 * @see String
	 * @see UserInfo
	 * @see DashboardInfo
	 */
	DashboardInfo getChatUsers(String roomId, UserInfo userContext);

	/**
	 * 训练计划导入
	 *
	 * @param realPath
	 * @param userInfo 用户信息
	 * @param cover    盖
	 * @return
	 * @throws
	 * @see String
	 * @see UserInfo
	 * @see boolean
	 * @see List<Classroom>
	 */
	List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover);

	/**
	 * 训练计划导出
	 *
	 * @param userInfo 用户信息
	 * @param filePath 文件路径
	 * @throws
	 * @see UserInfo
	 * @see String
	 */
	void exportTrainPlan(UserInfo userInfo, String filePath);

	/**
	 * 获取课堂聊天组信息
	 *
	 * @param roomId
	 * @param groupId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<DashboardUser>
	 */
	List<DashboardUser> getChatGroupUsers(String roomId, String groupId);

	/**
	 * 获取中台组织删除消息时 同步删除课堂表中关联记录
	 *
	 * @param orgs 删除组织ids
	 * @throws
	 * @see List<String>
	 */
	void removeClassrooms(List<String> orgs);

	/**
	 * 查询分页
	 *
	 * @param roomListQuery 房间列表查询
	 * @param authContext   auth 上下文
	 * @return {@code IPage<ClassroomBo> }
	 * @throws
	 * @see ClassRoomPageQuery
	 * @see AuthContext
	 * @see IPage<ClassroomBo>
	 */
	IPage<ClassroomBo>  selectPage(ClassRoomPageQuery roomListQuery, AuthContext authContext);

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
	 * 查询临时任务id列表由用户
	 *
	 * @param userContext 用户上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see UserInfo
	 * @see List<String>
	 */
	List<String> selectTempTaskIdsByUser(UserInfo userContext);
}
