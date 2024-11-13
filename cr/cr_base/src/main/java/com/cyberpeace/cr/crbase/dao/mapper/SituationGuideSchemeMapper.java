package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.constant.SituationGuideScheme;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideMissionBo;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideSceneBo;
import com.cyberpeace.cr.crbase.service.context.guide.SituationRoomBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 态势导调方案Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface SituationGuideSchemeMapper extends BaseMapper<SituationGuideScheme> {

    /**
     * 查询岗位配置由业务类型
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code SituationGuideMissionBo }
     */
    SituationGuideMissionBo selectRoleConfigByBusinessType(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    /**
     * 查询房间训练状态
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code SituationRoomBo }
     */
    SituationRoomBo selectRoomTrainStatus(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    /**
     * 查询场景由房间id
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code SituationGuideSceneBo }
     */
    SituationGuideSceneBo selectSceneByRoomId(@Param("roomId") String roomId, @Param("businessType") Integer businessType);
}
