package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationTime;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SituationTimeMapper extends BaseMapper<SituationTime> {

	SituationTime selectByTopoId(@Param("topoId") String topoId);
}
