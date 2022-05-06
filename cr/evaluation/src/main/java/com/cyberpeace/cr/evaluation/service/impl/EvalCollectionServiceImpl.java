package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;

/**
 *
 */
@Service
public class EvalCollectionServiceImpl extends ServiceImpl<EvalCollectionMapper, EvalCollection>
    implements EvalCollectionService{

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
}




