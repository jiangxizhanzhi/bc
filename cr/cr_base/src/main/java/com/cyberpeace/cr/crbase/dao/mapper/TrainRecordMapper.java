package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

/**
 * 训练记录映射
 *
 * @author SN
 * @date 2024/11/07
 */
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
