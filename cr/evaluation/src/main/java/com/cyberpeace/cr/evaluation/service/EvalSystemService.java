package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 *
 */
public interface EvalSystemService extends IService<EvalSystem> {

	Boolean batchDelete(List<String> ids);

	IPage<EvalSystem> page(EvalListQuery listQuery);
}
