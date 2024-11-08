package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估系统Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalSystemService extends IService<EvalSystem> {

	/**
	 * 批量删除评估体系
	 *
	 * @param ids
	 * @return
	 */
	Boolean batchDelete(List<String> ids);

	/**
	 * 分页查询评估体系
	 *
	 * @param listQuery
	 * @return
	 */
	IPage<EvalSystem> page(EvalListQuery listQuery);
}
