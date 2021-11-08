package com.cyberpeace.evaluate.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.dao.EstimateInfoDao;
import com.cyberpeace.evaluate.dao.EstimateVariableLinksDao;
import com.cyberpeace.evaluate.dao.entity.EstimateInfo;
import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;
import com.cyberpeace.evaluate.dao.mapper.EstimateInfoMapper;
import com.cyberpeace.evaluate.service.EstimateInfoService;
import com.cyberpeace.evaluate.service.context.EstimateInfoContext;

@Service
public class EstimateInfoServiceImpl implements EstimateInfoService {

	@Autowired
	private EstimateInfoDao estimateInfoDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EstimateVariableLinksDao estimateVariableLinksDao;

	@Autowired
	private EstimateInfoMapper estimateInfoMapper;

	@Override
	public String addEstimateInfo(EstimateInfoContext estimateInfoContext) {
		// 1、生成评估数据和变量项配置数据；
		// 1.1、生成评估数据
		EstimateInfo estimateInfo = new EstimateInfo();
		BeanUtils.copyProperties(estimateInfoContext, estimateInfo);
		String estimateId = IDGeneratorUtil.getID();
		estimateInfo.setEstimateId(estimateId);
		estimateInfo.setUserId(UserInfoContext.getUserContext().getUserId());
		// 1.2、生成变量项配置数据
		List<EstimateVariableLinks> estimateVariableLinks = estimateInfoContext
				.getEstimateVariableLinks();
		estimateVariableLinks.forEach(link -> {
			link.setEstimateVariableLinkId(IDGeneratorUtil.getID());
			link.setEstimateId(estimateId);
		});

		// 2、写入数据库，事务
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				estimateVariableLinks.forEach(link -> {
					estimateVariableLinksDao.inset(link);
				});
				estimateInfoDao.insert(estimateInfo);
			}
		});
		return estimateId;
	}

	@Override
	public ListRsp<EstimateInfo> queryEstimates(Page pageBase) {
		//1、获得用户id;
		String userId = UserInfoContext.getUserContext().getUserId();

		//2、查询评估
		IPage<EstimateInfo> estimateInfoList = estimateInfoMapper.pageByUserId(pageBase, userId);

		return PageUtil.getListResult(estimateInfoList);
	}

	@Override
	public EstimateInfo getEstimates(String estimateId) {
		EstimateInfo ret = estimateInfoDao.getByEstimateId(estimateId);
		return ret;
	}

	@Override
	public void deleteEstimate(String estimateId) {
		estimateInfoDao.deleteById(estimateId);
	}
}
