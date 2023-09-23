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
 *
 */
@Service
public class EvalSystemServiceImpl extends ServiceImpl<EvalSystemMapper, EvalSystem> implements EvalSystemService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalIndexMapper evalIndexMapper;

	@Autowired
	private EvalSystemMapper evalSystemMapper;

	@Override
	public Boolean batchDelete(List<String> evalSystemIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<String> indexIds = evalIndexMapper.selectIdsByEvalSystemIds(evalSystemIds);

				evalSystemMapper.deleteBatchIds(evalSystemIds);
				if (!indexIds.isEmpty()) {
					evalIndexMapper.deleteBatchIds(indexIds);
				}

			}
		});

		return true;
	}

	@Override
	public IPage<EvalSystem> page(EvalListQuery listQuery) {

		Page<EvalSystem> page = listQuery.toPageParam();

        return evalSystemMapper.listEvalSystems(page, listQuery);
	}
}
