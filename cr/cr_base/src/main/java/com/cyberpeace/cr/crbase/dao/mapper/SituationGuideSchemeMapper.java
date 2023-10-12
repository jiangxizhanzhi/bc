package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.constant.SituationGuideScheme;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideMissionBo;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideSceneBo;
import com.cyberpeace.cr.crbase.service.context.guide.SituationRoomBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SituationGuideSchemeMapper extends BaseMapper<SituationGuideScheme> {

    SituationGuideMissionBo selectRoleConfigByBusinessType(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    SituationRoomBo selectRoomTrainStatus(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    SituationGuideSceneBo selectSceneByRoomId(@Param("roomId") String roomId, @Param("businessType") Integer businessType);
}
