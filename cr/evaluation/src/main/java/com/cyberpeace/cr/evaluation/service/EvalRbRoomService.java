package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.StudentScoreReq;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;

public interface EvalRbRoomService extends IService<RbRoom> {


    /**
     * 分页查询评估任务评估
     * @param missionEvaluationQuery 分页查询体
     * @return 任务评估
     */
    IPage<MissionEvaluationContext>
    getRbMissionEvaluationList(EvalMissionEvaluationBo missionEvaluationQuery);



    /**
     * 获取得分趋势信息
     * @param roomId 红蓝对抗实例id
     * @param roleType 角色类型
     * @return 得分趋势信息
     */
    JSONObject getRbScoreProgress(String roomId, Integer roleType);


    StudentScoreRsp getStudentScore(String resource_id, String user_hash);

    /**
     * 修改评估
     */
    void submitEvaluation( String taskSubmitId, Integer score, String detail, Integer reason,
                          Integer evaluateStatus, String currentUserId);



    void updateStudentScore(StudentScoreReq studentScoreReq);
}