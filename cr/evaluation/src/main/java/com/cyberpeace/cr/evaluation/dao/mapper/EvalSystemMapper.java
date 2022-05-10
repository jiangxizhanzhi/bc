package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalSystem
 */
public interface EvalSystemMapper extends BaseMapper<EvalSystem> {

	IPage<EvalSystem> listEvalSystems(Page page, EvalListQuery listQuery);
}




