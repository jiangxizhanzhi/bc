/**
 * 这段代码定义了一个名为 `CrTaskLogMapper` 的接口，用于操作数据库中的 `CrTaskLog` 实体类及其相关数据。以下是对其的简要分析： 
 * 1. **继承与注解**： 
 * - 接口 `CrTaskLogMapper` 继承自 `BaseMapper<CrTaskLog>`，这是 MyBatis-Plus 提供的一个基础接口，提供了常见的 CRUD 操作方法。 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口，通常需要配合 Spring 的 `@EnableMapperScan` 使用。 
 * - `@Repository` 注解表明该接口是一个数据访问层组件。 
 * 2. **功能方法**： 
 * - `selectReleaseByRoomIdAndActionCodes`：根据房间ID和操作码查询释放记录。 
 * - `selectReleaseByRoomId`：仅根据房间ID查询释放记录。 
 * - `getDynamicLogByRoomIdAndUserId`：根据房间ID、用户ID、ID列表和操作码获取动态日志。 
 * - `getReleaseIdsByRoomIdAndUserId`：根据房间ID和用户ID获取释放ID列表。 
 * - `batchInsert`：批量插入任务日志。 
 * 3. **设计特点**： 
 * - 方法命名清晰，直接反映了其功能。 
 * - 结合了 MyBatis-Plus 的便捷性和自定义扩展能力，适合复杂的业务场景。 
 * - 使用了泛型和集合类型，支持灵活的数据处理需求。 
 * 总体而言，这段代码实现了对 `CrTaskLog` 实体类的多种查询和操作，适用于需要复杂查询逻辑的应用场景。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.service.context.DynamicData;

/**
 * 靶场任务日志Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrTaskLogMapper extends BaseMapper<CrTaskLog> {

	/**
	 * 查询释放由房间id和行动代码
	 *
	 * @param roomId      房间id
	 * @param actionCodes 操作代码
	 * @return {@code List<CrTaskLog> }
	 */
	List<CrTaskLog> selectReleaseByRoomIdAndActionCodes(String roomId, List<Integer> actionCodes);

	/**
	 * 查询释放由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTaskLog> }
	 */
	List<CrTaskLog> selectReleaseByRoomId(String roomId);

	/**
	 * 获取动态日志由房间id和用户id
	 *
	 * @param roomId      房间id
	 * @param studentId   学员id
	 * @param ids         id列表
	 * @param actionCodes 操作代码
	 * @return {@code List<DynamicData> }
	 */
	List<DynamicData> getDynamicLogByRoomIdAndUserId(String roomId, String studentId,
			List<String> ids, List<Integer> actionCodes);

	/**
	 * 获取释放id列表由房间id和用户id
	 *
	 * @param roomId    房间id
	 * @param studentId 学员id
	 * @return {@code List<String> }
	 */
	List<String> getReleaseIdsByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 批量插入
	 *
	 * @param taskLogs 任务日志
	 */
	void batchInsert(List<CrTaskLog> taskLogs);
}
