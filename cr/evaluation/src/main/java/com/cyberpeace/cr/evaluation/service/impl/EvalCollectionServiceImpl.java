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
 * 评估收集Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see EvalCollectionService
 * @since 1.0.0
 */
@Service
public class EvalCollectionServiceImpl extends ServiceImpl<EvalCollectionMapper, EvalCollection> implements EvalCollectionService {

    /**
     * 评估收集Mapper
     *
     * @see EvalCollectionMapper
     */
    @Autowired
	private EvalCollectionMapper evalCollectionMapper;

    /**
     * 保存批量上下文
     *
     * @param evalCollectionContexts eval 集合上下文
     * @throws
     * @see List<EvalCollectionContext>
     */
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

    /**
     * 获取上下文由评估指示器id
     *
     * @param evalIndicatorId 评估指示器id
     * @return {@code List<EvalCollectionContext> }
     * @throws
     * @see String
     * @see List<EvalCollectionContext>
     */
    @Override
	public List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId) {
		List<EvalCollection> evalCollectionList = evalCollectionMapper
				.selectIdsByEvalIndicatorIds(Lists.newArrayList(evalIndicatorId));

		List<EvalCollectionContext> EvalCollectionContexts = evalCollectionList.stream()
				.map(evalCollection -> {
					EvalCollectionContext evalCollectionContext = BeanUtils.copy(evalCollection,
							EvalCollectionContext.class);

					evalCollectionContext.setEvalCollectionId(evalCollection.getResourceId());

					return evalCollectionContext;
				}).collect(Collectors.toList());

		return EvalCollectionContexts;
	}
}
