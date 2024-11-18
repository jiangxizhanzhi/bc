package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 测评系统Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface EvalSystemService extends IService<EvalSystem> {

	/**
	 * 批量删除评估体系
	 *
	 * @param ids
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 */
	Boolean batchDelete(List<String> ids);

	/**
	 * 分页查询评估体系
	 *
	 * @param listQuery
	 * @return
	 * @throws
	 * @see EvalListQuery
	 * @see IPage<EvalSystem>
	 */
	IPage<EvalSystem> page(EvalListQuery listQuery);
}
