package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.service.context.guide.AttackTimeCountGroup;
import com.cyberpeace.cr.crbase.service.context.guide.AttackTypeCountGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface SituationGuideHisMapper extends BaseMapper<SituationGuideHistory> {

    Integer countByType(@Param("roomId") String roomId, @Param("type") Integer type);

    List<AttackTypeCountGroup> countByCategory(@Param("roomId") String roomId, @Param("eventType") Integer eventType);

    List<String> getEventNameList(@Param("roomId") String roomId, @Param("eventType") Integer eventType);

    List<SituationGuideHistory> getEventByScreen(@Param("roomId") String roomId,
                                                 @Param("typeList") List<Integer> typeList,
                                                 @Param("eventType") Integer eventType,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime,
                                                 @Param("eventName") String eventName,
                                                 @Param("category") Integer category,
                                                 @Param("sourceId") String sourceId,
                                                 @Param("targetId") String targetId,
                                                 @Param("result") Integer result,
                                                 @Param("role") Integer role);

    List<AttackTimeCountGroup> countByTime(@Param("roomId") String roomId, @Param("startDate") Date startDate);

}
