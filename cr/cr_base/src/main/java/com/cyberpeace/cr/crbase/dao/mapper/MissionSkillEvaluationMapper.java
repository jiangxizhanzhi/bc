package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionSkillEvaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MissionSkillEvaluationMapper extends BaseMapper<MissionSkillEvaluation> {

    MissionSkillEvaluation selectLastOne(@Param("userId") String userId,
                                         @Param("skillId") String skillId);

}
