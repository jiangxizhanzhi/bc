package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SituationGuideHisMapper extends BaseMapper<SituationGuideHistory> {

	Integer countByType(@Param("roomId") String roomId, @Param("type") Integer type);

	List<AttackTypeCountGroup> countByCategory(@Param("roomId") String roomId, @Param("eventType") Integer eventType);

}
