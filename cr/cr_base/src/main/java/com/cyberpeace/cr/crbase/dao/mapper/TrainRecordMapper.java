package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 训练记录Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TrainRecordMapper` 的接口，继承自 MyBatis-Plus 提供的 `BaseMapper` 接口，并扩展了一些自定义方法。以下是代码的简要分析： 
 * ### 主要功能 
 * 1. **基础功能**： 
 * - 继承了 `BaseMapper<TrainRecord>`，这意味着该接口可以利用 MyBatis-Plus 提供的通用 CRUD 操作（如增删改查）。 
 * 2. **自定义方法**： 
 * - 提供了一系列与 `TrainRecord` 实体相关的自定义 SQL 方法，用于处理特定业务逻辑。 
 * - 这些方法通过注解 `@Param` 来接收参数，支持复杂查询或更新操作。 
 * 3. **注解说明**： 
 * - `@Mapper`：标识这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个数据访问层组件。 
 * ### 方法概览 
 * 以下是对每个方法的简要描述： 
 * - `setExtra`：设置额外信息，根据业务模板 ID、用户 ID、业务类型以及额外字段进行操作。 
 * - `setEndTime`：设置结束时间，与 `setExtra` 方法类似，但增加了结束时间的设置。 
 * - `setEndTimeByRoomId`：通过房间 ID 设置结束时间。 
 * - `countFinishedByUserIdAndType`：统计某个用户在某种类型的训练记录中已完成的数量。 
 * - `batchEnd`：批量结束指定业务模板下的所有记录。 
 * - `getByRoomId`：根据房间 ID 查询对应的训练记录。 
 * - `updateEndTimeByRoomId`：通过房间 ID 更新结束时间。 
 * ### 总结 
 * 这段代码是一个典型的 MyBatis Mapper 接口，主要用于操作 `TrainRecord` 数据表中的数据。它结合了 MyBatis-Plus 的通用功能和一些自定义 SQL 方法，为具体的业务场景提供了灵活的支持。 
**/
@Mapper
@Repository
public interface TrainRecordMapper extends BaseMapper<TrainRecord> {

	/**
	 * 设置额外
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 */
	void setExtra(@Param("business_template_id") String businessTemplateId,
			@Param("user_id") String userId, @Param("business_type") Integer businessType, @Param("extra") String extra);

	/**
	 * 设置结束时间
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 */
	void setEndTime(@Param("business_template_id") String businessTemplateId,
			@Param("user_id") String userId, @Param("business_type") Integer businessType, @Param("extra") String extra);

	/**
	 * 设置结束时间由房间id
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @param extra        额外
	 */
	void setEndTimeByRoomId(@Param("room_id") String roomId,
					@Param("business_type") Integer businessType, @Param("extra") String extra);

	/**
	 * 计数完成由用户id和类型
	 *
	 * @param userId 用户id
	 * @param type   类型
	 * @return {@code Integer }
	 */
	Integer countFinishedByUserIdAndType(@Param("userId") String userId,
			@Param("type") Integer type);

	/**
	 * 批量结束
	 *
	 * @param businessTemplateId 业务模板id
	 */
	void batchEnd(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 获取由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code TrainRecord }
	 */
	TrainRecord getByRoomId(String roomId);

	/**
	 * 更新结束时间由房间id
	 *
	 * @param roomId  房间id
	 * @param endTime 结束时间
	 */
	void updateEndTimeByRoomId(@Param("roomId") String roomId, @Param("endTime") Date endTime);
}

