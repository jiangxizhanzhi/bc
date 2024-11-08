package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 * 评估指数Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalIndexService extends IService<EvalIndex> {

    /**
     * 通过体系id获取指标项列表
     *
     * @param systemId
     * @param level
     * @return
     */
    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

    /**
     * 删除体系id的指标项
     *
     * @param evalSystemId 评估系统id
     */
    void deleteBatchByEvalSystemId(String evalSystemId);
}
