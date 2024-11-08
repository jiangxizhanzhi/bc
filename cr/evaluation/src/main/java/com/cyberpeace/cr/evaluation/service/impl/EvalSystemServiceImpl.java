package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalSystemMapper;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估系统Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class EvalSystemServiceImpl extends ServiceImpl<EvalSystemMapper, EvalSystem> implements EvalSystemService {

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 评估指数Mapper
	 */
	@Autowired
	private EvalIndexMapper evalIndexMapper;

	/**
	 * 评估系统Mapper
	 */
	@Autowired
	private EvalSystemMapper evalSystemMapper;

	/**
	 * 批量删除
	 *
	 * @param evalSystemIds 评估系统id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchDelete(List<String> evalSystemIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<String> indexIds = evalIndexMapper.selectIdsByEvalSystemIds(evalSystemIds);

				evalSystemMapper.deleteBatchIds(evalSystemIds);
				if (indexIds.size() > 0) {
					evalIndexMapper.deleteBatchIds(indexIds);
				}

			}
		});

		return true;
	}

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalSystem> }
	 */
	@Override
	public IPage<EvalSystem> page(EvalListQuery listQuery) {

		Page page = listQuery.toPageParam();

        return evalSystemMapper.listEvalSystems(page, listQuery);
	}
}
