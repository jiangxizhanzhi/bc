package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;

import java.util.List;

/**
 * rb裁判Service
 *
 * @author SN
 * @date 2023/05/18
 */
public interface RbRefereeService {
    /**
     * 获取评估列表
     *
     * @param req 请求体
     * @return {@code List<EvaluationListRsp>}
     */
    List<EvaluationListRsp> getEvaluationList(EvaluationListReq req);

    /**
     * 获取评分列表
     *
     * @param roomId 房间id
     * @return {@code List<StudentScoreListRsp>}
     */
    List<StudentScoreListRsp> getScoreList(String roomId);
}
