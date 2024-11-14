package com.cyberpeace.cr.teamtrain.service;

import com.cyberpeace.common.model.EntranceBo;
import com.cyberpeace.cr.teamtrain.service.bo.situation.StudentScoreVo;
import com.cyberpeace.cr.teamtrain.service.bo.situation.TeamSituationInfo;

import java.util.List;

/**
 * 团队态势Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface TeamSituationService {

    /**
     * 是否展示行业态势入口
     *
     * @param roomId 房间id
     * @return {@code Boolean}
     */
    EntranceBo ifShowSituation(String roomId);


    /**
     * 获取训练态势信息
     *
     * @param roomId 房间id
     * @return {@code TeamSituationInfo}
     */
    TeamSituationInfo getSituationInfo(String roomId);

    /**
     * 获取top5分数
     *
     * @param roomId 房间id
     * @return {@code Boolean}
     */
    List<StudentScoreVo> getTop5Score(String roomId);
}
