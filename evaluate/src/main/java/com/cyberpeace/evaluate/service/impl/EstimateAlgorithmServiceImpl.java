package com.cyberpeace.evaluate.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.dao.AlgorithmParamDao;
import com.cyberpeace.evaluate.dao.AlgorithmParamLinkDao;
import com.cyberpeace.evaluate.dao.EstimateAlgorithmDao;
import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;
import com.cyberpeace.evaluate.dao.entity.AlgorithmParamLink;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;
import com.cyberpeace.evaluate.dao.mapper.EstimateAlgorithmMapper;
import com.cyberpeace.evaluate.service.EstimateAlgorithmService;
import com.cyberpeace.evaluate.service.context.EstimateAlgorithmContext;
import com.google.common.collect.Lists;

@Service
public class EstimateAlgorithmServiceImpl implements EstimateAlgorithmService {

	@Autowired
	private EstimateAlgorithmDao estimateAlgorithmDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AlgorithmParamDao algorithmParamDao;

	@Autowired
	private AlgorithmParamLinkDao algorithmParamLinkDao;

	@Autowired
	private EstimateAlgorithmMapper estimateAlgorithmMapper;

	@Override
	public List<EstimateAlgorithm> selectByType(String algorithmType) {
		return estimateAlgorithmDao.selectByType(algorithmType);
	}

	@Override
	public String add(EstimateAlgorithmContext context) {

		EstimateAlgorithm algorithm = estimateAlgorithmDao
				.selectById(context.getEstimateAlgorithmId());
		if (null == algorithm) {
			addNew(context);
		} else {
			update(context);
		}

		return context.getEstimateAlgorithmId();

	}

	@Override
	public void delete(List<String> algorithmIds) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				algorithmIds.forEach(algorithmId -> {
					// 查询关联的参数编号
					List<AlgorithmParamLink> paramLinks = algorithmParamLinkDao
							.selectByAlgorithm(algorithmId);
					List<String> paramIds = Lists.newArrayList();
					paramLinks.forEach(link -> paramIds.add(link.getParamId()));

					// 删除算法
					estimateAlgorithmDao.deleteById(algorithmId);
					// 删除关联信息
					algorithmParamLinkDao.deleteByAlgorithm(algorithmId);
					if (CollectionUtils.isNotEmpty(paramIds)){
						// 删除参数
						algorithmParamDao.batchDelete(paramIds);
					}
				});
			}
		});

	}

	@Override
	public EstimateAlgorithm selectById(String algorithmId) {
		return estimateAlgorithmDao.selectById(algorithmId);
	}

	/**
	 * 新增算法
	 * @param context
	 */
	private void addNew(EstimateAlgorithmContext context) {
		// 算法数据新增
		EstimateAlgorithm estimateAlgorithm = new EstimateAlgorithm();
		BeanUtils.copyProperties(context, estimateAlgorithm);

		// 算法变量数据新增
		List<AlgorithmParam> algorithmParams = context.getParams();
		if (CollectionUtils.isNotEmpty(context.getParams())) {
			estimateAlgorithm.setParamCount(algorithmParams.size());
		} else {
			estimateAlgorithm.setParamCount(0);
		}
		List<String> paramIds = Lists.newArrayList();
		algorithmParams.forEach(algorithmParam -> {
			String paramId = IDGeneratorUtil.getID();
			algorithmParam.setParamId(paramId);
			paramIds.add(paramId);
		});

		// 关联关系新增
		List<AlgorithmParamLink> paramLinkList = Lists.newArrayList();
		paramIds.forEach(paramId -> {
			AlgorithmParamLink paramLink = new AlgorithmParamLink();
			paramLink.setParamId(paramId);
			paramLink.setAlgorithmId(context.getEstimateAlgorithmId());
			paramLink.setAlgorithmParamLinkId(IDGeneratorUtil.getID());
			paramLink.setSerialNumber(paramIds.indexOf(paramId));
			paramLinkList.add(paramLink);
		});

		// 设置算法参数数据
		estimateAlgorithm.setParams(JSON.toJSONString(algorithmParams));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				estimateAlgorithmDao.insert(estimateAlgorithm);
				if (CollectionUtils.isNotEmpty(algorithmParams)) {
					algorithmParamDao.batchAdd(algorithmParams);
				}
				if (CollectionUtils.isNotEmpty(paramLinkList)) {
					algorithmParamLinkDao.batchAdd(paramLinkList);
				}
			}
		});
	}

	@Override
	public ListRsp<EstimateAlgorithm> selectPageByType(String type, Page pageBase) {

		IPage<EstimateAlgorithm> algorithmIPage = estimateAlgorithmMapper.selectPageByType(pageBase, type);
		ListRsp<EstimateAlgorithm> reslut = PageUtil.getListResult(algorithmIPage);
		return reslut;
	}

	/**
	 * 更新算法信息
	 * @param context
	 */
	private void update(EstimateAlgorithmContext context) {

		// 查询已存在信息
		EstimateAlgorithm estimateAlgorithm = estimateAlgorithmDao
				.selectById(context.getEstimateAlgorithmId());
		if (null != estimateAlgorithm) {
			// 如果为空设置则设置参数 不为空则被修改 不需要重置
			if (StringUtils.isBlank(context.getAlgorithmContent())) {
				context.setAlgorithmContent(estimateAlgorithm.getAlgorithmContent());
			}
			if (StringUtils.isBlank(context.getAlgorithmName())) {
				context.setAlgorithmName(estimateAlgorithm.getAlgorithmName());
			}
			if (StringUtils.isBlank(context.getAlgorithmType())) {
				context.setAlgorithmType(estimateAlgorithm.getAlgorithmType());
			}
			if (StringUtils.isBlank(context.getLanguageType())) {
				context.setLanguageType(estimateAlgorithm.getLanguageType());
			}
			if (null == context.getParams()) {
				context.setParams(
						JSONArray.parseArray(estimateAlgorithm.getParams(), AlgorithmParam.class));
			}
			if (StringUtils.isBlank(context.getAlgorithmDesc())) {
				context.setAlgorithmDesc(estimateAlgorithm.getAlgorithmDesc());
			}

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<String> algorithmIds = Lists.newArrayList();
					algorithmIds.add(context.getEstimateAlgorithmId());
					delete(algorithmIds);
					addNew(context);
				}
			});
		}
	}

}
