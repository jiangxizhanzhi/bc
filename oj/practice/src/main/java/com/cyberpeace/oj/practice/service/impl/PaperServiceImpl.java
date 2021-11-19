package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.constant.PracticeType;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeBase;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

	@Autowired
	PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	PracticeOperationMapper practiceOperationMapper;

	@Autowired
	PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public Paper create(AddOrUpdatePaperContext paperContext) {

		// 解析试卷信息
		Paper paper = BeanUtils.copy(paperContext, Paper.class);
		paper.setResourceId(IDGeneratorUtil.getID());
		paper.setIsPublic(CommonConstant.TRUE);

		// 数据设置
		List<PracticeTheoryRef> theoryRefList = Lists.newArrayList();
		List<PracticeOperationRef> operationRefs = Lists.newArrayList();
		setPaperRelateData(paper, theoryRefList, operationRefs, paperContext.getPracticeList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				paperMapper.insert(paper);

				if (CollectionUtils.isNotEmpty(theoryRefList)) {
					practiceTheoryRefMapper.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefMapper.saveBatch(operationRefs);
				}
			}
		});

		return paper;
	}

	@Override
	public Paper update(AddOrUpdatePaperContext paperContext) {

		// 数据拷贝
		Paper paper = BeanUtils.copy(paperContext, Paper.class);

		// 参数设置
		List<PracticeTheoryRef> theoryRefList = Lists.newArrayList();
		List<PracticeOperationRef> operationRefs = Lists.newArrayList();
		setPaperRelateData(paper, theoryRefList, operationRefs, paperContext.getPracticeList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				paperMapper.updateById(paper);
				practiceTheoryRefMapper.deleteByPaper(paper.getResourceId());
				practiceOperationRefMapper.deleteByPaper(paper.getResourceId());

				if (CollectionUtils.isNotEmpty(theoryRefList)) {
					practiceTheoryRefMapper.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefMapper.saveBatch(operationRefs);
				}
			}
		});

		return paper;
	}

	/**
	 * 设置相关参数
	 * @param paper
	 * @param theoryRefs
	 * @param operationRefs
	 */
	private void setPaperRelateData(Paper paper, List<PracticeTheoryRef> theoryRefs,
			List<PracticeOperationRef> operationRefs,
			List<PaperPracticeContext> practiceContextList) {

		if (CollectionUtils.isNotEmpty(practiceContextList)) {
			Double totalScore = StreamApiUtils.mapToDouble(practiceContextList,
					PaperPracticeContext::getScore);
			paper.setTotalScore(totalScore);
			paper.setPracticeCount(practiceContextList.size());

		} else {
			paper.setTotalScore(new Double(CommonConstant.ZERO));
			paper.setPracticeCount(CommonConstant.ZERO);
		}

		// 解析practiceList, 设置试卷基础信息
		Map<Integer, List<PaperPracticeContext>> columnMap = StreamApiUtils
				.groupByColumn(practiceContextList, PaperPracticeContext::getPracticeType);

		// 生成理论习题练习
		List<PaperPracticeContext> theoryContextList = columnMap
				.getOrDefault(PracticeType.THEORY.getCode().intValue(), Lists.newArrayList());
		List<PracticeTheoryRef> theoryRefList = contextToEntity(practiceTheoryMapper,
				theoryContextList, paper, PracticeTheoryRef.class);
		theoryRefs.addAll(theoryRefList);

		// 生成实践操作练习
		List<PaperPracticeContext> operationContextList = columnMap
				.getOrDefault(PracticeType.OPERATION.getCode(), Lists.newArrayList());
		List<PracticeOperationRef> operationRefList = contextToEntity(practiceOperationMapper,
				operationContextList, paper, PracticeOperationRef.class);
		operationRefs.addAll(operationRefList);

	}

	/**
	 * 参数转实体类
	 * @param baseMapper
	 * @param paperPracticeContexts
	 * @param paper
	 * @return
	 */
	private <T> List<T> contextToEntity(BaseMapper baseMapper,
			List<PaperPracticeContext> paperPracticeContexts, Paper paper, Class<T> cls) {

		// 为空则返回空集合
		if (CollectionUtils.isEmpty(paperPracticeContexts)) {
			return Lists.newArrayList();
		}

		List<String> practiceIds = StreamApiUtils.simpleMap(paperPracticeContexts,
				PaperPracticeContext::getResourceId);

		List<PracticeBase> practiceList = baseMapper.selectBatchIds(practiceIds);
		if (practiceList.size() != practiceIds.size()) {
			log.error("practice data was inconsistent with database data when creating paper!!!");
			throw new BusinessException(OjBusinessErrorCode.PARAM_DATABASE_ERROR);
		}

		Map<String, PaperPracticeContext> practiceMap = StreamApiUtils
				.columnMap(paperPracticeContexts, PaperPracticeContext::getResourceId, pc -> pc);

		List<T> refList = StreamApiUtils.simpleMap(practiceList, practiceBase -> {

			PracticeTheoryRef theoryRef = new PracticeTheoryRef();
			practiceBase.setScore(practiceMap.get(practiceBase.getResourceId()).getScore());
			theoryRef.setPaperId(paper.getResourceId());
			theoryRef.setSnapshot(JsonUtil.toJsonString(practiceBase));
			theoryRef.setOriginId(practiceBase.getResourceId());
			theoryRef.setOptionRandom(
					practiceMap.get(practiceBase.getResourceId()).getOptionRandom());

			T instance = BeanUtils.copy(theoryRef, cls);
			return instance;
		});

		return refList;
	}

}
