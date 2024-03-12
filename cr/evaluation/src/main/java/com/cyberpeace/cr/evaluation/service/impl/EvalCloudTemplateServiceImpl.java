package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalCloudTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalCloudTemplateListContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalCloudTemplateServiceImpl extends ServiceImpl<EvalCloudTemplateMapper, EvalCloudTemplate> implements EvalCloudTemplateService {

	@Autowired
	private EvalCloudTemplateMapper evalCloudTemplateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalRuleTemplateMapper evalRuleTemplateMapper;

	@Autowired
	private EvalActionTemplateMapper evalActionTemplateMapper;

	@Autowired
	private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

	@Autowired
	private EvalHostTemplateMapper evalHostTemplateMapper;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public void addCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		EvalCloudTemplate evalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
		evalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
		evalCloudTemplate.setResourceId(evalCloudTemplateAddReq.getCloudTemplateId());
		// TODO: 2024/3/8  调用中台接口保存拓扑
		evalCloudTemplate.setTopoId("111");
		evalCloudTemplateMapper.insert(evalCloudTemplate);
	}

	@Override
	public void modifyCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		EvalCloudTemplate evalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
		evalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
		// TODO: 2024/3/8  调用中台接口更新拓扑
		evalCloudTemplateMapper.updateById(evalCloudTemplate);
	}

	@Override
	public void deleteCloudTemplate(List<String> ids) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				evalCloudTemplateMapper.deleteBatchIds(ids);
				evalRuleTemplateMapper.deleteByCloudTemplateIds(ids);
				evalActionTemplateMapper.deleteByCloudTemplateIds(ids);
				evalDimensionTemplateMapper.deleteByCloudTemplateIds(ids);
				evalHostTemplateMapper.deleteByCloudTemplateIds(ids);
				evalStepTemplateMapper.deleteByCloudTemplateIds(ids);

				// TODO: 2024/3/8 调用中台接口删除拓扑
			}
		});
	}

	@Override
	public List<EvalCloudTemplateListRsp> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
		List<EvalCloudTemplateListContext> cloudList = evalCloudTemplateMapper.selectList(evalCloudTemplateListReq.getType(), evalCloudTemplateListReq.getPattern());
		List<String> createUserIds = cloudList.stream().map(EvalCloudTemplateListContext::getCreateUserId).distinct().collect(Collectors.toList());
		Map<String, String> userMap = remoteUserService.getIdNameMap(createUserIds);
		return cloudList.stream().map(evalCloudTemplateListContext -> {
			EvalCloudTemplateListRsp evalCloudTemplateListRsp = BeanUtils.copy(evalCloudTemplateListContext, EvalCloudTemplateListRsp.class);
			evalCloudTemplateListRsp.setCloudTemplateId(evalCloudTemplateListContext.getResourceId());
			evalCloudTemplateListRsp.setCreateUser(userMap.get(evalCloudTemplateListContext.getCreateUserId()));
			evalCloudTemplateListRsp.setType(EnumUtils.getEnumDescByCode(evalCloudTemplateListContext.getType(), EvalCloudTemplate.TestType.class));
			evalCloudTemplateListRsp.setNodeCount(0);
			return evalCloudTemplateListRsp;
		}).collect(Collectors.toList());
	}

	@Override
	public EvalCloudTemplateEnumRsp getCloudTemplateEnum() {
		EvalCloudTemplateEnumRsp evalCloudTemplateEnumRsp = new EvalCloudTemplateEnumRsp();
		evalCloudTemplateEnumRsp.setCloudType(Arrays.stream(EvalCloudTemplate.CloudType.values())
													.map(cloudType -> new CodeNameRsp(cloudType.getCode(), cloudType.getDesc())).collect(Collectors.toList()));
		evalCloudTemplateEnumRsp.setTestType(Arrays.stream(EvalCloudTemplate.TestType.values())
												   .map(testType -> new CodeNameRsp(testType.getCode(), testType.getDesc())).collect(Collectors.toList()));
		return evalCloudTemplateEnumRsp;
	}
}
