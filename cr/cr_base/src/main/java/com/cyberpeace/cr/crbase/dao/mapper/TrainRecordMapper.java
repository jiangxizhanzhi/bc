package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

@Mapper
@Repository
public interface TrainRecordMapper extends BaseMapper<TrainRecord> {

	void setExtra(@Param("business_template_id") String businessTemplateId,
			@Param("user_id") String userId, @Param("business_type") Integer businessType, @Param("extra") String extra);

	void setEndTime(@Param("business_template_id") String businessTemplateId,
			@Param("user_id") String userId, @Param("business_type") Integer businessType, @Param("extra") String extra);

	void setEndTimeByRoomId(@Param("room_id") String roomId,
					@Param("business_type") Integer businessType, @Param("extra") String extra);

	Integer singleTrainCountFinishedByUserIdAndType(@Param("userId") String userId,
			@Param("type") Integer type);

	void batchEnd(@Param("businessTemplateId") String businessTemplateId);

	TrainRecord getByRoomId(String roomId);

	void updateEndTimeByRoomId(@Param("roomId") String roomId, @Param("endTime") Date endTime);
}
