package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 * 测评编号Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface EvalIndexService extends IService<EvalIndex> {

    /**
     * 通过体系id获取指标项列表
     *
     * @param systemId
     * @param level
     * @return
     * @throws
     * @see String
     * @see Integer
     * @see List<IndexContext>
     */
    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

    /**
     * 删除体系id的指标项
     *
     * @param evalSystemId 测评系统id
     * @throws
     * @see String
     */
    void deleteBatchByEvalSystemId(String evalSystemId);
}
