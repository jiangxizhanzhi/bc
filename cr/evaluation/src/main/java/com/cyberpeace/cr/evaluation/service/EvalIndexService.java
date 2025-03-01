package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import java.util.List;

/**
 *
 */
public interface EvalIndexService extends IService<EvalIndex> {

    /** 通过体系id获取指标项列表
     * @param systemId
     * @param level
     * @return
     */
    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level, Boolean isTest);

    /** 删除体系id的指标项
     * @param evalSystemId
     */
	void deleteBatchByEvalSystemId(String evalSystemId);
}
