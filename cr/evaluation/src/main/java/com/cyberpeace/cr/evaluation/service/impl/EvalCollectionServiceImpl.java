package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class EvalCollectionServiceImpl extends ServiceImpl<EvalCollectionMapper, EvalCollection> implements EvalCollectionService {

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Override
	public void saveBatchContext(List<EvalCollectionContext> evalCollectionContexts) {
		List<EvalCollection> evalCollectionList = evalCollectionContexts.stream()
				.map(evalCollectionContext -> {
					EvalCollection evalCollection = BeanUtils.copy(evalCollectionContext,
							EvalCollection.class);

					evalCollection.setResourceId(evalCollectionContext.getEvalCollectionId());

					return evalCollection;
				}).collect(Collectors.toList());

		this.saveBatch(evalCollectionList);
	}

	@Override
	public List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId) {
		List<EvalCollection> evalCollectionList = evalCollectionMapper
				.selectIdsByEvalIndicatorIds(Lists.newArrayList(evalIndicatorId));

		return evalCollectionList.stream().map(evalCollection -> {
			EvalCollectionContext evalCollectionContext = BeanUtils.copy(evalCollection,
					EvalCollectionContext.class);

			evalCollectionContext.setEvalCollectionId(evalCollection.getResourceId());

			return evalCollectionContext;
		}).collect(Collectors.toList());
	}
}
