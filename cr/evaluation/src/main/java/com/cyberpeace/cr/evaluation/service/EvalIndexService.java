package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 *
 */
public interface EvalIndexService extends IService<EvalIndex> {

    /** 通过体系id获取指标项列表
     * @param systemId
     * @param level
     * @return
     */
    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level, Boolean isTest);

    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

    /** 删除体系id的指标项
     * @param evalSystemId
     */
	void deleteBatchByEvalSystemId(String evalSystemId);
}
