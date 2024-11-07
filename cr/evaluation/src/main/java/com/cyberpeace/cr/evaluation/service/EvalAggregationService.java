package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;

/**
 * 评估集合体Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface EvalAggregationService extends IService<EvalAggregation> {

    /**
     * 批量保存评估聚合项
     *
     * @param evalAggregations 评估聚合项列表
     */
    void saveBatchContext(List<EvalAggregationContext> evalAggregations);

    /**
     * 通过指标项id获取评估聚合项
     *
     * @param resourceId 指标项id
     * @return
     */
    List<EvalAggregationContext> getContextByEvalIndicatorId(String resourceId);

}
