package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.evaluation.controller.req.EvalStudentScoreReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSubmitEvaluationReq;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbSituationScoreRsp;

public interface EvalRbRoomService {

    /**
     * 分页查询评估任务评估
     * @param missionEvaluationQuery 分页查询体
     * @return 任务评估
     */
    IPage<MissionEvaluationContext>
    getRbMissionEvaluationList(EvalMissionEvaluationBo missionEvaluationQuery);

    /**
     * 获取得分趋势信息
     * @param evalTemplateId 评估模版id
     * @param roomId         红蓝对抗实例id
     * @param roleType       角色类型
     * @return 得分趋势信息
     */
    JSONObject getRbScoreProgress(String evalTemplateId, String roomId, Integer roleType);

    StudentScoreRsp getStudentScore(String resource_id, String user_hash);

    /**
     * 修改评估
     */
    void submitEvaluation(EvalSubmitEvaluationReq req, String currentUserId);


    void updateStudentScore(EvalStudentScoreReq studentScoreReq);

    /**
     * 获取红蓝学员排名
     * @param evalTemplateId 评估模版id
     * @param roomId         红蓝对抗实例id
     * @param roleType       角色类型
     * @return 红蓝学员排名
     */
    JSONObject getRbMembersRanking(String evalTemplateId, String roomId, Integer roleType);

    /**
     * 获取任务排名数据
     * @param evalTemplateId 评估模版id
     * @param roomId         红蓝对抗实例id
     * @param roleType       角色类型
     * @return 任务排名数据
     */
    JSONObject getRbLeaderBoard(String evalTemplateId, String roomId, Integer roleType);

    RbSituationScoreRsp getTeamScore(String evalTemplateId, String roomId, Integer roleType);

}