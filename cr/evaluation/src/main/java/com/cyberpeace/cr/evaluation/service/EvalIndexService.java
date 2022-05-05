package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 *
 */
public interface EvalIndexService extends IService<EvalIndex> {

    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

	void deleteBatchByEvalSystemId(String evalSystemId);
}
