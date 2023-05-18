package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;

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
}
